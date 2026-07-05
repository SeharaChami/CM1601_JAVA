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
        for(Item element : formattedList){
            for (String part : element.item){
                System.out.print(part+"|");
            }
            System.out.println();
        }
    }
    public void add(){
        Item item;
        String[] part = new String[8];
        part[0] = generateItemCode();
        System.out.println("Your item code is "+part[0]);

        String partName;
        do {
            System.out.print("Enter part name : ");
            partName = scanner.nextLine();
        }while (partName.isEmpty());
        part[1] = partName;

        String partBrand;
        do {
            System.out.print("Enter part brand : ");
            partBrand = scanner.nextLine();
        }while (partBrand.isEmpty());
        part[2] = partBrand;

        double partPrice = 0.0;
        String price = "";
        while (true){
            System.out.print("Enter price : ");
            try{
                partPrice = scanner.nextDouble();
                if(partPrice > 0){
                    price = String.format("Rs %.2f",partPrice);
                    break;
                }else {
                    System.out.println("Provide a number greater than 0..");
                }
            }catch(InputMismatchException e){
                System.out.println("Provide a valid price value..");
                scanner.nextLine();
            }
        }
        part[3] = price;

        int qty = 0;
        while(true){
            System.out.print("Enter quantity : ");
            try{
                qty = scanner.nextInt();
                if(qty > 0){
                    break;
                }else {
                    System.out.println("Provide a number greater than 0..");
                }
            }catch(InputMismatchException e){
                System.out.println("Provide a valid quantity value");
                scanner.nextLine();
            }
        }
        part[4] = Integer.toString(qty);

        String partField;
        do{
            System.out.print("Enter part field : ");
            partField = scanner.nextLine();
        }while (partField.isEmpty());
        part[5] = partField;

        String date;
        do {
            System.out.print("Enter date (YYYY-MM-DD) : ");
            date = scanner.next();
        }while (date.isEmpty());
        part[6] = date;

        String img = "";
        boolean flag = true;
        while (flag){
            System.out.print("Enter image : ");
            img = scanner.next();
            if(imageValidate(img)==false){
                System.out.println("Provide only png or jpg file..");
            }else {
                flag = false;
            }
        }
        part[7] = img;
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

    public boolean imageValidate(String part){
        boolean flag = false;
        String temp = "";
        
        for(int i = 0; i < part.length(); i++){
            if (part.charAt(i) == '.'){
                flag = true;
            } else if (flag == true) {
                temp = temp+part.charAt(i);
            }
        }
        return List.of("png","jpg","jpeg").contains(temp.toLowerCase());
    }

}
