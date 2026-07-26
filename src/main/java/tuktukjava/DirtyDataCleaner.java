package tuktukjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DirtyDataCleaner {
    List<String> lineList = new ArrayList<>();
    List<String[]> formattedList = new ArrayList<>();

    public  DirtyDataCleaner(File myfile,int fieldcount) {
        try {
            FileReader fileReader = new FileReader(myfile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                lineList.add(line.trim());

            }
            reader.close();

        } catch (IOException e) {
            System.out.println("File is not found..");
        }

        for (String line : lineList) {
            String[] newLine = line.split("[;,|]", -1);
            if (newLine.length != fieldcount) {
                newLine = line.split(";", -1);
            }
            if (newLine.length != fieldcount) {
                System.out.println("Skipped" + line);
                continue;
            }
            for (int j = 0; j < newLine.length; j++) {
                newLine[j] = newLine[j].trim();
                if (newLine[j].isEmpty()) {
                    newLine[j] = "N/A";
                }
            }
            validateRow(newLine);
            formattedList.add(newLine);
        }
    }
    public abstract void validateRow(String[] newLine);

}
