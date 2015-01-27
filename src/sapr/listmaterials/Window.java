package sapr.listmaterials;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Grigoriy on 15.01.2015.
 *
 * Интерфейс временный, для отладочных целей
 */
public class Window extends JFrame {
    public Model model;
    public Layout layout;

    public static void main(String[] args) {
        new Window();
    }

    public Window() {
        super("Paint");

        //создание модели
        model = new Model();
        Data.ReadFromFile(model, "D:\\WORKSPACES\\IDEA Workspace\\List Material\\MyModel1.dgt");
        for (int i = 0; i < model.Count; i++) {
            model.Details.get(i).getBorders();
        }

        //создание материала
        Material material = new Material();
        material.Add(new MaterialList(100, 120));
        material.Add(new MaterialList(80, 40));
        material.Add(new MaterialList(75, 15));

        layout = new Layout(model.Details.get(0), 1000, 1000, 1);
        layout.CombinationRectsX();

        paint(this.getGraphics());
    }

    public void paint(Graphics g) {
        super.paint(g);
        //-----------------------------
        //настройка окна
        setVisible(true);
        setSize(1000, 700);

        for(int i = 0; i <model.Count; i++) {
            //model.Details.get(i).paint(this);
            layout.paint(this);
        }
    }
}
