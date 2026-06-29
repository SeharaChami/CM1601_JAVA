package tuktukjava;

import java.io.*;
import java.util.*;

public class DataCleaner {
    List<String> lineList = new ArrayList<>();
    List<String[]> formattedList = new ArrayList<>();

    public DataCleaner(File myfile){

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
        for (int i = 0; i< lineList.size();i++){
            String[] newLine = lineList.get(i).trim().split("[;,|]");

            if(newLine.length == 8){
                try {
                    String sprice = priceFormat(newLine[3]);
                    double price = Double.parseDouble(sprice);
                    newLine[3] = String.format("Rs %.2f",price);

                }catch (NumberFormatException | IllegalFormatConversionException e ) {
                    System.out.println("no price value..");
                }
            }for (int j = 0 ; j< newLine.length;j++){
                if(newLine[j].equals(" ")){
                    newLine[j] = "NoData";
                }
            }
            formattedList.add(newLine);
        }
        for (int j = 0 ; j<formattedList.size();j++){
            System.out.println(formattedList.get(j)[1]);
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

    public List<Item> returnItem(){
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
            System.out.println("Index issue..");
        }
        return formattedItems;
    }

}
