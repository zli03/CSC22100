package com.example.assignment1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.lang.IllegalStateException;
import java.util.*;
import java.io.IOException;
public class FrequencyOfCharacters extends Application {
    Integer N;
    Integer M;
    double startAngle;
    double scale;
    String Title;
    String filename;
    Scanner input;

    Boolean isPiechart;
    List<String> barchartInputs = new ArrayList();
    List<String> piechartInputs = new ArrayList();

    public void toggleGroup(){
        ToggleGroup group = new ToggleGroup();

        RadioButton radioPiechart = new RadioButton("Pie Chart");
        radioPiechart.setToggleGroup(group);

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Chart Picker");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,200,20,10));

        gridDialog.add(radioPiechart, 0, 0);

        Platform.runLater(() -> radioPiechart.setSelected(true));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK){
                this.isPiechart = radioPiechart.isSelected();
                if (this.isPiechart) { dialogPiechart(); }
                }
            return null;
        });

        Optional <Boolean> Result = dialog.showAndWait();
    }

    public void dialogPiechart() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Pie Chart");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets( 20, 150, 10, 10));

        TextField numberEvents = new TextField();
        TextField totalNumberEvents = new TextField();
        TextField startingAngle = new TextField();

        ComboBox title = new ComboBox();
        title.getItems().addAll( "Alice in Wonderland",
                "A Tale of Two Cities", "David Copperfield", "Oliver Twist",
                "Emma", "Pride and Prejudice", "Moby Dick", "War and Peace",
                "xWords");

        gridDialog.add(new Label("Display"), 0, 0);
        gridDialog.add(numberEvents, 1, 0);
        gridDialog.add(new Label("Total"), 2, 0);
        gridDialog.add(totalNumberEvents, 3,0);
        gridDialog.add(new Label("Starting Angle"), 0, 1);
        gridDialog.add(startingAngle, 1, 1);
        gridDialog.add(new Label("Title"), 0, 2);
        gridDialog.add(title, 1, 2);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> numberEvents.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK){
                piechartInputs.add(numberEvents.getText()); piechartInputs.add(totalNumberEvents.getText()); piechartInputs.add(startingAngle.getText()); piechartInputs.add(title.getValue().toString());
                return piechartInputs;
            }
            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Result.ifPresent(event -> {
            this.N = Integer.parseInt(piechartInputs.get(0));
            this.M = Integer.parseInt(piechartInputs.get(1));
            this.startAngle = Double.parseDouble(piechartInputs.get(2));
            this.Title = piechartInputs.get(3);
            this.filename = "C:\\Users\\zixua\\IdeaProjects\\Assignment3\\Texts\\" + Title + ".txt";
        });
    }

    public void openFile() {
        try {
            input = new Scanner(Paths.get(filename));
        } catch (IOException ioException){
            System.err.println("File is not found");
        }
    }

    public String readFile() {
        String w = "";
        try {
            while (input.hasNext()) {
                w += input.nextLine().replaceAll("[^a-zA-Z]","").toLowerCase();
            }
        }
        catch (NoSuchElementException elementException) {
            System.err.println("Invalid input! Terminating...");
        }
        catch (IllegalStateException stateException) {
            System.out.println("Error processing file! Terminating...");
        }
        return w;
    }

    public void closeFile() {
        if (input != null) input.close();
    }

    public Canvas addCanvasLegend(double widthCanvas, double heightCanvas, HistogramAlphabet H) {
        String information;

        Canvas CV = new Canvas(widthCanvas, heightCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();

        MyColor colorLeftCanvas = MyColor.LINEN;
        GC.setFill(colorLeftCanvas.getJavaFXColor());
        GC.fillRect(0.0, 0.0, widthCanvas, heightCanvas);

        double xText = 20; double yText = 0.03625 * heightCanvas;
        MyColor colorStroke = MyColor.GRAY;
        GC.setStroke(colorStroke.invertColor());
        GC.setFont(Font.font("Calibri", 13));
        GC.strokeText("Frequency: Cumulative " + H.getCumulativeFrequency(), xText, yText);

        Map <Character, Integer> sortedFrequency = H.sortDownFrequency();

        Double yStep = 0.03625 * heightCanvas;
        for (Character K: sortedFrequency.keySet()){
            yText += yStep;
            information = K + ":\t" + sortedFrequency.get(K);
            GC.strokeText(information, xText, yText);
        }
        return CV;
    }

    public Canvas addCanvasPieChart(double widthCanvas, double heightCanvas, HistogramAlphabet H){
        Canvas CV = new Canvas(widthCanvas, heightCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();

        MyPoint center = new MyPoint(0.4 * widthCanvas, 0.5 * heightCanvas, null);

        double diameterPieChart = 0.60 * Math.min(widthCanvas, heightCanvas);
        HistogramAlphabet.MyPieChart pieChart = H.new MyPieChart(N, M, center, diameterPieChart, diameterPieChart, startAngle);
        Map <Character, Slice> slices = pieChart.getMyPieChart();

        System.out.println("\nPie Chart");
        slices.forEach(((K,V) -> System.out.println(K + ": " + slices.get(K))));

        double sumOfAngles = 0.0;
        for (Character key : slices.keySet()){
            sumOfAngles += slices.get(key).getArcAngle();
        }
        System.out.println("\nSum Of Angles: " + sumOfAngles);

        pieChart.draw(GC);

        return CV;
    }

    @Override
    public void start(Stage PS){
        toggleGroup();

        openFile();
        String w = readFile();
        closeFile();

        HistogramAlphabet H = new HistogramAlphabet(w);
        Map <Character, Integer> sortedFrequency = H.sortDownFrequency();

        System.out.println("\nFrequency of Characters");
        sortedFrequency.forEach((K, V) -> System.out.println(K + ": " + V));
        System.out.println("\nCumulative Frequency: " + H.getCumulativeFrequency());

        System.out.println("\nSorted Probability of Characters");
        System.out.println(H.sortDownProbability());
        System.out.println("\nSum of Probabilities : " + H.getSumOfProbability());

        BorderPane BP = new BorderPane();
        Pane leftP = new Pane();
        Pane centerP = new Pane();

        double widthCanvas = 800; double heightCanvas = 400;
        double widthLeftCanvas = 0.275 * widthCanvas;
        double widthCenterCanvas = widthCanvas - widthLeftCanvas;

        leftP.getChildren().add(addCanvasLegend(widthCenterCanvas, heightCanvas, H));
        BP.setLeft(leftP);

        if (isPiechart){
            centerP.getChildren().add(addCanvasPieChart(widthCenterCanvas, heightCanvas, H));
        }
        BP.setCenter(centerP);

        Scene SC = new Scene(BP, widthCanvas, heightCanvas, MyColor.WHITE.getJavaFXColor());
        PS.setTitle("Frequency of Characters: " + Title);
        PS.setScene(SC);
        PS.show();
    }

    public static void main (String [] args) { launch(); }
}
