package sapr.listmaterials;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by Grigoriy on 22.02.2015.
 */
public class Layout {
    public Detail TemplateDetail;
    public List<Row> Rows;
    public int Count;//количество рядов
    public int Type; //0 - ряды вдоль оси Ох, 1 - вдоль Оу
    public int Mode;
    /**
     Режими генерації:
     1 - деталі в рядах однаково орієнтовані
     2 - деталі в сусідніх рядах повернуті на 180 градусів
     */
    public Point2D.Double StartPoint; //крайня ліва нижня точка розкладки
    public MaterialList materialList; //лист матеріалу

    Layout(Detail d, Point2D.Double startPoint, MaterialList mList, int type, int mode) {
        //ініціалізація
        TemplateDetail = d;
        StartPoint = startPoint;
        materialList = mList;
        Type = type;
        Mode = mode;
        Rows = new ArrayList<Row>();
        Count = 0;

        //генерація розкладки
        int detCounter = 0;
        int detsInRow = 0;
        int rowNum = -1;
        if(Mode == 1) {
            while(detCounter < TemplateDetail.Demand) {
                if(detsInRow == 0) {
                    Rows.add(new Row(materialList, 0));
                    rowNum++;

                    while(Rows.get(rowNum).add(TemplateDetail)) {
                        detCounter++;
                        detsInRow++;
                    }
                }
                detsInRow = 0;
            }
        }

        if(Mode == 2) {

        }
    }

    public void paint(JFrame frame) {
        //paint borders
        frame.getGraphics().drawLine((int)StartPoint.getX() * Data.mxy, (int)StartPoint.getY() * Data.mxy, (int)StartPoint.getX() * Data.mxy, (int)(StartPoint.getY() - getHeight()) * Data.mxy);
        frame.getGraphics().drawLine((int)StartPoint.getX() * Data.mxy, (int)(StartPoint.getY() - getHeight()) * Data.mxy, (int)(StartPoint.getX() + getWidth()) * Data.mxy, (int)(StartPoint.getY() - getHeight()) * Data.mxy);
        frame.getGraphics().drawLine((int)(StartPoint.getX() + getWidth()) * Data.mxy, (int)(StartPoint.getY() - getHeight()) * Data.mxy, (int)(StartPoint.getX() + getWidth()) * Data.mxy, (int)StartPoint.getY() * Data.mxy);
        frame.getGraphics().drawLine((int)(StartPoint.getX() + getWidth()) * Data.mxy, (int)StartPoint.getY() * Data.mxy, (int)StartPoint.getX() * Data.mxy, (int)StartPoint.getY() * Data.mxy);

        for(int i = 0; i < Count; i++) {
            for (int j = 0; j < Rows.get(i).Count; j++) {
                Rows.get(i).Details.get(j).paint(frame);
            }
        }
    }

    public double getWidth() {
        double maxWidth = 0;
        for(int i = 0; i < Rows.size(); i++) {
            if(maxWidth <= Rows.get(i).getWidth()) {
                maxWidth = Rows.get(i).getWidth();
            }
        }
        return maxWidth;
    }

    public double getHeight() {
        double maxHeight = 0;
        for(int i = 0; i < Rows.size(); i++) {
            if(maxHeight <= Rows.get(i).getHeight()) {
                maxHeight = Rows.get(i).getHeight();
            }
        }
        return maxHeight;
    }
}
