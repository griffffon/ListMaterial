package sapr.listmaterials;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 21.01.2015.
 */
public class Borders {
    public int numTop, numBottom, numLeft, numRight;
    public List<Point2D.Double> top;
    public List<Point2D.Double> bottom;
    public List<Point2D.Double> left;
    public List<Point2D.Double> right;

    Borders() {
        numBottom = 0;
        numLeft = 0;
        numRight = 0;
        numTop = 0;

        top = new ArrayList<Point2D.Double>();
        bottom = new ArrayList<Point2D.Double>();
        left = new ArrayList<Point2D.Double>();
        right = new ArrayList<Point2D.Double>();
    }
}
