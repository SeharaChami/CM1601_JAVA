package tuktukjava;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    File itemfile = new File("inventory_legacy.txt");
    DataCleaner dataset = new DataCleaner(itemfile);
    List<Item> formattedList = new ArrayList<>();
    public Inventory() {

        this.formattedList = dataset.returnItem();

        for(int j = 0;j < 6;j++){}
    }

    public void displayInventory(){

    }
}
