package sapr.listmaterials;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 08.01.2015.
 */
public class Model {
    public String Name;
    public String ServiceInfo;
    public int Count; //количество деталей
//    List<String> Names = new ArrayList<String>(); //имена деталей
//    List<Integer> PointsCount = new ArrayList<Integer>(); //количество точек для каждой детали
//    List<Integer> Demands = new ArrayList<Integer>(); //потребность в детали каждого типа
    List<Detail> Details = new ArrayList<Detail>();
}
