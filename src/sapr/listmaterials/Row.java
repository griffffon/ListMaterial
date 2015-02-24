package sapr.listmaterials;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 25.01.2015.
 */
public class Row { //ряд розкладки
    public List<Detail> Details;
    public int Count;
    public int Type; //0 - ряды вдоль оси Ох, 1 - вдоль Оу
    public MaterialList materialList;
    public Point2D.Double StartPoint;
    public String text;

    Row(MaterialList materialList, int type) {
        Details = new ArrayList<Detail>();
        Count = 0;
        Type = type;
        this.materialList = materialList;
        StartPoint = new Point2D.Double();
    }

    /*public void add(Detail detail) {
        Details.add(detail);
        Count++;
    }*/

    public double ShiftX(Detail d1, Detail d2, boolean flag) {
        //d1 - левая деталь
        //d2 - правая деталь
        double result = 0;
        double eps = 0.001;
        double y1, y2, result1, x_shift;

        for(int i = 0; i < d2.Borders.left.size(); i++) {
            for(int j = 0; j < d1.Borders.right.size() - 1; j++) {
                if(Math.abs(d1.Borders.right.get(j).getY() - d1.Borders.right.get(j+1).getY()) <= eps) continue;
                if(d1.Borders.right.get(j).getY() > d1.Borders.right.get(j+1).getY()) {
                    y1 = d1.Borders.right.get(j+1).getY();
                    y2 = d1.Borders.right.get(j).getY();
                }
                else {
                    y1 = d1.Borders.right.get(j).getY();
                    y2 = d1.Borders.right.get(j+1).getY();
                }
                if((d2.Borders.left.get(i).getY() <= y2) && (d2.Borders.left.get(i).getY() >= y1)) {
                    x_shift = d1.Borders.right.get(j).getX() + (d1.Borders.right.get(j + 1).getX() - d1.Borders.right.get(j).getX()) * (d2.Borders.left.get(i).getY() - d1.Borders.right.get(j).getY()) / (d1.Borders.right.get(j+1).getY() - d1.Borders.right.get(j).getY());
                    if(!flag) {
                        result1 = x_shift - d2.Borders.left.get(i).getX();
                    }
                    else {
                        result1 = d2.Borders.left.get(i).getX() - x_shift;
                    }
                    if(result1 > result) result = result1;
                }
            }
        }
        return result;
    }

    public double ShiftY(Detail d1, Detail d2, boolean flag) {
        //d1 - нижняя деталь
        //d2 - верхняя деталь
        double result = 0;
        double eps = 0.001;
        double x1, x2, result1, y_shift;

        for(int i = 0; i < d2.Borders.bottom.size(); i++) {
            for(int j = 0; j < d1.Borders.top.size() - 1; j++) {
                if(Math.abs(d1.Borders.top.get(j).getX() - d1.Borders.bottom.get(j+1).getX()) <= eps) continue;
                if(d1.Borders.top.get(j).getX() > d1.Borders.top.get(j+1).getX()) {
                    x1 = d1.Borders.top.get(j+1).getX();
                    x2 = d1.Borders.top.get(j).getX();
                }
                else {
                    x1 = d1.Borders.top.get(j).getX();
                    x2 = d1.Borders.top.get(j+1).getX();
                }
                if((d2.Borders.bottom.get(i).getX() <= x2) && (d2.Borders.bottom.get(i).getX() >= x1)) {
                    y_shift = d1.Borders.top.get(j).getY() + (d1.Borders.top.get(j + 1).getY() - d1.Borders.top.get(j).getY()) * (d2.Borders.bottom.get(i).getX() - d1.Borders.top.get(j).getX()) / (d1.Borders.top.get(j+1).getX() - d1.Borders.top.get(j).getX());
                    if(!flag) {
                        result1 = y_shift - d2.Borders.bottom.get(i).getY();
                    }
                    else {
                        result1 = d2.Borders.bottom.get(i).getY() - y_shift;
                    }
                    if(result1 > result) result = result1;
                }
            }
        }
        return result;
    }

    public boolean add(Detail detail) {
        //суміщення в ряду однаково орієнтованих деталей (додавання деталі і суміщення)
        if(((materialList.width - this.getWidth()) >= detail.getWidth()) && ((materialList.height - this.getHeight()) >= detail.getHeight())) {
            if(Count == 0) {
                //додавання першої деталі в ряд
                double deltaX = detail.getWidth() / 2;
                double deltaY = detail.getHeight() / 2;
                for(int i = 0; i < detail.Points.size(); i++) {
                    detail.Points.set(i, new Point2D.Double(StartPoint.getX() + detail.Points.get(i).getX() + deltaX, -StartPoint.getY() + detail.Points.get(i).getY() + deltaY));
                }
            }
            else {
                if(Type == 0) {
                    double shiftX = 0;
                    double valA2 = ShiftX(Details.get(Count - 1), detail, true);
                    double valB2 = ShiftX(Details.get(Count - 1), detail, false);
                    if(valB2 > valA2) {
                        shiftX = valB2;
                    } else {
                        shiftX = valA2;
                    }

                    double deltaY = detail.getHeight() / 2;
                    for(int i = 0; i < detail.Points.size(); i++) {
                        detail.Points.set(i, new Point2D.Double(detail.Points.get(i).getX() + shiftX, detail.Points.get(i).getY() - deltaY));
                    }
                }

                if(Type == 1) {
                    double shiftY = 0;
                    double valA1 = ShiftY(Details.get(Count - 1), detail, true);
                    double valB1 = ShiftY(Details.get(Count - 1), detail, false);
                    if(valB1 > valA1) {
                        shiftY = valB1;
                    } else {
                        shiftY = valA1;
                    }

                    double deltaX = detail.getWidth() / 2;
                    for(int i = 0; i < detail.Points.size(); i++) {
                        detail.Points.set(i, new Point2D.Double(detail.Points.get(i).getX() + deltaX, detail.Points.get(i).getY() - shiftY));
                    }
                }
            }

            Details.add(detail);
            Count++;
            return true;
        }
        else return false;
    }

    public double getWidth() {
        if(Details.size() > 0) {
            double minX = Details.get(0).Points.get(0).x;
            double maxX = Details.get(0).Points.get(0).x;
            for (int i = 0; i < Details.size(); i++) {
                for (int j = 0; j < Details.get(i).Count; j++) {
                    if (minX >= Details.get(i).Points.get(j).x) {
                        minX = Details.get(i).Points.get(j).x;
                    }
                    if (maxX <= Details.get(i).Points.get(j).x) {
                        maxX = Details.get(i).Points.get(j).x;
                    }
                }
            }
            return (maxX + minX) / 2;
        } else return 0;
    }

    public double getHeight() {
        if(Details.size() > 0) {
            double minY = Details.get(0).Points.get(0).y;
            double maxY = Details.get(0).Points.get(0).y;
            for (int i = 0; i < Details.size(); i++) {
                for (int j = 0; j < Details.get(i).Count; j++) {
                    if (minY >= Details.get(i).Points.get(j).y) {
                        minY = Details.get(i).Points.get(j).y;
                    }
                    if (maxY <= Details.get(i).Points.get(j).y) {
                        maxY = Details.get(i).Points.get(j).y;
                    }
                }
            }
            return (maxY + minY) / 2;
        } else return 0;
    }
}
