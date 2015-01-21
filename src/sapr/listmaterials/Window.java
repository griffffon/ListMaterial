package sapr.listmaterials;

import javax.swing.*;

/**
 * Created by Grigoriy on 15.01.2015.
 *
 * Интерфейс временный, для отладочных целей
 */
public class Window extends JFrame {
    public static void main(String[] args) {
        //создание модели
        Model model = new Model();
        Data.ReadFromFile(model, "D:\\WORKSPACES\\IDEA Workspace\\List Material\\MyModel1.dgt");
        for (int i = 0; i < model.Count; i++) {
            model.Details.get(i).getBorders();
            //model.Details.get(i).getBorders();
            //model.Details.get(i).getTopAndBottomBorder();
        }

        //model.Details.get(2).getTopAndBottomBorder(4, 0);

        //создание материала
        Material material = new Material();
        material.Add(new MaterialList(100, 120));
        material.Add(new MaterialList(80, 40));
        material.Add(new MaterialList(75, 15));

        //-----------------------------
        //настройка окна
        Window window = new Window();
        window.setVisible(true);
        window.setSize(1000, 700);
    }
}
