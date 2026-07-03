package tuktukjava;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Item {
    String[] item;


    public Item(String[] line){
        this.item = line;
    }

    public void LowStockMon(){
        int limit = 10;
        int qty = Integer.parseInt(item[4].trim());
        if(qty < limit && qty > 0){
            System.out.println(item[0]);
        }
    }
    public void Delete(){

    }
    public void Update(){

    }

}
