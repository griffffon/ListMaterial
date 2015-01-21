package sapr.listmaterials;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grigoriy on 07.01.2015.
 */
public class Data {
    public static boolean ReadFromFile(Model model, String path) {
        BufferedReader reader = null;
        String line;
        List<String> lines = new ArrayList<String>();
        FileInputStream inF = null;
        int [] nn;

        try {
            inF = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //reader = new BufferedReader(new FileReader(path), "UTF-8");
        try {
            reader = new BufferedReader(new InputStreamReader(inF,"Windows-1251"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            if((line = reader.readLine()) != null) {
                line = PrepareString(line);
                model.Name = line;
            }

            if((line = reader.readLine()) != null) {
                line = PrepareString(line);
                model.ServiceInfo = line;
            }

            if((line = reader.readLine()) != null) {
                line = PrepareString(line);
                model.Count = Integer.parseInt(line);
                nn = new int[model.Count];
            }

            for(int i = 0; i < model.Count; i++) { //добавляем пустые модели
                model.Details.add(new Detail());
            }

            for(int i = 0; i < model.Count; i++) {
                if((line = reader.readLine()) != null) {
                    line = PrepareString(line);
                    model.Details.get(i).Name = line;
                }
            }

            nn = new int[model.Count];
            for(int i = 0; i < model.Count; i++) {
                if((line = reader.readLine()) != null) {
                    line = PrepareString(line);
                    model.Details.get(i).Count = Integer.parseInt(line.split(" ")[0]);
                    model.Details.get(i).Demand = Integer.parseInt(line.split(" ")[1]);
                    nn[i] = model.Details.get(i).Count;
                }
            }

            for(int i = 0; i < model.Count; i++) {
                for (int j = 0; j < nn[i]; j++) {
                    if ((line = reader.readLine()) != null) {
                        line = PrepareString(line);
                        double x = Double.parseDouble(line.split(" ")[0]);
                        double y = Double.parseDouble(line.split(" ")[1]);
                        if(model.Details.get(i).Count != nn[i]) continue;
                        if((x == 999.00) && (y == 999.00)) {
                            model.Details.get(i).Count = j;
                            continue;
                        }
                        model.Details.get(i).Points.add(new Point2D.Double(x, y));
                    }
                    nn[i] = model.Details.get(i).Count;
                }
                model.Details.get(i).bypass(); //обход точек детали. они должны ити против часовой стрелки
                //TODO: раскоментить
                /*double xc = (model.Details.get(i).getMaxX() + model.Details.get(i).getMinX()) / 2;
                double yc = (model.Details.get(i).getMaxY() + model.Details.get(i).getMinY()) / 2;
                for(int j = 0; j < model.Details.get(i).Count; j++) {
                    model.Details.get(i).Points.set(j, new Point2D.Double(model.Details.get(i).Points.get(j).getX() - xc, model.Details.get(i).Points.get(j).getY() - yc));
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String PrepareString(String str) {
        str = str.trim();
        StringBuilder newStr = new StringBuilder();

        for(int i = 0; i < str.length()-1; i++) {
            char curr, next;
            curr = str.charAt(i);
            next = str.charAt(i+1);
            if((curr == ' ') && (next == ' ')) {
                continue;
            }
            else {
                newStr.append(curr);
                if(i == str.length()-2) newStr.append(next);
            }
        }

        if((str.length() == 1) && (!str.equals(" "))) {
            return str;
        }

        return newStr.toString();
    }
}
