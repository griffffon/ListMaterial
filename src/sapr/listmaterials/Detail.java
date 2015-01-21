package sapr.listmaterials;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 07.01.2015.
 */
public class Detail {
    public String Name;
    public int Count;
    public int Demand;
    public List<Point2D.Double> Points;
    public Point2D.Double PolarPoint;
    public Borders borders;

    //границы детали (левая, правая, верхняя, нижняя)
   /* public List<Point2D.Double> BorderLeft;
    public List<Point2D.Double> BorderRight;
    public List<Point2D.Double> BorderTop;
    public List<Point2D.Double> BorderBottom;*/

    Detail() {
        Points = new ArrayList<Point2D.Double>();
        PolarPoint = new Point2D.Double();

        borders = new Borders();

        /*BorderLeft = new ArrayList<Point2D.Double>();
        BorderRight = new ArrayList<Point2D.Double>();
        BorderTop = new ArrayList<Point2D.Double>();
        BorderBottom = new ArrayList<Point2D.Double>();*/
    }

    //Определение контура, описанного вокруг детали
    public double getWidth() {
        return getMaxX() - getMinX();
    }

    public double getHeight() {
        return getMaxY() - getMinY();
    }

    public double getSquare() {
        double result = 0;
        for(int i = 0; i < Count; i++) {
            if(i == Count - 1) {
                result += Points.get(i).getX() * Points.get(0).getY() - Points.get(0).getX() * Points.get(i).getY();
            }
            else {
                result += Points.get(i).getX() * Points.get(i+1).getY() - Points.get(i+1).getX() * Points.get(i).getY();
            }
        }
        return Math.abs(result) / 2;
    }

    public void bypass() {
        //точки должны ити против часовой стрелки
        //если они идут по часовой стрелке - перенумеровываем
        List<Point2D.Double> tmp = new ArrayList<Point2D.Double>();
        int n1 = Points.size() / 3;
        int n2 = 2 * Points.size() / 3;

        Point2D.Double AB = new Point2D.Double(Points.get(n1).getX() - Points.get(0).getX(), Points.get(n1).getY()-Points.get(0).getY());
        Point2D.Double BC = new Point2D.Double(Points.get(n2).getX() - Points.get(n1).getX(), Points.get(n2).getY()-Points.get(n1).getY());

        double ABxBC = AB.getX() * BC.getY() - BC.getX() * AB.getY();

        if(ABxBC >= 0) {//поворот против часовой стрелки
            return;
        }
        else {//поворот за часовой стрелкой
            int totalPoints = Points.size() - 1;

            while(totalPoints >= 0) {
                tmp.add(Points.get(totalPoints));
                totalPoints--;
            }

            Points = tmp;
            return;
        }
    }

    //определяем границы детали
    public void getBorders() {
        getTopAndBottomBorders(getMinXNum(), getMaxXNum());
        getLeftAndRightBorders(getMinYNum(), getMaxYNum());
    }

    public void getTopAndBottomBorders(int numInfPointX, int numExtPointX) {
        int numBottom1 = 0, numTop1 = 0;
        if(numInfPointX > numExtPointX) {
            borders.numTop = numInfPointX-numExtPointX+1;

            for(int j = 0; j < borders.numTop; j++) {
                borders.top.add(new Point2D.Double(Points.get(numExtPointX + j).getX(), Points.get(numExtPointX + j).getY()));
            }

            numBottom1 = Count - numInfPointX;

            for(int j = 0; j < numBottom1; j++) {
                borders.bottom.add(new Point2D.Double(Points.get(numInfPointX + j).getX(), Points.get(numInfPointX + j).getY()));
            }

            for(int j = 0; j < numExtPointX; j++) {
                borders.bottom.add(new Point2D.Double(Points.get(j).getX(), Points.get(j).getY()));
            }

            borders.numBottom = numBottom1 + numExtPointX;
        }
        else {
            numTop1 = Count - numExtPointX;
            //numTop1 = Count - numExtPointX - 1;

            for(int j = 0; j < numTop1; j++) {
                borders.top.add(new Point2D.Double(Points.get(numExtPointX+j).getX(), Points.get(numExtPointX+j).getY()));
            }

            for(int j = 0; j < numInfPointX; j++) {
                borders.top.add(new Point2D.Double(Points.get(j).getX(), Points.get(j).getY()));
            }

            borders.numTop = numTop1 + numInfPointX;
            borders.numBottom = numExtPointX - numInfPointX + 1;

            for(int j = 0; j < borders.numBottom; j++) {
                borders.bottom.add(new Point2D.Double(Points.get(numInfPointX+j).getX(), Points.get(numInfPointX+j).getY()));
            }
        }
    }

