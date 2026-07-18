package tuktukjava;

import java.io.*;
import java.util.*;

public class Inventory {

    List<Item> formattedList = new ArrayList<>();
    public Inventory(List<Item> items) throws IOException {

        this.formattedList = items;
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
    public void add(String name,String brand,String price,String field,String quantity,String date,String img) throws IOException {
        Item item = new Item(null);
        String[] part = new String[8];
        part[0] = generateItemCode();
        part[1] = item.setName(name);
        part[2] = item.setBrand(brand);
        part[3] = item.setPrice(price);
        part[4] = item.setQty(quantity);
        part[5] = item.setField(field);
        part[6] = item.setDate(date);
        part[7] = item.setImg(img);
        item = new Item(part);
        formattedList.add(item);
        saveItem(item);
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
        List<Item> lowItems = null;
        int limit = 10;
        for(Item element : formattedList){
            int qty = Integer.parseInt(element.item[4].trim());
            if(qty < limit && qty > 0){
                System.out.println(element.item[0]);
            }
            lowItems.add(element);
        }
    }
//    public void Update(){
//        System.out.println("Enter item code of the relevant item..");
//        String code = input.nextLine();
//        for(Item element : formattedList){
//            if (code.equals(element.item[0])){
//                System.out.println("enter field..");
//                String part = input.nextLine().toLowerCase();
//                switch (part) {
//                    case "name":
//                        System.out.println("name : ");
//                        String name = element.setName();
//                        element.item[1] = name;
//                    case "brand":
//                        System.out.println("brand : ");
//                        String brand = element.setBrand();
//                        element.item[2] = brand;
//                    case "price":
//                        System.out.println("brand : ");
//                        String price = element.setPrice();
//                        element.item[3] = price;
//                    case "quantity":
//                        System.out.println("quantity : ");
//                        String qty = element.setQty();
//                        element.item[4] = qty;
//                    case "field":
//                        System.out.println("field : ");
//                        String field = element.setField();
//                        element.item[5] = field;
//                    case "date":
//                        System.out.println("brand : ");
//                        String date = element.setDate();
//                        element.item[6] = date;
//                    case "img":
//                        System.out.println("brand : ");
//                        String img = element.setImg();
//                        element.item[7] = img;
//                }
//            }
//        }
//    }
    public String generateItemCode(){
        String itemCode = "";
        int last = formattedList.size()-1;
        String lastCode = formattedList.get(last).item[0];
        String temp = "";
        boolean flag = false;
        for (int i = 1; i < lastCode.length();i++){
            temp = temp + lastCode.charAt(i);
        }
        last = (Integer.parseInt(temp)+1);
        if(last > 9){
            itemCode = "P0"+last;
        }else itemCode = "P00"+last;
        return itemCode;
    }

    public void saveItem(Item item) throws IOException {
        FileWriter writer = new FileWriter(FileManager.cleanItemRoot,true);
        for (String detail:item.item){
            writer.write(detail+"|");
        }
        writer.write("\n");
        writer.close();
    }
}
