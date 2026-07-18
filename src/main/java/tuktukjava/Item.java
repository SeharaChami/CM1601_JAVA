package tuktukjava;

import java.util.*;

public class Item {
    String[] item;

    public Item(String[] line){
        this.item = line;
    }
    public String setName(String partName){
        return partName;
    }
    public String setBrand(String partBrand){
        return partBrand;
    }
    public String setPrice(String partPrice){
        double price = 0.0;
            try{
                price = Double.parseDouble(partPrice);
            }catch(NumberFormatException e){
                return null;
            }
            if(price > 0){
                partPrice = String.format("Rs %.2f",price);
            }else {
                return null;
            }
        return partPrice;
    }
    public String setQty(String qty){
        int partqty;
        try{
            partqty = Integer.parseInt(qty);
        }catch(NumberFormatException e){
            return  null;
        }
        if(partqty > 0){
            return qty;
        }else {
            return null;
        }
    }
    public String setField(String partField) {
        return partField;
    }
    public String setDate(String date){
        return date;
    }
    public String setImg(String img){
        boolean flag = true;
            if(imageValidate(img)==false){
                return null;
            }
            else return img;
    }
    public boolean imageValidate(String part){
        boolean flag = false;
        String temp = "";

        for(int i = 0; i < part.length(); i++){
            if (part.charAt(i) == '.'){
                flag = true;
            } else if (flag == true) {
                temp = temp+part.charAt(i);
            }
        }
        return List.of("png","jpg","jpeg").contains(temp.toLowerCase());
    }
}
