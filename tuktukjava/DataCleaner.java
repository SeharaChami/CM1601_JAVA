package tuktukjava;

import java.io.*;
import java.util.*;

public class DataCleaner {
    List<String> lineList = new ArrayList<>();
    List<Item> formattedList = new ArrayList<>();

    public DataCleaner(File myfile){

        try{
            FileReader fileReader = new FileReader(myfile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                lineList.add(line);
            }
            reader.close();

        }catch (IOException e){
            System.out.println("File is not found..");
        }

        for (int i = 0; i < lineList.size(); i++) {
            Item item = new Item(lineList.get(i));
            System.out.println(item);
            formattedList.add(item);
        }
    }
}
