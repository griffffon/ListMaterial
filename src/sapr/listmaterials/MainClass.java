package sapr.listmaterials;

/**
 * Created by Grigoriy on 07.01.2015.
 */
public class MainClass {
    public static void main(String [] args) {
        //создание модели
        Model model = new Model();
        Data.ReadFromFile(model, "D:\\WORKSPACES\\IDEA Workspace\\List Material\\MyModel1.dgt");
        for(int i = 0; i < model.Count; i++) {
            model.Details.get(i).getBorders();
        }

        //создание материала
        Material material = new Material();
        material.Add(new MaterialList(100, 120));
        material.Add(new MaterialList(80, 40));
        material.Add(new MaterialList(75, 15));

        int a = 0;
    }
}
