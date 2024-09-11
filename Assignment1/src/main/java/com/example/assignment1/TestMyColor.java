package com.example.assignment1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.util.Optional;

public class TestMyColor extends Application {
    MyColor [] myColors = MyColor.getMyColors();
    int sizeMyColor = myColors.length;

    public VBox addLeftVBox(double widthLeftCanvas, double heightCanvas, TilePane TP, MyColor color) {

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

    public Canvas addCenterCanvas (double widthCanvas, double heightCanvas, MyColor color) {

        MyColor colorPicked = Optional.ofNullable(color).orElse(MyColor.WHITE);
        Canvas CV = new Canvas(widthCanvas, heightCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();

        GC.clearRect( 0, 0, widthCanvas, heightCanvas);
        GC.setFill(colorPicked.getJavaFXColor());
        GC.fillRect( 0, 0, widthCanvas, heightCanvas);

        double xText = 5.0; double yText = 20.0;
        GC.setStroke(colorPicked.invertColor());
        GC.setFont(Font.font ("Calibri", 13));
        GC.strokeText(colorPicked.toString(), xText, yText);

        return CV;
    }
    @Override
    public void start(Stage stage) {

        BorderPane BP = new BorderPane();
        Pane leftPane = new Pane(); Pane centerPane = new Pane();

        double widthCanvas = 600; double heightCanvas = 300;
        double widthLeftCanvas = 0.4 * widthCanvas;
        double widthCenterCanvas = widthCanvas - widthLeftCanvas;

        MyColorPalette CP = new MyColorPalette(widthLeftCanvas, heightCanvas);
        TilePane TP = CP.getPalette();

        leftPane.getChildren().add(addLeftVBox(widthLeftCanvas, heightCanvas, TP, MyColor.BLACK));
        BP.setLeft(leftPane);

        centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCanvas, null));
        BP.setCenter(centerPane);

        TP.setOnMouseClicked(e -> {

            MyColor color = CP.getColorPicked(); String tileID = color.toString();
            for (Node tile : TP.getChildren()) {
                if (tile.getId() == tileID) {
                    centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCanvas, color));
                    BP.setCenter(centerPane);
                    break;
                }
            }
        });

        stage.setTitle("Assignment #1");
        Scene scene = new Scene(BP, Color.WHITE);
        stage.setScene(scene);
        stage.setHeight(320);
        stage.setResizable(false);
        stage.show();
    }
}
