package tuktukjava;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.IllegalFormatConversionException;
import java.util.List;
import java.util.Locale;

public class InventoryCleaner extends DirtyDataCleaner{

    public InventoryCleaner(File myfile, int fieldcount) {
        super(myfile, fieldcount);
    }

    @Override
    public void validateRow(String[] newLine) {
        try {
            String sprice = priceFormat(newLine[3]);
            double price = Double.parseDouble(sprice);
            newLine[3] = String.format("Rs %.2f",price);

        }catch (NumberFormatException | IllegalFormatConversionException e ) {
            System.out.println("couldn't format price value..");
        }
        newLine[6] = String.valueOf(dateFormat(newLine[6]));
        newLine[5] = newLine[5].toUpperCase();
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

}
