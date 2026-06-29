package tuktukjava;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    DataCleaner dataset;
    List<Item> formattedList = new ArrayList<>();
    public Inventory(DataCleaner dataset) {
        this.dataset = dataset;
        System.out.println(dataset);

        this.formattedList = dataset.returnItem();

        for(int j = 0;j < 6;j++){}
    }

    public void displayInventory(){

    }
}
