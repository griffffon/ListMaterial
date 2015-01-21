package sapr.listmaterials;

/**
 * Created by Grigoriy on 15.01.2015.
 */
import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {
    public static int x0, y0; //начало координат. соответствует левому нижнему углу панели
    public int mxy = 10; //масштабирование
    public Color bgrColor = Color.WHITE;
    private Layout layout;

    //задаем раскладку, которую хотим отрисовать
    public void setLayout(Layout l) {
        layout = l;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        x0 = 0;
        y0 = this.getHeight();
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.WHITE);
        g2d.setColor(Color.BLACK);

        if(layout != null) {

        }
    }
}

