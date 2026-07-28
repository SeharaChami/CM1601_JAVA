package tuktukjava;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
        String msg = " added to the inventory";
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
            saveToAuditLog(item, " deleted");
        }
    }
    public void update(Item item) throws IOException {
        FileManager.saveItems(formattedList);
        saveToAuditLog(item," updated");
    }
    public Item searchByCode(String code){
        for (Item item:formattedList){
            if(item.item[0] != null && item.item[0].trim().equalsIgnoreCase(code)){
                return item;
            }
        }
        return null;
    }
    public List<Item> getLowStockItems() {
        List<Item> lowItems = new ArrayList<>();
        for (Item item : formattedList) {
            int qty = 0;
            int threshold = 10;
            try { qty = Integer.parseInt(item.item[4].trim()); } catch (NumberFormatException ignored) {}
            if (item.item.length > 8 && item.item[8] != null && !item.item[8].trim().equals("null")) {
                try { threshold = Integer.parseInt(item.item[8].trim()); } catch (NumberFormatException ignored) {}
            }
            if (qty < threshold) {
                lowItems.add(item);
            }
        }
        return lowItems;
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
        writer.write(String.join("|",item.item)+"\n");
        writer.close();
    }
    public static void saveToAuditLog(Item item,String msg) throws IOException {
        LocalTime currentTime = LocalTime.now();
        LocalDate date = LocalDate.now();
        String root = "audit_log.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(root,true));
        writer.write(item.item[0]+"->"+msg+" : "+currentTime+" on "+date+"\n");
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
                    String code1Str = categoryList.get(j).item[0];
                    String code2Str = categoryList.get(j+1).item[0];

                    if (code1Str == null || code2Str == null) continue;

                    int code1 = Integer.parseInt(code1Str.substring(1));
                    int code2 = Integer.parseInt(code2Str.substring(1));

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
    public List<Item> search(String name, String brand, String category, double minPrice, double maxPrice, int minQty) {
        List<Item> results = new ArrayList<>();

        for (Item item : formattedList) {
            boolean nameMatch = false;
            boolean brandMatch = false;
            boolean categoryMatch = false;
            if(name.isEmpty() || item.item[1].toLowerCase().contains(name)){
                nameMatch =true;
            }
            if(brand.isEmpty() || item.item[2].toLowerCase().contains(brand)){
                brandMatch = true;
            }
            if(category.isEmpty() || item.item[5].toLowerCase().contains(category)){
                categoryMatch = true;
            }

            double itemPrice = 0;
            int itemQty = 0;

            String priceStr = "";
            boolean found = false;
            for (int i = 0; i < item.item[3].length(); i++) {
                char c = item.item[3].charAt(i);
                if (Character.isDigit(c)) {
                    priceStr = priceStr + c;
                    found = true;
                }
                else if (c == '.' && found) {
                    priceStr = priceStr + c;
                }
            }

            try {
                itemPrice = Double.parseDouble(priceStr);
            } catch (NumberFormatException ignored) {}
            try {
                itemQty = Integer.parseInt(item.item[4].trim());
            } catch (NumberFormatException ignored) {}

            boolean priceMatch = (itemPrice >= minPrice && itemPrice <= maxPrice);
            boolean qtyMatch = (itemQty >= minQty);

            if (nameMatch && brandMatch && categoryMatch && priceMatch && qtyMatch) {
                results.add(item);
            }
        }

        return results;
    }

    public List<Item> getFormattedList() {
        return formattedList;
    }
}
