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

    //Определение контура, описанного вокруг детали
    public double MinX = Data.Min(X);
    public double MaxX = Data.Max(X);
    public double MinY = Data.Min(Y);
    public double MaxY = Data.Max(Y);
    public double Width = MaxX - MinX;
    public double Height = MaxY - MinY;

}
