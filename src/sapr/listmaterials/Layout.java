package sapr.listmaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 09.01.2015.
 */
public class Layout { //разкладка
    public Detail templateDetail;
    public List<Detail> details = new ArrayList<Detail>();
    public int count; //количество деталей в разкладке
    public double widthLayout, heightLayout; //ширина и высота разкладки
    public double widthDetail, heightDetail; //ширина и высота детали, из которой сформирована разкладка
    public int rows, cols; //количество строк и столбцов разкладки
    public int flag; //признак размещения строк. если flag = 0 - строки располагаются вдоль ОХ. если flag = 1 - вдоль OY
    public Vector a1, a2, q;

    Layout(Detail d) {
        templateDetail = d;
        widthDetail = templateDetail.getWidth();
        heightDetail = templateDetail.getHeight();
    }

    public void CombinationDetail() {
        //добаляем первую деталь в расскладку.
        //она уже смещена к началу координат
        details.add(templateDetail);

        //сдвиг второй детали вдоль оси ОХ
        double deltaOX = 0;
        double deltaR = 0;
        double deltaL = 0;



    }
}
