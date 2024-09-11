package com.example.assignment1;

import java.util.Optional;

public class MyCircle extends MyOval{
    MyPoint center;
    double radius;
    MyColor color;

    MyCircle(MyPoint p, double radius, MyColor color){
        super(p, 2.0 * radius, 2.0 * radius, color);

        this.center = p; this.radius = radius;
        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);
    }

    @Override
    public void setColor(MyColor color){ this.color = color; }

    public MyPoint getCenter(){ return center; }

    public double getRadius(){ return radius; }

    public MyColor getColor(){ return color; }

    @Override
    public boolean similarObject(MyShape S){

        if (S.getClass().toString().equals("class MyCircle")){
        MyCircle C = (MyCircle) S;
        return radius == C.getRadius();
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){

        return "Circle Center: (" + center.getX() + ", " + center.getY() + ")" + "\n" +
                "Radius: " + radius + "\n" +
                "Perimeter " + perimeter() + "\n" +
                "Area " + area();
    }
}
