package sapr.listmaterials;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 09.01.2015.
 */
public class Layout { //разкладка
    public Detail TemplateDetail;
    public double Width, Height; //ширина и высота разкладки
    public List<Row> Rows;
    public int Count;//количество рядов
    public int Type; //0 - ряды вдоль оси Ох, 1 - вдоль Оу
    public Point2D.Double StartPoint;

    Layout(Detail d, Point2D.Double start, double width, double height, int type) {
        TemplateDetail = d;
        Type = type;
        Rows = new ArrayList<Row>();
        Count = 0;
        Width = width;
        Height = height;
        StartPoint = start;

        if(Type == 0) {
            Count = new BigDecimal(Height / TemplateDetail.getHeight()).setScale(0, RoundingMode.DOWN).intValue();
        } else if(Type == 1) {
            Count = new BigDecimal(Width / TemplateDetail.getWidth()).setScale(0, RoundingMode.DOWN).intValue();
        }

        for(int i = 0; i < Count; i++) {
            Rows.add(new Row());
        }
    }

    public void paint(JFrame frame) {
        //paint borders
        frame.getGraphics().drawLine((int)StartPoint.getX() * Data.mxy, (int)StartPoint.getY() * Data.mxy, (int)StartPoint.getX() * Data.mxy, (int)(StartPoint.getY() - Height) * Data.mxy);
        frame.getGraphics().drawLine((int)StartPoint.getX() * Data.mxy, (int)(StartPoint.getY() - Height) * Data.mxy, (int)(StartPoint.getX() + Width) * Data.mxy, (int)(StartPoint.getY() - Height) * Data.mxy);
        frame.getGraphics().drawLine((int)(StartPoint.getX() + Width) * Data.mxy, (int)(StartPoint.getY() - Height) * Data.mxy, (int)(StartPoint.getX() + Width) * Data.mxy, (int)StartPoint.getY() * Data.mxy);
        frame.getGraphics().drawLine((int)(StartPoint.getX() + Width) * Data.mxy, (int)StartPoint.getY() * Data.mxy, (int)StartPoint.getX() * Data.mxy, (int)StartPoint.getY() * Data.mxy);

        //paint details in layout
        for(int i = 0; i < Rows.get(0).Count; i++) {
            Rows.get(0).Details.get(i).paint(frame);
        }

        for(int i = 0; i < Rows.get(1).Count; i++) {
            Rows.get(1).Details.get(i).paint(frame);
        }

        for(int i = 0; i < Rows.get(2).Count; i++) {
            Rows.get(2).Details.get(i).paint(frame);
        }
    }

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

    public void CombinationX() {
        if(Type == 0) {
            //добавляем детали на раскладку, пока в этом есть необходимость
            int counter = 0;
            while (counter < TemplateDetail.Demand) {
                Rows.get(counter / Count).add(new Detail(TemplateDetail));
                counter++;
            }

            //сдвигаем "прямоугольники" деталей
            double deltaY = 0;
            for(int i = 0; i < Count; i++) {
                deltaY = i * TemplateDetail.getHeight();
                for(int j = 0; j < Rows.get(i).Count; j++) {
                    if(j == 0) {
                        Detail detail = Rows.get(i).Details.get(j);
                        Point2D.Double tmpStartPoint = new Point2D.Double(StartPoint.getX(), StartPoint.getY() - deltaY);
                        detail.setStartPoint(tmpStartPoint);
                    }
                    else {
                        double shiftX = 0;
                        double valA = ShiftX(Rows.get(i).Details.get(j-1), Rows.get(i).Details.get(j), true);
                        double valB = ShiftX(Rows.get(i).Details.get(j-1), Rows.get(i).Details.get(j), false);
                        if(valB > valA) {
                            shiftX = valB;
                        } else {
                            shiftX = valA;
                        }

                        Detail detail = Rows.get(i).Details.get(j);
                        Point2D.Double tmpStartPoint = new Point2D.Double(StartPoint.getX() + j * shiftX, StartPoint.getY() - deltaY);
                        detail.setStartPoint(tmpStartPoint);
                    }
                }
            }
        }
        else if(Type == 1) {
            //NOT FINISHED
            //NOT TESTED
            //добавляем детали на раскладку, пока в этом есть необходимость
            int counter = 0;
            while (counter < TemplateDetail.Demand) {
                Rows.get(counter / Count).add(new Detail(TemplateDetail));
                counter++;
            }

            //сдвигаем "прямоугольники" деталей
            double deltaX = 0;
            for(int i = 0; i < Count; i++) {
                deltaX = i * TemplateDetail.getWidth();
                for(int j = 0; j < Rows.get(i).Count; j++) {
                    if(j == 0) {
                        Detail detail = Rows.get(i).Details.get(j);
                        Point2D.Double tmpStartPoint = new Point2D.Double(StartPoint.getX() + deltaX, StartPoint.getY());
                        detail.setStartPoint(tmpStartPoint);
                    }
                    else {
                        double shiftY = 0;
                        double valA = ShiftY(Rows.get(i).Details.get(j - 1), Rows.get(i).Details.get(j), true);
                        double valB = ShiftY(Rows.get(i).Details.get(j - 1), Rows.get(i).Details.get(j), false);
                        if(valB > valA) {
                            shiftY = valB;
                        } else {
                            shiftY = valA;
                        }

                        Detail detail = Rows.get(i).Details.get(j);
                        Point2D.Double tmpStartPoint = new Point2D.Double(StartPoint.getX() + deltaX, StartPoint.getY() - j * shiftY);
                        detail.setStartPoint(tmpStartPoint);
                    }
                }
            }
        }
    }
}
