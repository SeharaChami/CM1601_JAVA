package tuktukjava;

import java.util.*;
import tuktukjava.DataCleaner;

public class Item {
    String[] item;
    Scanner input = new Scanner(System.in);

    public Item(String[] line){
        this.item = line;
    }
    public String setName(){
        String partName;
        do {
            System.out.print("Enter part name : ");
            partName = input.nextLine();
        }while (partName.isEmpty());
        return partName;
    }
    public String setBrand(){
        String partBrand;
        do {
            System.out.print("Enter part brand : ");
            partBrand = input.nextLine();
        }while (partBrand.isEmpty());
        return partBrand;
    }
    public String setPrice(){
        double price = 0.0;
        String partPrice = "";
        while (true){
            System.out.print("Enter price : ");
            try{
                price = input.nextDouble();
                if(price > 0){
                    partPrice = String.format("Rs %.2f",price);
                    break;
                }else {
                    System.out.println("Provide a number greater than 0..");
                }
            }catch(InputMismatchException e){
                System.out.println("Provide a valid price value..");
                input.nextLine();
            }
        }
        return partPrice;
    }
    public String setQty(){
        int qty = 0;
        String partqty;
        while(true){
            System.out.print("Enter quantity : ");
            try{
                qty = input.nextInt();
                if(qty > 0){
                    break;
                }else {
                    System.out.println("Provide a number greater than 0..");
                }
            }catch(InputMismatchException e){
                System.out.println("Provide a valid quantity value");
                input.nextLine();
            }
        }
        partqty = Integer.toString(qty);
        return partqty;
    }
    public String setField() {
        String partField;
        do {
            System.out.print("Enter part field : ");
            partField = input.nextLine();
        } while (partField.isEmpty());
        return partField;
    }
    public String setDate(){
        String date;
        do {
            System.out.print("Enter date (YYYY-MM-DD) : ");
            date = input.next();
            String newDate = String.valueOf(DataCleaner.dateFormat(date));
        }while (date.isEmpty());
        return date;
    }
    public String setImg(){
        String img = "";
        boolean flag = true;
        while (flag){
            System.out.print("Enter image : ");
            img = input.next();
            if(imageValidate(img)==false){
                System.out.println("Provide only png or jpg file..");
            }else {
                flag = false;
            }
        }
        return img;
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
