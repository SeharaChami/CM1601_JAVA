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
    public void add(Item newItem) throws IOException {
        Item item = newItem;
        formattedList.add(item);
        saveItem(item);
        String msg = item.getCode()+" added to the inventory";
        saveToAuditLog(item,msg);
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
    public Item searchByCode(String code){
        for (Item item:formattedList){
            if(item.item[0] != null && item.item[0].trim().equalsIgnoreCase(code)){
                return item;
            }
        }
        return null;
    }
    public void update(Item item) throws IOException {
        FileManager.saveItems(formattedList);
        saveToAuditLog(item,item.getCode()+" updated");
    }
    public String generateItemCode() {
        String itemCode = "";
        if (formattedList.isEmpty()) {
            return "P001";
        } else {
            int last = formattedList.size() - 1;
            String lastCode = formattedList.get(last).item[0];
            String temp = "";
            boolean flag = false;
            for (int i = 1; i < lastCode.length(); i++) {
                temp = temp + lastCode.charAt(i);
            }
            last = (Integer.parseInt(temp) + 1);
            if (last > 9) {
                itemCode = "P0" + last;
            } else itemCode = "P00" + last;
            return itemCode;
        }
    }

    public void saveItem(Item item) throws IOException {
        FileWriter writer = new FileWriter(FileManager.cleanItemRoot,true);
        for (String detail:item.item){
            writer.write(detail+"|");
        }
        writer.write("\n");
        writer.close();
    }
    public static void saveToAuditLog(Item item,String msg) throws IOException {
        String root = "audit_log.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(root,true));
        for(String detail : item.item){
            writer.write(detail);
        }
        writer.write(msg);
        writer.close();
    }
}
