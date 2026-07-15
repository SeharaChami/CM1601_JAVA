package tuktukjava.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tuktukjava.Inventory;
import tuktukjava.Item;

import java.io.File;

public class InventoryController {
    File itemfile = new File("inventory_legacy.txt");
    Inventory inventory = new Inventory(itemfile);
    Item item = new Item(new String[]{});
    @FXML
    public Button addItem;
    public TextField brandField;
    public TextField priceField;
    public TextField fieldInput;
    public TextField quantityField;
    public TextField imgField;
    public TextField nameField;
    public Label errorLabel;
    public DatePicker datePicker;
    public Label nameLabel;
    public Label brandLabel;
    public Label priceLabel;
    public Label qtyLabel;
    public Label fieldLabel;
    public Label imgLabel;
    public Label dateLabel;
    String name;
    String brand;
    String price;
    String field;
    String quantity;
    String date;
    String img;


    public void onNameText(ActionEvent actionEvent) {
        if(nameField.getText().isEmpty()){
            nameLabel.setText("Name cannot be empty..");
        }
        else nameLabel.setText(null);
    }

    public void onBrandText(ActionEvent actionEvent) {
        if (brandField.getText().isEmpty()){
            brandLabel.setText("Brand cannot be empty..");
        }
        else brandLabel.setText(null);
    }

    public void onPriceText(ActionEvent actionEvent) {
        price = item.setPrice(priceField.getText());
        if(price == null){
            priceLabel.setText("Provide only numeric value greater that 0..");
        }
        else priceLabel.setText(null);
    }

    public void onQtyText(ActionEvent actionEvent) {
        quantity = item.setQty(quantityField.getText());
        if(quantity == null){
            qtyLabel.setText("Provide only numeric value greater that 0..");
        }
        else qtyLabel.setText(null);
    }

    public void onFieldText(ActionEvent actionEvent) {
        if (fieldInput.getText().isEmpty()) {
            fieldLabel.setText("Field cannot be empty..");
        } else fieldLabel.setText(null);
    }

    public void onDatePicker(ActionEvent actionEvent) {
        if (datePicker.getValue()==null) {
            dateLabel.setText("Provide a valid date..");
        } else dateLabel.setText(null);
    }

    public void onImgRefField(ActionEvent actionEvent) {
        img = item.setImg(imgField.getText());
        if (img == null){
            imgLabel.setText("Provide in only \"png\",\"jpg\",\"jpeg\"file format..");
        }
        else imgLabel.setText(null);
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        name = nameField.getText().trim();
        brand = brandField.getText().trim();
        price = priceField.getText().trim();
        field = fieldInput.getText().trim();
        quantity = quantityField.getText().trim();
        date = datePicker.getValue().toString();
        img = imgField.getText().trim();

        if(name.isEmpty()||brand.isEmpty()||price.isEmpty()||field.isEmpty()||quantity.isEmpty()||date.isEmpty()||img.isEmpty()){
            imgLabel.setText("Please fill all fields..");
        }
        else{
            inventory.add(name,brand,price,field,quantity,date,img);
            inventory.getInventory();
        }
    }
}
