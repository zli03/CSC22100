package com.example.assignment1;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public interface MyShapeInterface {

    public abstract MyRectangle getMyBoundingRectangle();
    public abstract boolean containsMyPoint(MyPoint p);

    //boolean containsMyPoint(MyPoint p);
    boolean similarObject(MyShape S);

    static boolean similarObject(MyShape S1, MyShape S2) {
        String sClassS1 = S1.getClass().toString();
        String sClassS2 = S2.getClass().toString();
        if (sClassS1.equals(sClassS2)) {
            switch (sClassS1) {

                case "class MyRectangle":
                    MyRectangle R1 = (MyRectangle) S1;
                    MyRectangle R2 = (MyRectangle) S2;
                    return R1.getWidth() == R2.getWidth() && R1.getHeight() == R2.getHeight();

                case "class MyOval":
                    MyOval O1 = (MyOval) S1;
                    MyOval O2 = (MyOval) S2;
                    return O1.getSemiMajor() == O2.getSemiMajor() && O1.getSemiMinor() == O2.getSemiMinor();

                case "class MyCircle":
                    MyCircle C1 = (MyCircle) S1;
                    MyCircle C2 = (MyCircle) S2;
                    return C1.getRadius() == C2.getRadius();

                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    static List<MyPoint> intersectMyShapes(MyShape s1, MyShape s2)
    {
        MyRectangle r1 = s1.getMyBoundingRectangle();
        MyRectangle r2 = s2.getMyBoundingRectangle();
        MyRectangle r = overlapMyShapes(r1,r2);

        if(r!=null) {

            double x = r.getTLC().getX();
            double y = r.getTLC().getY();
            double w = r.getWidth();
            double h = r.getHeight();

            List<MyPoint> intersect = new ArrayList<>();

            for (double i = 0; i <= w; i++) {
                double xi = x + i;
                for (double j = 0; j <= h; j++) {
                    MyPoint p = new MyPoint(xi, y + j, null);
                    if (s1.containsMyPoint(p) && s2.containsMyPoint(p)) {
                        intersect.add(p);
                    }
                }
            }
            return intersect;
        }
        else{return null;}
    }

    static MyRectangle overlapMyShapes(MyShape s1, MyShape s2){

        MyRectangle bound1 = s1.getMyBoundingRectangle();
        MyRectangle bound2 = s2.getMyBoundingRectangle();

        double x1 = bound1.getTLC().getX();
        double y1 = bound1.getTLC().getY();
        double w1 = bound1.getWidth();
        double h1 = bound1.getHeight();

        double x2 = bound2.getTLC().getX();
        double y2 = bound2.getTLC().getY();
        double w2 = bound2.getWidth();
        double h2 = bound2.getHeight();

        if(y1 + h1 < y2 || y2 + h2 < y1)
        {return null;}
        if(x1 + w1 < x2 || x2 + w2 < x1)
        {return null;}
        double x_min = Math.min(x1+w1,x2+w2);
        double x_max = Math.max(x1,x2);
        double y_min = Math.min(y1+h1,y2+h2);
        double y_max = Math.max(y1,y2);

        MyPoint bound_point = new MyPoint(x_max,y_max,null);
        return new MyRectangle(bound_point,Math.abs(x_min-x_max),Math.abs(y_min-y_max),null);
    }

    public default Canvas drawIntersectMyShapes(MyShape s1, MyShape s2, double w, double h, MyColor color)
    {

        List<MyPoint> intersect = intersectMyShapes(s1,s2);
        Canvas overlayCV = new Canvas(w,h);
        GraphicsContext overlayGC = overlayCV.getGraphicsContext2D();

        //s1.getMyBoundingRectangle().stroke(overlayGC);
        s1.draw(overlayGC);
        //s2.getMyBoundingRectangle().stroke(overlayGC);
        s2.draw(overlayGC);

        MyRectangle r = overlapMyShapes(s1,s2);
        MyColor colorR = MyColor.AQUA;
        r.setColor(colorR);
        r.stroke(overlayGC);

        if(intersect !=null)
        {
            System.out.println("Intersect");
            for(MyPoint p : intersect)
            {
                p.setColor(color);
                p.draw(overlayGC);
            }
        }
        else{System.out.println("No Intersect");}
        return overlayCV;
    }

    public static MyRectangle intersectMyRectangles(MyRectangle R1, MyRectangle R2)
    {
        double x1 = R1.getTLC().getX();
        double y1 = R1.getTLC().getY();
        double w1 = R1.getWidth();
        double h1 = R1.getHeight();

        double x2 = R2.getTLC().getX();
        double y2 = R2.getTLC().getY();
        double w2 = R2.getWidth();
        double h2 = R2.getHeight();

        if(y1+h1 < y2 || y1 > y2 + h2) {return null;}

        if(x1 + w1 < x2 || x1 > x2 + w2) {return null;}

        double xMax = Math.max(x1,x2);
        double yMax = Math.max(y1,y2);
        double xMin = Math.min(x1+w1,x2+w2);
        double yMin = Math.min(y1 +h1,y2 + h2);

        MyPoint p = new MyPoint(xMax,yMax, null);
        return new MyRectangle(p,Math.abs(xMax - xMin), Math.abs(yMax - yMin), null);
    }

}

