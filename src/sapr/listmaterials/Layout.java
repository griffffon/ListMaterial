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
    public Detail templateDetail;
    //public List<Detail> details = new ArrayList<Detail>();
    public int count; //количество деталей в разкладке
    public double widthLayout, heightLayout; //ширина и высота разкладки
    public double widthDetail, heightDetail; //ширина и высота детали, из которой сформирована разкладка
    //public int rowsNum, colsNum; //количество строк и столбцов разкладки
    public List<Row> rows;
    public int Count;
    public int DetsInRow;
    public int Type; //0 - ряды вдоль оси Ох, 1 - вдоль Оу
    public int flag; //признак размещения строк. если flag = 0 - строки располагаются вдоль ОХ. если flag = 1 - вдоль OY
    public Vector a1, a2, q;

    Layout(Detail d, double widthMaterial, double heightMaterial, int type) {
        templateDetail = d;
        widthDetail = templateDetail.getWidth();
        heightDetail = templateDetail.getHeight();
        Type = type;

        rows = new ArrayList<Row>();
        Count = 0;

        if(Type == 0) {
            Count = new BigDecimal(heightMaterial / templateDetail.getHeight()).setScale(0, RoundingMode.DOWN).intValue();
            DetsInRow = new BigDecimal(widthMaterial / templateDetail.getWidth()).setScale(0, RoundingMode.DOWN).intValue();
        } else if(Type == 1) {
            Count = new BigDecimal(widthMaterial / templateDetail.getWidth()).setScale(0, RoundingMode.DOWN).intValue();
            DetsInRow = new BigDecimal(heightMaterial / templateDetail.getHeight()).setScale(0, RoundingMode.DOWN).intValue();
        }

        for(int i = 0; i < Count; i++) {
            rows.add(new Row());
        }
    }

    public void paint(JFrame frame) {
        int xc, yc;
        int mxy = 10;
        xc = frame.getWidth() / 2;
        yc = frame.getHeight() / 2;

        for(int i = 0; i < Count; i++) {
            for(int j = 0; j < rows.get(i).details.size(); j++) {
                for(int k = 0; k < rows.get(i).details.get(j).Count - 1; k++) {
                    frame.getGraphics().drawLine(xc + (int) rows.get(i).details.get(j).Points.get(k).getX() * mxy, yc - (int) rows.get(i).details.get(j).Points.get(k).getY() * mxy, xc + (int) rows.get(i).details.get(j).Points.get(k+1).getX() * mxy, yc - (int) rows.get(i).details.get(j).Points.get(k+1).getY() * mxy);
                }
            }
        }
    }

    public double ShiftX(Detail d1, Detail d2, boolean flag) {
        //d1 - левая деталь
        //d2 - правая деталь
        double result = 0;
        double eps = 0.001;
        double y1, y2, result1, x_shift;

        for(int i = 0; i < d2.borders.left.size(); i++) {
            for(int j = 0; j < d1.borders.right.size() - 1; j++) {
                if(Math.abs(d1.borders.right.get(j).getY() - d1.borders.right.get(j+1).getY()) <= eps) continue;
                if(d1.borders.right.get(j).getY() > d1.borders.right.get(j+1).getY()) {
                    y1 = d1.borders.right.get(j+1).getY();
                    y2 = d1.borders.right.get(j).getY();
                }
                else {
                    y1 = d1.borders.right.get(j).getY();
                    y2 = d1.borders.right.get(j+1).getY();
                }
                if((d2.borders.left.get(i).getY() <= y2) && (d2.borders.left.get(i).getY() >= y1)) {
                    x_shift = d1.borders.right.get(j).getX() + (d1.borders.right.get(j + 1).getX() - d1.borders.right.get(j).getX()) * (d2.borders.left.get(i).getY() - d1.borders.right.get(j).getY()) / (d1.borders.right.get(j+1).getY() - d1.borders.right.get(j).getY());
                    if(!flag) {
                        result1 = x_shift - d2.borders.left.get(i).getX();
                    }
                    else {
                        result1 = d2.borders.left.get(i).getX() - x_shift;
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

        for(int i = 0; i < d2.borders.bottom.size(); i++) {
            for(int j = 0; j < d1.borders.top.size() - 1; j++) {
                if(Math.abs(d1.borders.top.get(j).getX() - d1.borders.bottom.get(j+1).getX()) <= eps) continue;
                if(d1.borders.top.get(j).getX() > d1.borders.top.get(j+1).getX()) {
                    x1 = d1.borders.top.get(j+1).getX();
                    x2 = d1.borders.top.get(j).getX();
                }
                else {
                    x1 = d1.borders.top.get(j).getX();
                    x2 = d1.borders.top.get(j+1).getX();
                }
                if((d2.borders.bottom.get(i).getX() <= x2) && (d2.borders.bottom.get(i).getX() >= x1)) {
                    y_shift = d1.borders.top.get(j).getY() + (d1.borders.top.get(j + 1).getY() - d1.borders.top.get(j).getY()) * (d2.borders.bottom.get(i).getX() - d1.borders.top.get(j).getX()) / (d1.borders.top.get(j+1).getX() - d1.borders.top.get(j).getX());
                    if(!flag) {
                        result1 = y_shift - d2.borders.bottom.get(i).getY();
                    }
                    else {
                        result1 = d2.borders.bottom.get(i).getY() - y_shift;
                    }
                    if(result1 > result) result = result1;
                }
            }
        }
        return result;
    }

    public void CombinationRectsX() {
        //заполняем ряды деталями
        Detail firstDet = new Detail(templateDetail);
        //сместить в левый нижний угол раскладки

        for(int i = 0; i < firstDet.Points.size(); i++) {

        }

        for(int i = 0; i < Count; i++) {
            for(int j = 0; j < DetsInRow; j++) {
                rows.get(i).details.add(new Detail(templateDetail));
            }
        }



        //добаляем первую деталь в расскладку.
        //левый край прямоугольника, описаного вокруг детали, смещаем в (0; 0)
       /* Detail d1 = new Detail(templateDetail);
        double deltaX = d1.getWidth() / 2;
        double deltaY = d1.getHeight() / 2;
        for(int i = 0; i < d1.Count; i++) {
            d1.Points.set(i, new Point2D.Double(d1.Points.get(i).getX() + deltaX, d1.Points.get(i).getY() - deltaY));
        }

        details.add(d1);

        //if((templateDetail.getWidth() <= widthMaterial) && (templateDetail.getHeight() <= heightMaterial)) {
        //    int numDetsInRow = new BigDecimal(widthMaterial / templateDetail.getWidth()).setScale(0, RoundingMode.DOWN).intValue();
            for(int i = 1; i < Count; i++) {
                Detail det = new Detail(details.get(i-1));
                for(int j = 0; j < det.Count; j++) {
                    det.Points.set(j, new Point2D.Double(det.Points.get(j).getX() + det.getWidth(), det.Points.get(j).getY()));
                }
                details.add(det);
            }
        //}*/
    }
}
