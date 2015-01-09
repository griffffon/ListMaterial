package sapr.listmaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 08.01.2015.
 */
public class Model {
    public String Name;
    public String ServiceInfo;
    public int Count; //количество деталей
    List<Detail> Details = new ArrayList<Detail>();
}
