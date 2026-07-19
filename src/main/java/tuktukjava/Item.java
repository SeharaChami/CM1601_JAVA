package tuktukjava;

import java.util.*;

public class Item {
    String[] item;
    private String code;
    private String name;
    private String brand;
    private String pirce;
    private String qty;
    private String field;
    private String date;
    private String img;

    public Item(String[] line){
        this.item = line;
    }


    public void setItem(String[] item) {
        this.item = item;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(String price) {
        this.pirce = pirce;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCode() {
        return code;
    }

    public String getName(String partName){
        return partName;
    }
    public String getBrand(String partBrand){
        return partBrand;
    }
    public String getPrice(String partPrice){
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
    public String getQty(String qty){
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
    public String getField(String partField) {
        return partField;
    }
    public String getDate(String date){
        return date;
    }
    public String getImg(String img){
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
