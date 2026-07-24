package tuktukjava;

import java.io.*;
import java.util.*;

public class Inventory {

    private List<Item> formattedList = new ArrayList<>();
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

    public void delete(Item item) throws IOException {
        Item toRemove = null;

        for (Item example : formattedList) {
            if (example.item[0].equalsIgnoreCase(item.item[0])) {
                toRemove = example;
            }
        }
        if (toRemove != null) {
            formattedList.remove(toRemove);
            FileManager.saveItems(formattedList);
            saveToAuditLog(item, item.item[0] + " deleted");
        }
    }
    public void update(Item item) throws IOException {
        FileManager.saveItems(formattedList);
        saveToAuditLog(item,item.getCode()+" updated");
    }
    public Item searchByCode(String code){
        for (Item item:formattedList){
            if(item.item[0] != null && item.item[0].trim().equalsIgnoreCase(code)){
                return item;
            }
        }
        return null;
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
    public List<List<Item>> getItemsByCategory() {
        List<String> categories = new ArrayList<>();
        List<List<Item>> result = new ArrayList<>();

        for (Item item : formattedList) {
            String category = item.item[5].trim().toUpperCase();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }

        for (String category : categories) {
            List<Item> categoryList = new ArrayList<>();

            for (Item item : formattedList) {
                if (item.item[5].trim().toUpperCase().equals(category)) {
                    categoryList.add(item);
                }
            }

            for (int i = 0; i < categoryList.size() - 1; i++) {
                for (int j = 0; j < categoryList.size() - 1 - i; j++) {
                    int code1 = Integer.parseInt(categoryList.get(j).item[0].substring(1));
                    int code2 = Integer.parseInt(categoryList.get(j+1).item[0].substring(1));
                    if (code1 > code2) {
                        Item temp = categoryList.get(j);
                        categoryList.set(j, categoryList.get(j + 1));
                        categoryList.set(j + 1, temp);
                    }
                }
            }
            result.add(categoryList);
        }
        return result;
    }
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        for (Item item : formattedList) {
            String category = item.item[5].trim().toUpperCase();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        return categories;
    }
}