    public void getLeftAndRightBorders(int numInfPointY, int numExtPointY) {
        int numLeft1 = 0, numRight1 = 0;
        if(numInfPointY > numExtPointY) {
            borders.numLeft = numInfPointY - numExtPointY + 1;

            for(int j = 0; j < borders.numLeft; j++) {
                borders.left.add(new Point2D.Double(Points.get(numExtPointY+j).getX(), Points.get(numExtPointY+j).getY()));
            }

            numRight1 = Count - numInfPointY;

            for(int j = 0; j < numRight1; j++) {
                borders.right.add(new Point2D.Double(Points.get(numInfPointY+j).getX(), Points.get(numInfPointY+j).getY()));
            }

            for(int j = 0; j < numExtPointY; j++) {
                borders.right.add(new Point2D.Double(Points.get(j).getX(), Points.get(j).getY()));
            }

            borders.numRight = numRight1 + numExtPointY;
        }
        else {
            numRight1 = Count - numExtPointY;

            for(int j = 0; j < numRight1; j++) {
                borders.right.add(new Point2D.Double(Points.get(numExtPointY + j).getX(), Points.get(numExtPointY + j).getY()));
            }

            for(int j = 0; j < numInfPointY; j++) {
                borders.right.add(new Point2D.Double(Points.get(j).getX(), Points.get(j).getY()));
            }

            borders.numRight = numRight1 + numInfPointY;
            borders.numLeft = numExtPointY - numInfPointY + 1;

            for(int j = 0; j < numLeft1; j++) {
                borders.left.add(new Point2D.Double(Points.get(numInfPointY + j).getX(), Points.get(numInfPointY + j).getY()));
            }
        }
    }

    public double getMinX() {
        double min = Points.get(0).getX();
        for(int i = 1; i < Points.size(); i++) {
            if(Points.get(i).getX() < min) {
                min = Points.get(i).getX();
            }
        }
        return min;
    }

    public double getMaxX() {
        double max = Points.get(0).getX();
        for(int i = 1; i < Points.size(); i++) {
            if(Points.get(i).getX() > max) {
                max = Points.get(i).getX();
            }
        }
        return max;
    }

    public double getMinY() {
        double min = Points.get(0).getY();
        for(int i = 1; i < Points.size(); i++) {
            if(Points.get(i).getY() < min) {
                min = Points.get(i).getY();
            }
        }
        return min;
    }

    public double getMaxY() {
        double max = Points.get(0).getY();
        for(int i = 1; i < Points.size(); i++) {
            if(Points.get(i).getY() > max) {
                max = Points.get(i).getY();
            }
        }
        return max;
    }

    public int getMinXNum() {
        double min = Points.get(0).getX();
        int n = 0;
        for(int i = 1; i < Points.size(); i++) {
            if(Points.get(i).getX() < min) {
                min = Points.get(i).getX();
                n = i;
            }
        }
        return n;
    }

    public int getMaxXNum() {
        double max = Points.get(0).getX();
        int n = 0;
        for(int i = 1; i < Points.size(); i++) {
            if(Points.get(i).getX() > max) {
                max = Points.get(i).getX();
                n = i;
            }
        }
        return n;
    }

    public int getMinYNum() {
        double min = Points.get(0).getY();
        int n = 0;
        for(int i = 1; i < Points.size(); i++) {
            if(Points.get(i).getY() < min) {
                min = Points.get(i).getY();
                n = i;
            }
        }
        return n;
    }

    public int getMaxYNum() {
        double max = Points.get(0).getY();
        int n = 0;
        for(int i = 1; i < Points.size(); i++) {
            if(Points.get(i).getY() > max) {
                max = Points.get(i).getY();
                n = i;
            }
        }
        return n;
    }
}
