package tuktukjava;


import java.io.*;
import java.util.*;

public class dataCleaner {
    List<String> lineList = new ArrayList<>();
    List<Item> formattedList = new ArrayList<>();

    public dataCleaner(File myfile){

    }
    FileReader fileReader = new FileReader(myfile);

    BufferedReader reader = new BufferedReader(fileReader);
    String line;

            while ((line = reader.readLine()) != null) {
        lineList.add(line);
    }

            reader.close();
}
