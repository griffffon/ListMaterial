package sapr.listmaterials;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Grigoriy on 27.01.2015.
 */
public class MyPanel extends JPanel {
    public static int x_center, y_center;
    Model model;

    MyPanel(Model m) {
        model = m;
    }

    @Override
    public void paintComponent(Graphics g) {
        x_center = this.getWidth() / 2;
        y_center = this.getHeight() / 2;
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);


    }
}
