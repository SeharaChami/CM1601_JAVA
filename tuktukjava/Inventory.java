package tuktukjava;

import java.io.*;
import java.util.*;

public class Inventory {

    Scanner scanner = new Scanner(System.in);

    DataCleaner dataset;
    List<Item> formattedList = new ArrayList<>();
    int fieldcount = 8 ;
    public Inventory(File itemfile) {

        dataset =  new DataCleaner(itemfile,fieldcount);
        this.formattedList = dataset.returnItems();

    }

    public void getInventory(){
        int itemcount = 0;
        for(Item element : formattedList){
            for (String part : element.item){
                System.out.print(part+"|");
            }
            itemcount += Integer.parseInt(element.item[4]);
            System.out.println();
        }
        System.out.println("Number of items : "+itemcount);
    }
    public void add(){
        String[] nullArr = {"null","null","null","null","null","null","null","null"};
        Item item = new Item(null);
        String[] part = new String[8];
        part[0] = generateItemCode();
        System.out.println("Your item code is "+part[0]);

        part[1] = item.setName();
        part[2] = item.setBrand();
        part[3] = item.setPrice();
        part[4] = item.setQty();
        part[5] = item.setField();
        part[6] = item.setDate();
        part[7] = item.setImg();
        item = new Item(part);
        formattedList.add(item);
    }

    public void delete(String code){
        for (Item element : formattedList){
            if (element.item[0].equals(code)){
                formattedList.remove(element);
                break;
            }
        }
    }
    public void lowStockMon(){
        int limit = 10;
        for(Item element : formattedList){
            int qty = Integer.parseInt(element.item[4].trim());
            if(qty < limit && qty > 0){
                System.out.println(element.item[0]);
            }
        }
    }
    public void Update(){

    }
    public String generateItemCode(){
        String itemCode = "";
        int last = formattedList.size()-1;
        String lastCode = formattedList.get(last).item[0];
        String temp = "";
        boolean flag = false;
        for (int i = 1; i < lastCode.length();i++){
            temp = temp+lastCode.charAt(i);
        }
        last = (Integer.parseInt(temp)+1);
        if(last > 9){
            itemCode = "P0"+last;
        }else itemCode = "P00"+last;
        return itemCode;
    }

}
