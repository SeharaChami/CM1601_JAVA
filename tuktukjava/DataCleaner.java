package tuktukjava;

import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.*;

public class DataCleaner {
    List<String> lineList = new ArrayList<>();
    List<String[]> formattedList = new ArrayList<>();

    public DataCleaner(File myfile,int fieldcount){

        try{
            FileReader fileReader = new FileReader(myfile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                lineList.add(line.trim());

            }
            reader.close();

        }catch (IOException e){
            System.out.println("File is not found..");
        }

        for (String line:lineList){
            String[] newLine = line.split("[;,|]", -1);
            if (newLine.length != fieldcount) {
                newLine = line.split(";", -1);
            }

            for (int j = 0 ; j < newLine.length;j++){
                newLine[j] = newLine[j].trim();
                if(newLine[j].isEmpty()){
                    newLine[j] = null;
                }
            }

            if(fieldcount == 8 ){
                try {
                    String sprice = priceFormat(newLine[3]);
                    double price = Double.parseDouble(sprice);
                    newLine[3] = String.format("Rs %.2f",price);

                }catch (NumberFormatException | IllegalFormatConversionException e ) {
                    System.out.println("couldn't format price value..");
                }
                newLine[6] = String.valueOf(dateFormat(newLine[6]));

            }
            formattedList.add(newLine);
        }
    }

    public String priceFormat(String stringPrice){
        String finalPrice = "";
        boolean found = false;

        for(int j = 0;j < stringPrice.length();j++){
            char temp = stringPrice.charAt(j);
            if (Character.isDigit(temp) ){
                finalPrice = finalPrice + temp;
                found = true;

            } else if (temp == '.' && found == true){
                finalPrice = finalPrice + temp;
            }
        }
        return finalPrice;
    }
    public static LocalDate dateFormat(String date){
        String[] patterns = {"yyyy-MM-dd","dd/MM/yyyy","yyyy/MM/dd","dd-MM-yyyy","d-MMM-yyyy","MMM d, yyyy"};
        LocalDate newDate = null;

        for (String pattern : patterns) {
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
                newDate = LocalDate.parse(date,formatter);
                return newDate;
            }
            catch (DateTimeParseException e){
            }
        }
        return null;
    }

    public List<Item> returnItems(){
        List<Item> formattedItems = new ArrayList<>();

        try {
            for (int i = 0; i < formattedList.size(); i++) {
                String[] tempList = new String[8];
                for (int j = 0; j < 8; j++) {
                    tempList[j] = formattedList.get(i)[j];
                }
                Item item = new Item(tempList);
                formattedItems.add(item);
            }
        } catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("System issue identified..");
        }
        return formattedItems;
    }

    public List<Dealer> returnDealers(){
        List<Dealer> formattedDealers = new ArrayList<>();

        try {
            for (int i = 0; i < formattedList.size(); i++) {
                String[] tempList = new String[4];
                for (int j = 0; j < 4; j++) {
                    tempList[j] = formattedList.get(i)[j];
                }
                Dealer dealer = new Dealer(tempList);
                formattedDealers.add(dealer);
            }
        } catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("System issue identified..");
        }
        return formattedDealers;
    }

}
