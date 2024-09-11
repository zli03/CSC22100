/*package com.example.assignment1;

import javafx.scene.canvas.GraphicsContext;
import java.util.Arrays;
import java.util.Optional;

public class MyTriangle extends MyShape{

    MyPoint p1, p2, p3;
    MyColor color;

    double s1, s2, s3;
    double s;

    double angle1, angle2, angle3;

    MyTriangle(MyPoint p1, MyPoint p2, MyPoint p3, MyColor color){

        super(new MyPoint(), null);

        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);

        s1 = p2.distance(p3);
        s2 = p3.distance(p1);
        s3 = p1.distance(p2);
        s = 0.5 * (s1 + s2 + s3);

        angle1 = Math.toDegrees(Math.acos((s2 * s2 + s3 * s3 - s1 * s1) / (2.0 * s2 * s3)));
        angle2 = Math.toDegrees(Math.acos((s3 * s3 + s1 * s1 - s2 * s2) / (2.0 * s3 * s1)));
        angle3 = Math.toDegrees(Math.acos((s1 * s1 + s2 * s2 - s3 * s3) / (2.0 * s1 * s2)));
    }

    MyTriangle(double s1, double s2, double s3, MyColor color){

        super(new MyPoint(), null);

        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        s = 0.5 * (s1 + s2 + s3);

        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);

        if (!isTriangle()){
            System.out.println("\nThe side lengths (" + s1 + ", " + s2 + ", " + s3 + ") do not form proper triangle");
            System.exit(1);
        }

        angle1 = Math.toDegrees(Math.acos((s2 * s2 + s3 * s3 - s1 * s1) / (2.0 * s2 * s3)));
        angle2 = Math.toDegrees(Math.acos((s3 * s3 + s1 * s1 - s2 * s2) / (2.0 * s3 * s1)));
        angle3 = Math.toDegrees(Math.acos((s1 * s1 + s2 * s2 - s3 * s3) / (2.0 * s1 * s2)));
    }

    MyTriangle(double side, double angle, MyColor color){

        super(new MyPoint(), null);

        this.s1 = side;
        this.s2 = side;
        this.s3 = 2.0 * s * Math.sin(Math.PI * angle / 360.0);
        s = 0.5 * (s1 + s2 + s3);

        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);

        angle1 = 0.5 * (180.0 - angle);
        angle2 = angle1;
        angle3 = angle;
    }

    MyTriangle(double side, MyColor color){

        super(new MyPoint(), null);

        this.s1 = side;
        this.s2 = side;
        this.s3 = side;
        s = 1.5 * side;

        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);

        angle1 = 60.0;
        angle2 = 60.0;
        angle3 = 60.0;
    }

    public void setColor(MyColor color){ this.color = color; }

    public MyPoint [] getVertices(){

        MyPoint [] vertices = new MyPoint [4];
        vertices[0] = p1; vertices[1] = p2; vertices[2] = p3; vertices[3] = p1;
        return vertices;
    }

    public double [] getSides(){

        double [] sides = new double [3];
        sides[0] = s1; sides[1] = s2; sides[2] = s3;
        return sides;
    }

    public double [] getAngles(){

        double [] angles = new double [3];
        angles[0] = angle1; angles[1] = angle2; angles[2] = angle3;
        return angles;
    }

    @Override
    public MyColor getColor(){ return color; }

    public boolean isTriangle(){ return (s - s1) > 0 && (s - s2) > 0 && (s - s3) > 0;}

    public MyPoint centroid(){

        return new MyPoint((p1.getX() + p2.getX() + p3.getX()) / 3.0, (p1.getY() + p2.getY() + p3.getY()) / 3.0, null);
    }

    @Override
    public double perimeter(){ return 2.0 * s; }

    @Override
    public double area(){ return Math.sqrt(s * (s - s1) * (s - s2) * (s - s3)); }

    public MyRectangle getMyBoundingRectangle(){

        double x1 = p1.getX(); double y1 = p1.getY();
        double x2 = p2.getX(); double y2 = p2.getY();
        double x3 = p3.getX(); double y3 = p3.getY();
        double xmax = Math.max(Math.max(x1, x2), x3);
        double ymax = Math.max(Math.max(y1, y2), y3);
        double xmin = Math.min(Math.min(x1, x2), x3);
        double ymin = Math.min(Math.min(y1, y2), y3);

        MyPoint pTLC = new MyPoint(xmin, ymin, null);
        return new MyRectangle(pTLC, Math.abs(xmax - xmin), Math.abs(ymax - ymin), null);
    }

    public boolean containsMyPoint(MyPoint P){

        double x = p.getX();
        double y = p.getY();

        int windingNumber = 0;
        MyPoint [] vertices = this.getVertices();

        double x1 = vertices[0].getX();
        double y1 = vertices[0].getY();
        for(int i = 1; i < 4; ++i){
            double x2 = vertices[i].getX();
            double y2 = vertices[i].getY();
            double ccw = (x2 - x1) * (y - y1) - (x - x1) * (y2 - y1);

            if (y2 > y && y >= y1) {
                if (ccw > 0) ++windingNumber;
            }
            if (y2 <= y && y < y1) {
                if (ccw < 0) --windingNumber;
            }

            x1 = x2;
            y1 = y2;
        }

        return windingNumber != 0;

        public boolean similarObject(MyShape S){

            if (S.getClass().toString().equals("class MyTriangle")) {
                MyTriangle T = (MyTriangle) S;
                double[] sidesThis = this.getSides();
                Arrays.sort(sidesThis);
                double[] sidesT = T.getSides();
                Arrays.sort(sidesT);
                return (sidesThis[0] == sidesT[0] && sidesThis[1] == sidesT[1] && sidesThis[2] == sidesT[2]);
            } else {
                return false;
            }
        }
    }

    public void stroke(GraphicsContext GC){

        double [] xCoordinates = new double[3];
        double [] yCoordinates = new double[3];

        xCoordinates[0] = p1.getX(); xCoordinates[1] = p2.getX(); xCoordinates[2] = p3.getX();
        yCoordinates[0] = p1.getY(); yCoordinates[1] = p2.getY(); yCoordinates[2] = p3.getY();

        GC.setStroke(color.getJavaFXColor());
        GC.strokePolygon(xCoordinates, yCoordinates, 3);
    }

    @Override
    public void draw(GraphicsContext GC){

        double [] xCoordinates = new double[3];
        double [] yCoordinates = new double[3];

        xCoordinates[0] = p1.getX(); xCoordinates[1] = p2.getX(); xCoordinates[2] = p3.getX();
        yCoordinates[0] = p1.getY(); yCoordinates[1] = p2.getY(); yCoordinates[2] = p3.getY();

        GC.setFill(color.getJavaFXColor());
        GC.fillPolygon(xCoordinates, yCoordinates, 3);
    }

    public String toString(){

        return "Triangle Vertices: " + Arrays.toString(getVertices()) + "\n" +
                "Sides: " + Arrays.toString(getSides()) + "\n" +
                "Angles: " + Arrays.toString(getAngles()) + "\n" +
                "Centroid: " + centroid() + "\n" +
                "Area: " + area() + "\n" +
                "Perimeter: " + perimeter() + "\n" +
                "Color: " + getColor();
    }
}
*/