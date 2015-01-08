package sapr.listmaterials;

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
                        model.Details.get(i).X.add(j, x);
                        model.Details.get(i).Y.add(j, y);
                    }
                }
                //nn[i] = model.Details.get(i).Count;



                /*for(int j = 0; j < model.Details.get(i).Count; j++) {
                    double Xmax, Xmin, Ymax, Ymin;
                    Xmax = Max(model.Details.get(i).X);
                    Xmin = Min(model.Details.get(i).X);
                    Ymax = Max(model.Details.get(i).Y);
                    Ymin = Min(model.Details.get(i).Y);
                    double Xc = (Xmax + Xmin) / 2;
                    double Yc = (Ymax + Ymin) / 2;

                    model.Details.get(i).X.set(j, model.Details.get(i).X.get(j) - Xc);
                    model.Details.get(i).Y.set(j, model.Details.get(i).Y.get(j) - Yc);
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static double Max(List<Double> list) {
        double max = list.get(0);
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) > max) {
                max = list.get(i);
            }
        }
        return max;
    }

    private static double Min(List<Double> list) {
        double min = list.get(0);
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) < min) {
                min = list.get(i);
            }
        }
        return min;
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

        return newStr.toString();
    }
}
