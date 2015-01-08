package sapr.listmaterials;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    //public double Xc, Yc;

    /*public Detail() {
        Count = 0;
        Demand = 0;
    }*/

    /*public Detail(String name, int count, int demand, List<Double> x, List<Double> y) {
        Name = name;
        Count = count;
        Demand = demand;
        XWork = x;
        YWork = y;
    }*/
}
