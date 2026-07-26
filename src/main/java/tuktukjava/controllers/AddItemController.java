package tuktukjava.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tuktukjava.Inventory;
import tuktukjava.Item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class AddItemController {
    @FXML
    public Button clearBtn;
    @FXML
    public Button backBtn;
    private Inventory inventory;
    Item item = new Item(new String[8]);
    @FXML
    public Button addItem;
    @FXML private TextField itemCodeField;
    @FXML private TextField nameField;
    @FXML private TextField brandField;
    @FXML private TextField priceField;
    @FXML private TextField fieldInput;
    @FXML private TextField quantityField;
    @FXML private DatePicker datePicker;
    @FXML private Label nameLabel;
    @FXML private Label brandLabel;
    @FXML private Label priceLabel;
    @FXML private Label qtyLabel;
    @FXML private Label fieldLabel;
    @FXML private Label imgLabel;
    @FXML private Label dateLabel;
    @FXML public Label msgLabel;
    @FXML private Label errorLabel;
    String name;
    String brand;
    String price;
    String field;
    String quantity;
    String date;
    private File imgFile;
    String img;
    @FXML
    public void initialize(){

    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        String tempCode = inventory.generateItemCode();
        itemCodeField.setText(tempCode);
        item.setCode(tempCode);
    }

    @FXML
    public void onNameText(ActionEvent actionEvent) {
        if(nameField.getText().isEmpty()){
            nameLabel.setText("Name cannot be empty..");
        }
        else
        {nameLabel.setText(null);
        item.setName(nameField.getText());
        }
    }
    @FXML
    public void onBrandText(ActionEvent actionEvent) {
        if (brandField.getText().isEmpty()){
            brandLabel.setText("Brand cannot be empty..");
        }
        else {
            brandLabel.setText(null);
            item.setBrand(brandField.getText());
        }
    }
    @FXML
    public void onPriceText(ActionEvent actionEvent) {
        price = item.getPrice(priceField.getText());
        if(price == null){
            priceLabel.setText("Provide only numeric value greater that 0..");
        }
        else {
            priceLabel.setText(null);
            item.setPrice(price);
        }
    }
    @FXML
    public void onQtyText(ActionEvent actionEvent) {
        quantity = item.getQty(quantityField.getText());
        if(quantity == null){
            qtyLabel.setText("Provide only numeric value greater that 0..");
        }
        else {
            qtyLabel.setText(null);
            item.setQty(quantity);
        }
    }
    @FXML
    public void onFieldText(ActionEvent actionEvent) {
        if (fieldInput.getText().isEmpty()) {
            fieldLabel.setText("Field cannot be empty..");
        } else {
            fieldLabel.setText(null);
            item.setField(fieldInput.getText());
        }
    }
    @FXML
    public void onDatePicker(ActionEvent actionEvent) {
        if (datePicker.getValue()==null) {
            dateLabel.setText("Provide a valid date..");
        } else {
            dateLabel.setText(null);
            item.setDate(datePicker.getValue().toString());
        }
    }
    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) throws IOException {
        onDatePicker(actionEvent);
        onBrandText(actionEvent);
        onFieldText(actionEvent);
        onQtyText(actionEvent);
        onPriceText(actionEvent);
        onNameText(actionEvent);

        name = nameField.getText().trim();
        brand = brandField.getText().trim();
        price = priceField.getText().trim();
        field = fieldInput.getText().trim();
        quantity = quantityField.getText().trim();
        img = imgFile.getName().trim();
        date = datePicker.getValue().toString();

        String formattedPrice = item.getPrice(price);
        String validQty = item.getQty(quantity);

        if (imgFile == null) {
            imgLabel.setText("Please choose an image");
            return;
        }
        try{
            File imgDir = new File("src/main/resources/images/");
            if(!imgDir.exists())imgDir.mkdirs();
            File dest = new File(imgDir,imgFile.getName());
            Files.copy(imgFile.toPath(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            imgLabel.setText("Could not save image..");
            return;
        }

        String[] part = new String[8];
        part[0] = item.getCode();
        part[1] = name;
        part[2] = brand;
        part[3] = formattedPrice;
        part[4] = validQty;
        part[5] = field;
        part[6] = date;
        part[7] = imgFile.getName();
        item.setItem(part);

        inventory.add(item);
        msgLabel.setText("Item " + item.getCode() + " added successfully.");
        onClearBtnClick(actionEvent);
    }
    @FXML
    public void onClearBtnClick(ActionEvent actionEvent) {
        itemCodeField.setText(inventory.generateItemCode());
        nameField.setText(null);
        brandField.setText(null);
        priceField.setText(null);
        quantityField.setText(null);
        fieldInput.setText(null);
        imgFile = null;
        datePicker.setValue(null);

        nameLabel.setText(null);
        brandLabel.setText(null);
        priceLabel.setText(null);
        qtyLabel.setText(null);
        fieldLabel.setText(null);
        imgLabel.setText(null);
        dateLabel.setText(null);
    }
    @FXML
    public void onItemAdded(MouseEvent mouseEvent) {
    }

    public void onBackBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/homePage-view.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
        stage.show();
    }
    @FXML
    public void onChooseImageClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String name = file.getName().toLowerCase();
            if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")) {
                imgFile = file;
                imgLabel.setText(file.getName());
            } else {
                imgLabel.setText("Only png, jpg, jpeg allowed");
            }
        }
    }
}
