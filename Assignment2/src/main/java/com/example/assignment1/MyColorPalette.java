package com.example.assignment1;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.*;

import java.util.Arrays;

public class MyColorPalette {
    MyColor colorPicked;
    MyColor [] colors = MyColor.getMyColors();
    int sizeMyColor = colors.length;
    double widthTile, heightTile;

    public MyColorPalette(double widthPalette, double heightPalette) {
        this.widthTile = widthPalette / 12.0 - 1.0;
        this.heightTile = this.widthTile;
    }
    public void setColorPicked(MyColor color){
        colorPicked = color;
    }
    public MyColor getColorPicked(){
        return colorPicked;
    }
    public TilePane getPalette() {

        TilePane TP = new TilePane();
        TP.setPrefTileWidth(widthTile); TP.setPrefTileHeight(heightTile);
        TP.setPrefRows(12);
        TP.setOrientation(Orientation.HORIZONTAL);
        TP.setPadding(new Insets( 1));

        for (int j = 0; j < sizeMyColor; j++){
            MyColor color = colors[j];
            String tileId = color.toString();

            Pane tileMyColor = new Pane();
            tileMyColor.setId(tileId);
            tileMyColor.setBackground(new Background(new BackgroundFill(color.getJavaFXColor(), CornerRadii.EMPTY, Insets.EMPTY)));

            tileMyColor.setOnMouseClicked(e -> {
                MyColor colorClicked = colors[Arrays.asList(MyColor.getMyColorIds()).indexOf(tileId)];
                setColorPicked(colorClicked);
            });

            TP.getChildren().add(tileMyColor);
        }
        return TP;
    }
}
