package sapr.listmaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 25.01.2015.
 */
public class Row { //ряд разкладки
    public List<Detail> Details;
    public int Count;

    Row() {
        Details = new ArrayList<Detail>();
        Count = 0;
    }

    public void add(Detail detail) {
        Details.add(detail);
        Count++;
    }
}
