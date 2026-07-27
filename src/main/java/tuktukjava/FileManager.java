package tuktukjava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static String cleanItemRoot = "src/main/resources/inventory_clean.txt";
    private static String legacyItemRoot = "inventory_legacy.txt";
    public static String cleanDealerRoot = "src/main/resources/dealer_clean.txt";
    private static String legacyDealerRoot = "dealers_legacy.txt";
    public static List<Item> loadItems() throws IOException {
        File cleanFile = new File(cleanItemRoot);

        if (cleanFile.exists() && cleanFile.length() > 0) {
            return readCleanItemFile(cleanFile);
        } else {
            File legacyFile = new File(legacyItemRoot);

            if (!legacyFile.exists()) {
                return new ArrayList<>();
            }

            InventoryCleaner cleaner = new InventoryCleaner(legacyFile, 8);
            List<Item> items = cleaner.returnItems();

            saveItems(items);
            return items;
        }
    }

    public static List<Dealer> loadDealers() throws IOException {
        File cleanFile = new File(cleanDealerRoot);
        if (cleanFile.exists() && cleanFile.length()>0){
            return readCleanDealerFile(cleanFile);
        }else{
            DealerCleaner cleaner = new DealerCleaner(new File(legacyDealerRoot),4);
            List<Dealer> dealers = cleaner.returnDealers();
            saveDealers(dealers);
            return dealers;
        }
    }
    public static List<Item> readCleanItemFile(File file) throws IOException {
        List<Item> items = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 8) {
                if (parts.length == 8) {
                    String[] temp = new String[9];
                    for (int i = 0; i < 8; i++) {
                        temp[i] = parts[i];
                    }
                    temp[8] = "10";
                    parts = temp;
                }
            }
            if (parts.length == 9) {
                items.add(new Item(parts));
            }
        }
        reader.close();
        return items;
    }
    public static List<Dealer> readCleanDealerFile(File file) throws IOException {
        List<Dealer> dealers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while((line = reader.readLine())!=null ){
            String[] parts = line.split("\\|",-1);
            if(parts.length == 4){
                dealers.add(new Dealer(parts));
            }
        }
        return dealers;
    }
    public static void saveItems(List<Item> items) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(cleanItemRoot));
        for (Item item : items) {
            writer.write(String.join("|", item.item));
            writer.newLine();
        }
        writer.close();
    }
    public static void saveDealers(List<Dealer> dealers) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(cleanDealerRoot));
        for (Dealer dealer : dealers) {
            writer.write(String.join("|", dealer.dealer));
            writer.newLine();
        }
        writer.close();

    }
}
