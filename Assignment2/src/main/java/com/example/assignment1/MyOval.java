package com.example.assignment1;

import javafx.scene.canvas.GraphicsContext;
import java.util.Optional;

public class MyOval extends MyShape{
    MyPoint center;
    double width, height;
    double halfWidth, halfHeight;

    double semiMajor, semiMinor;
    MyColor color;

    double focus;
    double eccentricity;

    MyOval(MyPoint center, double width, double height, MyColor color){
        super(new MyPoint(), null);

        this.center = center; this.width = width; this.height = height;
        halfWidth = 0.5 * this.width; halfHeight = 0.5 * this.height;
        semiMajor = Math.max(halfWidth, halfHeight);
        semiMinor = Math.min(halfWidth, halfHeight);

        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);

        focus = Math.sqrt(Math.pow(semiMajor, 2) - Math.pow(semiMinor, 2));
        eccentricity = focus / semiMajor;
    }

    MyOval(MyOval O, MyColor color){
        super(new MyPoint(), null);

        this.center = O.getCenter(); this.width = O.getWidth(); this.height = O.getHeight();
        halfWidth = 0.5 * this.width; halfHeight = 0.5 * this.height;
        semiMajor = Math.max(halfWidth, halfHeight);
        semiMinor = Math.min(halfWidth, halfHeight);

        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);

        focus = O.getFocus();
        eccentricity = O.getEccentricity();
    }

    @Override
    public void setColor(MyColor color){ this.color = color; }

    public MyPoint getCenter(){ return center; }
    public double getWidth(){ return width; }
    public double getHeight(){ return height; }
    public double getSemiMajor(){ return semiMajor; }
    public double getSemiMinor(){ return semiMinor; }

    @Override
    public MyColor getColor(){ return color; }

    public double getFocus(){ return focus; }
    public double getEccentricity(){ return eccentricity; }

    @Override
    public double perimeter(){
        return (Math.sqrt(2) * Math.PI * Math.sqrt(Math.pow(semiMajor, 2) + Math.pow(semiMinor, 2)));
    }

    @Override
    public double area(){ return Math.PI * semiMajor * semiMinor; }

    @Override
    public void stroke(GraphicsContext GC){
        GC.setStroke(color.getJavaFXColor());
        GC.strokeOval(center.getX() - halfWidth, center.getY() - halfHeight, width, height);
    }

    public void draw(GraphicsContext GC){
        GC.setFill(color.getJavaFXColor());
        GC.fillOval(center.getX() - halfWidth, center.getY() - halfHeight, width, height);
    }

    public MyRectangle getMyBoundingRectangle(){
        double x = center.getX() - halfWidth;
        double y = center.getY() - halfHeight;
        MyPoint pLTC = new MyPoint(x, y, null);

        return new MyRectangle(pLTC, width, height, null);
    }

    public boolean containsMyPoint(MyPoint P){

        if (halfWidth == halfHeight) {
            return p.distance(center) <= halfWidth;
        }
        else{
            double hxh = halfWidth * halfHeight;
            double dx = halfHeight * (p.getX() - center.getX());
            double dy = halfWidth * (p.getY() - center.getY());
            return dx * dx + dy * dy <= hxh * hxh;
        }
    }

    public boolean similarObject(MyShape S){

        if (S.getClass().toString().equals("class MyOval")) {
            MyOval O = (MyOval) S;
            return semiMajor == O.getSemiMajor() && semiMinor == O.getSemiMinor();
        }
        else {
            return false;
        }
    }

    @Override
    public String toString(){

        return "Oval Center (" + center.getX() + ", " + center.getY() + ")" + "\n"
                + "Major Axis " + semiMajor + " Minor Axis" + semiMinor + "\n"
                + "Perimeter " + perimeter() + "\n"
                + "Area " + area();
     }
}
