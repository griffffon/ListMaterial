package sapr.listmaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 25.01.2015.
 */
public class Row {
    public List<Detail> details;
    public int Count;

    Row() {
        details = new ArrayList<Detail>();
        Count = 0;
    }

    public void add(Detail detail) {
        details.add(detail);
        Count++;
    }
}
