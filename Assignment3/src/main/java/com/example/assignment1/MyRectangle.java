package com.example.assignment1;

import javafx.scene.canvas.GraphicsContext;
import java.util.Optional;

public class MyRectangle extends MyShape{

    MyPoint pTLC;
    double width, height;
    MyColor color;

    MyRectangle(MyPoint p, double width, double height, MyColor color){

        super(new MyPoint(), null);

        this.pTLC = p; this.width = width; this.height = height;
        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);
    }

    MyRectangle(MyRectangle R, MyColor color){

        super(new MyPoint(), null);

        this.pTLC = R.getTLC(); this.width = R.getWidth(); this.height = R.getHeight();
        this.color = Optional.ofNullable(color).orElse(R.getColor());
    }

    @Override
    public void setColor(MyColor color) { this.color = color; }

    public MyPoint getTLC() { return pTLC; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public MyColor getColor() { return color; }

    @Override
    public double perimeter(){ return 2 * (width * height); }
    @Override
    public double area(){ return width * height; }

    @Override
    public void stroke(GraphicsContext GC){

        GC.setStroke(color.getJavaFXColor());
        GC.strokeRect(pTLC.getX(), pTLC.getY(), width, height);
    }

    @Override
    public void draw(GraphicsContext GC){

        GC.setFill(color.getJavaFXColor());
        GC.fillRect(pTLC.getX(), pTLC.getY(), width, height);
    }

    public MyRectangle getMyBoundingRectangle(){

        return new MyRectangle(pTLC, width, height, null);
    }

    public boolean containsMyPoint(MyPoint p){

        double x = p.getX(); double y = p.getY();
        double xR = pTLC.getX(); double yR = pTLC.getY();

        return (xR <= x && x <= xR + width) && (yR <= y && y <= yR + height);
    }

    public boolean similarObject(MyShape S){

        if (S.getClass().toString().equals("class MyRectangle")){
            MyRectangle R = (MyRectangle) S;
            return (width == R.getWidth() && height == R.getHeight());
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){

        return "Rectangle Top Left Corner " + pTLC + " Width: " + width
                + " Height " + height + " Perimeter " + perimeter() + " Area " + area();
    }
}
