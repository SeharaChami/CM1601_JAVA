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
            // clean file exists — read it directly
            System.out.println("Reading from clean file...");
            return readCleanItemFile(cleanFile);
        } else {
            // first run — clean the legacy file
            System.out.println("Clean file not found, reading legacy file...");
            File legacyFile = new File(legacyItemRoot);

            if (!legacyFile.exists()) {
                System.out.println("Legacy file not found at: " + legacyFile.getAbsolutePath());
                return new ArrayList<>(); // return empty list, don't crash
            }

            DataCleaner cleaner = new DataCleaner(legacyFile, 8);
            List<Item> items = cleaner.returnItems();
            System.out.println("Cleaned items count: " + items.size());

            saveItems(items); // write to clean file
            return items;
        }
    }

    public static List<Dealer> loadDealers() throws IOException {
        File cleanFile = new File(cleanDealerRoot);
        if (cleanFile.exists()){
            return readCleanDealerFile(cleanFile);
        }else{
            DataCleaner dataCleaner = new DataCleaner(new File(legacyDealerRoot),4);
            List<Dealer> dealers = dataCleaner.returnDealers();
            saveDealers(dealers);
            return dealers;
        }
    }
    public static List<Item> readCleanItemFile(File file) throws IOException {
        List<Item> items = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // skip blank lines
            String[] parts = line.split("\\|", -1);
            if (parts.length == 8) {
                items.add(new Item(parts));
            } else {
                System.out.println("Skipping line with " + parts.length + " fields: " + line);
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
            if(parts.length == 8){
                dealers.add(new Dealer(parts));
            }
        }
        return dealers;
    }
    public static void saveItems(List<Item> items) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(cleanItemRoot));
        for (Item item : items) {
            writer.write(String.join("|", item.item)); // join with | no backslash
            writer.newLine();
        }
        writer.close();
    }
    public static void saveDealers(List<Dealer> dealers) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(cleanDealerRoot));
        for (Dealer dealer : dealers) {
            writer.write(String.join("|", dealer.dealer)); // join with | no backslash
            writer.newLine();
        }
        writer.close();

    }
}
