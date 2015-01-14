package sapr.listmaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 09.01.2015.
 */
public class Material {
    public int count = 0; //количество листов материала
    public List<MaterialList> items = new ArrayList<MaterialList>(); //листы материала

    public void Add(MaterialList materialList) {
        items.add(materialList);
        count++;
    }
}
