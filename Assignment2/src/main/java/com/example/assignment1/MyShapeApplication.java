package com.example.assignment1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class MyShapeApplication extends Application{

    public VBox addLeftVBox(double widthLeftCanvas, double heightLeftCanvas, TilePane TP, MyColor color){

        VBox VB = new VBox();
        VB.setPrefWidth(widthLeftCanvas);
        VB.setPadding(new Insets(5));

        Label lblMyColorPalette = new Label("MyColor Palette");
        lblMyColorPalette.setPrefWidth(widthLeftCanvas);
        lblMyColorPalette.setTextFill(MyColor.WHITE.getJavaFXColor());
        lblMyColorPalette.setBackground(new Background(new BackgroundFill(Optional.ofNullable(color).orElse(MyColor.GREY).getJavaFXColor(), CornerRadii.EMPTY, Insets.EMPTY)));

        VB.getChildren().addAll(lblMyColorPalette, TP);

        return VB;
    }

    public HBox addTopHBox(double widthTopCanvas, double heightTopCanvas, double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP) throws FileNotFoundException {

        HBox HB = new HBox();
        HB.setPrefWidth(widthTopCanvas);
        HB.setPadding(new Insets(5, 5, 5, 5));

        String [] nameImages = new String [] {"Line", "Oval", "Rectangle", "Intersection"};
        String pathFile = "C:\\Users\\zixua\\IdeaProjects\\Assignment2\\Geometric Shapes\\";

        Deque<MyShape> stackMyShapes = new ArrayDeque<MyShape>();
        for (String nameImage : nameImages){
            String nameFile = pathFile + nameImage + ".PNG";
            ImageView geometricImage = new ImageView(new Image(new FileInputStream(nameFile), heightTopCanvas, heightTopCanvas, true, false));

            geometricImage.setOnMouseClicked(e -> {
                switch(nameImage) {
                    /*(case "Arc":
                        dialogArc(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    */

                    case "Line":
                        dialogLine(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;

                    case "Oval":
                        dialogOval(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;

                    /*case "Polygon":
                        dialogPolygon(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    */

                    case "Rectangle":
                        dialogRectangle(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;

                    /*
                    case "Triangle":
                        dialogTriangle(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    */

                    case "Intersection":
                        dialogIntersection(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                }
            });

            HB.getChildren().add(geometricImage);
        }

        return HB;
    }


    /*
    public void dialogArc(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShape){

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyOval");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20, 100, 10, 10));

        TextField xCenter = new TextField(); TextField yCenter = new TextField();
        TextField width = new TextField(); TextField height = new TextField();
        TextField startingAngle = new TextField(); TextField extentAngle = new TextField();

        gridDialog.add(new Label("Oval center"), 0, 0);
        gridDialog.add(xCenter, 1, 0);
        gridDialog.add(new Label("x-Coordinate as fraction of canvas width"), 2, 0);
        gridDialog.add(yCenter, 1, 1);
        gridDialog.add(new Label("y-Coordinate as fraction of canvas width"), 2, 1);
        gridDialog.add(new Label("Oval Width"), 0,2);
        gridDialog.add(width, 1,2);
        gridDialog.add(new Label("Width as fraction of canvas height"), 2, 2);
        gridDialog.add(new Label("Oval height"), 0, 3);
        gridDialog.add(height, 1, 3);
        gridDialog.add(new Label("Height as fraction of canvas height"), 2, 3);
        gridDialog.add(new Label("Arc start angle"), 0, 4);
        gridDialog.add(startingAngle, 1, 4);
        gridDialog.add(new Label("In degrees"), 2, 4);
        gridDialog.add(new Label("Arc [extent] angle"), 0, 5);
        gridDialog.add(extentAngle, 1, 5);
        gridDialog.add(new Label("In degrees"), 2, 5);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> xCenter.requestFocus());

        List <String> geometricImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK){
                geometricImageInputs.add(xCenter.getText()); geometricImageInputs.add(yCenter.getText());
                geometricImageInputs.add(width.getText()); geometricImageInputs.add(height.getText());
                geometricImageInputs.add(startingAngle.getText()); geometricImageInputs.add(extentAngle.getText());
                return geometricImageInputs;
            }

            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            MyPoint pLTC = new MyPoint(Double.parseDouble(geometricImageInputs.get(0)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(1)) * heightCenterCanvas, null);
            double w = Double.parseDouble(geometricImageInputs.get(2)) * widthCenterCanvas;
            double h = Double.parseDouble(geometricImageInputs.get(3)) * heightCenterCanvas;
            double startAngle = Double.parseDouble(geometricImageInputs.get(4));
            double arcAngle = Double.parseDouble(geometricImageInputs.get(5));

            TP.setOnMouseClicked(e -> {

                MyColor color = CP.getColorPicked(); String tileId = color.toString();
                for (Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        MyOval O = new MyOval(pTLC, w, h, color);
                        MyArc A = new MyArc(O, startAngle, arcAngle, color);

                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        O.stroke(GC);
                        A.draw(GC);
                        A.getMyBoundingRectangle().stroke(GC);

                        stackMyShapes.push(A);
                        break;
                    }
                }
            });

            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });

    }
    */

    public void dialogLine(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShape) {

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyTriangle");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20, 100, 10, 10));

        TextField x1 = new TextField();
        TextField y1 = new TextField();
        TextField x2 = new TextField();
        TextField y2 = new TextField();

        gridDialog.add(new Label("End points as fraction of canvas width and height"), 0, 0);
        gridDialog.add(new Label("Point 1"), 0, 1);
        gridDialog.add(x1, 1, 1);
        gridDialog.add(y1, 1, 2);
        gridDialog.add(new Label("Point 2"), 0, 2);
        gridDialog.add(x2, 1, 2);
        gridDialog.add(y2, 2, 2);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> x1.requestFocus());

        List<String> geometricImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                geometricImageInputs.add(x1.getText()); geometricImageInputs.add(y1.getText());
                geometricImageInputs.add(x2.getText()); geometricImageInputs.add(y2.getText());
                return geometricImageInputs;
            }

            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            MyPoint p = new MyPoint(Double.parseDouble(geometricImageInputs.get(0)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(1)) * heightCenterCanvas, null);
            MyPoint q = new MyPoint(Double.parseDouble(geometricImageInputs.get(2)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(3)) * heightCenterCanvas, null);

            TP.setOnMouseClicked(e -> {

                MyColor color = CP.getColorPicked(); String tileId = color.toString();
                for (Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        MyLine L = new MyLine(p, q, color);

                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        L.draw(GC);
                        L.getMyBoundingRectangle().stroke(GC);

                        stackMyShape.push(L);
                        break;
                    }
                }
            });

            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }

    public void dialogOval(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShapes){

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyOval");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10, 10));

        TextField xCenter = new TextField(); TextField yCenter = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();

        gridDialog.add(new Label("Center"), 0,0);
        gridDialog.add(xCenter, 1, 0);
        gridDialog.add(new Label("x-Coordinate as fraction of canvas width"), 2, 0);
        gridDialog.add(yCenter, 1, 1);
        gridDialog.add(new Label("y-Coordinate as fraction of canvas width"), 2, 1);
        gridDialog.add(new Label("width"), 0, 2);
        gridDialog.add(width, 1,2);
        gridDialog.add(new Label("Width as fraction of canvas width"), 2, 2);
        gridDialog.add(new Label("Height"), 0,3);
        gridDialog.add(height, 1,3);
        gridDialog.add(new Label("Height as fraction of canvas height"), 2, 3);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> xCenter.requestFocus());

        List<String> geometricImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK){
                geometricImageInputs.add(xCenter.getText()); geometricImageInputs.add(yCenter.getText()); geometricImageInputs.add(width.getText()); geometricImageInputs.add(height.getText());
                return geometricImageInputs;
            }

            return null;
        });

        Optional <List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            MyPoint pTLC = new MyPoint(Double.parseDouble(geometricImageInputs.get(0)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(1)) * heightCenterCanvas, null);
            double w = Double.parseDouble(geometricImageInputs.get(2)) * widthCenterCanvas;
            double h = Double.parseDouble(geometricImageInputs.get(3)) * heightCenterCanvas;

            TP.setOnMouseClicked(e -> {

                MyColor color = CP.getColorPicked(); String tileId = color.toString();
                for (Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        MyOval O = new MyOval(pTLC, w, h, color);

                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        O.draw(GC);
                        O.getMyBoundingRectangle().stroke(GC);

                        stackMyShapes.push(O);
                        break;
                    }
                }
            });

            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }

    /*
    public void dialogPolygon(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShapes) {

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyPolygon");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField numberSides = new TextField();
        TextField xCenter = new TextField(); TextField yCenter = new TextField();
        TextField radius = new TextField();

        gridDialog.add(new Label("Number of sides"), 0 , 0);
        gridDialog.add(numberSides, 1, 0);
        gridDialog.add(new Label("Maximum number of sides: " + MyShapeInterface.maxNumberPolygonSides), 2, 0);
        gridDialog.add(new Label("Center"), 0 ,1);
        gridDialog.add(xCenter, 1, 1);
        gridDialog.add(new Label("x-Coordinate as fraction of canvas width"), 2, 1);
        gridDialog.add(yCenter, 1, 2);
        gridDialog.add(new Label("y-Coordinate as fraction of canvas width"), 2, 2);
        gridDialog.add(new Label("Radius"), 0, 3);
        gridDialog.add(radius, 1, 3);
        gridDialog.add(new Label ("Radius as fraction of the minimum of canvas width and canvas height"), 2, 3);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> numberSides.requestFocus());

        List<String> geometricImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK){
                geometricImageInputs.add(numberSides.getText()); geometricImageInputs.add(xCenter.getText()); geometricImageInputs.add(yCenter.getText()); geometricImageInputs.add(radius.getText());
                return geometricImageInputs;
            }

            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            int N = Integer.parseInt(geometricImageInputs.get(0));
            MyPoint center = new MyPoint(Double.parseDouble(geometricImageInputs.get(1)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(2)) * heightCenterCanvas, null);
            double r = Double.parseDouble(geometricImageInputs.get(3)) * Math.min(widthCenterCanvas, heightCenterCanvas);

            TP.setOnMouseClicked(e -> {

                MyColor color = CP.getColorPicked(); String tileId = color.toString();
                for (Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        MyPolygon Y = new MyPolygon(N, center, r, color);

                        GC.clearRect(0,0,widthCenterCanvas, heightCenterCanvas);
                        Y.draw(GC);
                        Y.getMyBoundingRectangle().stroke(GC);

                        stackMyShapes.push(Y);
                        break;
                    }
                }
            });

            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }
    */

    public void dialogRectangle(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShapes) {

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyRectangle");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField xPTLC = new TextField(); TextField yPTLC = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();

        gridDialog.add(new Label("Top Left Corner Point"), 0, 0);
        gridDialog.add(xPTLC, 1, 0);
        gridDialog.add(new Label("x-Coordinate as fraction of canvas width"), 2, 0);
        gridDialog.add(yPTLC, 1, 1);
        gridDialog.add(new Label("y-Coordinate as fraction of canvas height"), 2, 1);
        gridDialog.add(new Label("Width"), 0 , 2);
        gridDialog.add(width, 1, 2);
        gridDialog.add(new Label("As fraction of canvas width"), 2, 2);
        gridDialog.add(new Label("Height"), 0, 3);
        gridDialog.add(height, 1, 3);
        gridDialog.add(new Label("As fraction of canvas height"), 2, 3);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> xPTLC.requestFocus());

        List<String> geometricImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK){
                geometricImageInputs.add(xPTLC.getText()); geometricImageInputs.add(xPTLC.getText()); geometricImageInputs.add(width.getText()); geometricImageInputs.add(height.getText());
                return geometricImageInputs;
            }

            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            MyPoint pTLC = new MyPoint(Double.parseDouble(geometricImageInputs.get(0)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(1)) * heightCenterCanvas, null);
            double w = Double.parseDouble(geometricImageInputs.get(2)) * widthCenterCanvas;
            double h = Double.parseDouble(geometricImageInputs.get(3)) * heightCenterCanvas;

            TP.setOnMouseClicked(e -> {

                MyColor color = CP.getColorPicked(); String tileId = color.toString();
                for (Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        MyRectangle R = new MyRectangle(pTLC, w, h, color);

                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        R.draw(GC);
                        R.getMyBoundingRectangle().stroke(GC);

                        stackMyShapes.push(R);
                        break;
                    }
                }
            });

            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }

    /*
    public void dialogTriangle(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShapes) {

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyTriangle");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField x1 = new TextField(); TextField y1 = new TextField();
        TextField x2 = new TextField(); TextField y2 = new TextField();
        TextField x3 = new TextField(); TextField y3 = new TextField();

        gridDialog.add(new Label("Triangle vertices as fraction of canvas width and height"), 0, 0);
        gridDialog.add(new Label("Vertex 1"), 0 , 1);
        gridDialog.add(x1, 1, 1);
        gridDialog.add(y1, 1, 2);
        gridDialog.add(new Label("Vertex 2"), 0 , 2);
        gridDialog.add(x2, 1, 2);
        gridDialog.add(y2, 2, 2);
        gridDialog.add(new Label("Vertex 3"), 0, 3);
        gridDialog.add(x3, 1, 3);
        gridDialog.add(y3, 2, 3);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> x1.requestFocus());

        List<String> geometricImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK){
                geometricImageInputs.add(x1.getText()); geometricImageInputs.add(y1.getText());
                geometricImageInputs.add(x2.getText()); geometricImageInputs.add(y2.getText());
                geometricImageInputs.add(x3.getText()); geometricImageInputs.add(y3.getText());
                return geometricImageInputs;
            }

            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            MyPoint p = new MyPoint(Double.parseDouble(geometricImageInputs.get(0)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(1)) * heightCenterCanvas, null);
            MyPoint q = new MyPoint(Double.parseDouble(geometricImageInputs.get(2)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(3)) * heightCenterCanvas, null);
            MyPoint r = new MyPoint(Double.parseDouble(geometricImageInputs.get(4)) * widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(5)) * heightCenterCanvas, null);

            TP.setOnMouseClicked(e -> {

                MyColor color = CP.getColorPicked(); String tileId = color.toString();
                for (Node tile: TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        MyTriangle T = new MyTriangle(p, q, r, color);

                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        T.draw(GC);
                        T.getMyBoundingRectangle().stroke(GC);

                        stackMyShapes.push(T);
                        break;
                    }
                }
            });

            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }
    */

    public void dialogIntersection(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShapes) {

        Dialog dialog = new Dialog<>();
        dialog.setTitle("Intersection of 2 MyShape Objects");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        gridDialog.add(new Label("Draw the intersection of the last two MyShape Objects)"), 0, 0);

        dialog.getDialogPane().setContent(gridDialog);
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                TP.setOnMouseClicked(e -> {

                    MyColor color = CP.getColorPicked(); String tileId = color.toString();
                    for (Node tile : TP.getChildren()) {
                        if (tile.getId() == tileId){
                            Pane centerPane = new Pane();

                            MyShape S1 = stackMyShapes.pop();
                            MyShape S2 = stackMyShapes.pop();
                            centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCenterCanvas, S1, S2, color));
                            BP.setCenter(centerPane);
                            break;
                        }
                    }
                });
            }
        });
    }

    public Canvas addCenterCanvas(double widthCenterCanvas, double heightCenterCanvas, MyShape S1, MyShape S2, MyColor color){

        return S1.drawIntersectMyShapes(widthCenterCanvas, heightCenterCanvas, S1, S2, color);
    }

    @Override
    public void start(Stage PS) throws FileNotFoundException {

        double widthCanvas = 800.0; double heightCanvas = 600.0;

        BorderPane BP = new BorderPane();
        Pane topPane = new Pane(); Pane leftPane = new Pane(); Pane centerPane = new Pane();

        double widthLeftCanvas = 0.3 * widthCanvas; double heightTopCanvas = 0.15 * heightCanvas;
        double widthCenterCanvas = widthCanvas - widthLeftCanvas;
        double heightCenterCanvas = heightCanvas - heightTopCanvas;

        MyColorPalette CP = new MyColorPalette(widthLeftCanvas, heightCenterCanvas);
        TilePane TP = CP.getPalette();

        Scene SC = new Scene(BP, widthCanvas, heightCanvas, MyColor.WHITE.getJavaFXColor());
        PS.setTitle("MyShape!");
        PS.setScene(SC);

        MyPoint r = new MyPoint(widthCenterCanvas, 0.5 * heightCenterCanvas, null);
        MyPoint n = new MyPoint(widthCenterCanvas, heightCenterCanvas, null);
        MyPoint v = new MyPoint(0.5 * widthCenterCanvas, heightCenterCanvas, null);
        MyPoint h = new MyPoint(0, widthCenterCanvas, null);
        MyPoint u = new MyPoint(0, 0.5 * heightCenterCanvas, null);
        MyPoint t = new MyPoint();
        MyPoint p = new MyPoint(0.5 * widthCenterCanvas, 0, null);
        MyPoint k = new MyPoint(widthCenterCanvas, 0, null);

        MyPoint q = new MyPoint(0.5 * widthCenterCanvas, 0.5 * heightCenterCanvas, null);

        System.out.println("\nAngle of the line extending from" + r + "to [origin]" + q + ": " + r.angleX(q));
        System.out.println("Angle of the line extending from" + n + "to [origin]" + q + ": " + n.angleX(q));
        System.out.println("Angle of the line extending from" + v + "to [origin]" + q + ": " + v.angleX(q));
        System.out.println("Angle of the line extending from" + h + "to [origin]" + q + ": " + h.angleX(q));
        System.out.println("Angle of the line extending from" + u + "to [origin]" + q + ": " + u.angleX(q));
        System.out.println("Angle of the line extending from" + t + "to [origin]" + q + ": " + t.angleX(q));
        System.out.println("Angle of the line extending from" + p + "to [origin]" + q + ": " + p.angleX(q));
        System.out.println("Angle of the line extending from" + k + "to [origin]" + q + ": " + k.angleX(q));

        MyLine L1 = new MyLine(p, q, null);
        System.out.println("\n" + L1);

        MyLine L2 = new MyLine(r, q, null);
        System.out.println("\n" + L2);

        MyRectangle R = new MyRectangle(p, 0.5 * widthCenterCanvas, 0.5 * widthCenterCanvas, MyColor.LIME);
        System.out.println("\n" + R);

        MyOval O = new MyOval(q, 0.5 * widthCenterCanvas, 0.5 * heightCenterCanvas, MyColor.GOLD);
        System.out.println("\n" + O);

        double radius = 0.25 * Math.min(widthCenterCanvas, heightCenterCanvas);
        MyCircle C = new MyCircle(q, radius, MyColor.GREEN);
        System.out.println("\n" + C);

        /*
        MyTriangle T = new MyTriangle(t, r, v, MyColor.CYAN);
        System.out.println("\n" + T);

        double startAngle = 40;
        double arcAngle = 210;
        MyArc A = new MyArc(O, startAngle, arcAngle, MyColor.GREY);
        System.out.println("\n" + A);
        */

        MyShapeInterface [] shapes = new MyShape [5];
        shapes[0] = L1;
        shapes[1] = L2;
        shapes[2] = R;
        shapes[3] = O;
        shapes[4] = C;
        //shapes[5] = T;
        //shapes[6] = A;
        //shape[7] = Y;

        for (MyShapeInterface shape: shapes){
            System.out.println("\n" + shape);
            System.out.println(shape.getMyBoundingRectangle());
        }

        MyRectangle RR = (MyRectangle) shapes[2];
        MyOval OO = (MyOval) shapes[3];
        MyCircle CC = (MyCircle) shapes[4];
        //MyTriangle TT = (MyTriangle) shapes[5];
        //MyArc AA = (MyArc) shapes[6];
        //MyPolygon PP = (MyPolygon) shapes[7];

        //System.out.println("\n" + MyShapeInterface.intersectMyShapes(TT, AA));

        topPane.getChildren().add(addTopHBox(widthCanvas, heightTopCanvas, widthCenterCanvas, heightCenterCanvas, BP, CP, TP));
        BP.setTop(topPane);

        leftPane.getChildren().add(addLeftVBox(widthLeftCanvas, heightCenterCanvas, TP, MyColor.BLACK));
        BP.setLeft(leftPane);

        PS.show();
    }

    public static void main (String [] args) { launch(args); }
}
