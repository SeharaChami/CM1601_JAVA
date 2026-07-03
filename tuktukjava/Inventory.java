package tuktukjava;

import java.io.*;
import java.util.*;

public class Inventory {

    Scanner scanner = new Scanner(System.in);
    File itemfile = new File("inventory_legacy.txt");
    DataCleaner dataset = new DataCleaner(itemfile);
    List<Item> formattedList = new ArrayList<>();
    public Inventory() {
        this.formattedList = dataset.returnItems();

    }

    public void displayInventory(){
        for(int i = 0;i < formattedList.size();i++){
            for (int j = 0;j< formattedList.get(i).item.length;j++){
                System.out.print(formattedList.get(i).item[j]);
            }
            System.out.println();
        }
    }
    public void Add(){
        String[] part = new String[8];
        part[0] = generateItemCode();

        String partName;
        do {
            System.out.println("Enter part name : ");
            partName = scanner.nextLine();
        }while (partName.isEmpty());
        part[1] = partName;

        String partBrand;
        do {
            System.out.println("Enter part brand : ");
            partBrand = scanner.nextLine();
        }while (partBrand.isEmpty());
        part[2] = partBrand;

        Double partPrice = 0.0;
        do{
            try{
                System.out.println("Enter price : ");
                partPrice = scanner.nextDouble();
            }catch(InputMismatchException e){
                System.out.println("Provide a valid price value");
            }
        }while (partPrice == 0.0);
        String price = String.format("Rs %.2f",partPrice);
        part[3] = price;

        Integer qty = 0;
        do{
            try{
                System.out.println("Enter quantity : ");
                qty = scanner.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Provide a valid quantity value");
            }
        }while (qty != 0);
        part[4] = Integer.toString(qty);

        String partField;
        do{
            System.out.println("Enter part field : ");
            partField = scanner.nextLine();
        }while (partField.isEmpty());
        part[5] = partField;

        String date;
        do {
            System.out.println("Enter date (YYYY-MM-DD) : ");
            date = scanner.next();
        }while (date.isEmpty());

        String img;
        do {
            System.out.println("Enter image : ");
            img = scanner.next();
        }while (img.isEmpty());

    }

    public String generateItemCode(){
        String itemCode = "";
        Integer last = formattedList.size()-1;
        String lastCode = formattedList.get(last).item[0];
        System.out.println(lastCode);
        return itemCode;
    }


}
