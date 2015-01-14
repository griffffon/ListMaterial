package sapr.listmaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 09.01.2015.
 */
public class Layout { //разкладка
    public Detail detail;
    public int count; //количество деталей в разкладке
    public double widthLayout, heightLayout; //ширина и высота разкладки
    public double widthDetail, heightDetail; //ширина и высота детали, из которой сформирована разкладка
    public int rows, cols; //количество строк и столбцов разкладки
    public int flag; //признак размещения строк. если flag = 0 - строки располагаются вдоль ОХ. если flag = 1 - вдоль OY
    public Vector a1, a2, q;

    Layout(Detail d) {
        detail = d;
        widthDetail = Data.Max(detail.X) - Data.Min(detail.X);
        heightDetail = Data.Max(detail.Y) - Data.Min(detail.Y);
    }

    public void CombinationDetail() {


    }
}
