package sapr.listmaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 07.01.2015.
 */
public class Detail {
    public String Name;
    public int Count;
    public int Demand;
    public List<Double> X = new ArrayList<Double>();
    public List<Double> Y = new ArrayList<Double>();
    public double Xpol, Ypol;

    //границы детали (левая, правая, верхняя, нижняя)
    public List<Double> AlX = new ArrayList<Double>();
    public List<Double> AlY = new ArrayList<Double>();
    public List<Double> ArX = new ArrayList<Double>();
    public List<Double> ArY = new ArrayList<Double>();
    public List<Double> AvX = new ArrayList<Double>();
    public List<Double> AvY = new ArrayList<Double>();
    public List<Double> AnX = new ArrayList<Double>();
    public List<Double> AnY = new ArrayList<Double>();

    //Определение контура, описанного вокруг детали
    public double getWidth() {
        return getMaxX() - getMinX();
    }

    public double getHeight() {
        return getMaxY() - getMinY();
    }

    //определяем границы детали
    public void getBorders() {
        //определяем левую и правую границы
        double D = 0;
        double x1 = getWidth() / 2;
        double y1 = 0;
        double x2 = getWidth() / 2;
        double y2 = getHeight();
        for(int i = 0; i < Count; i++) {
            D = (X.get(i) - x1) * (y2 - y1) - (Y.get(i) - y1) * (x2 - x1);
            if(D <= 0) {
                AlX.add(X.get(i));
                AlY.add(Y.get(i));
            }
            else {
                ArX.add(X.get(i));
                ArY.add(Y.get(i));
            }
        }

        //определяем верхнюю и нижнюю границы
        x1 = 0;
        y1 = getHeight() / 2;
        x2 = getWidth();
        y2 = getHeight() / 2;
        for(int i = 0; i < Count; i++) {
            D = (X.get(i) - x1) * (y2 - y1) - (Y.get(i) - y1) * (x2 - x1);
            if(D <= 0) {
                AnX.add(X.get(i));
                AnY.add(Y.get(i));
            }
            else {
                AvX.add(X.get(i));
                AvY.add(Y.get(i));
            }
        }
    }

    private double getMinX() {
        return Data.Min(X);
    }

    private double getMaxX() {
        return Data.Max(X);
    }

    private double getMinY() {
        return Data.Min(Y);
    }

    private double getMaxY() {
        return Data.Max(Y);
    }
}
