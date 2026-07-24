package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tuktukjava.Inventory;
import tuktukjava.Item;

import java.util.List;

public class InventoryController {
    public TextField searchField;
    public VBox tableContent;
    public Label totalItemsLabel;
    public Label totalQtyLabel;
    public Label totalValueLabel;
    public Button prevBtn;
    public Button nextBtn;
    public Label pageLabel;
    private  List<Item> items;
    private Inventory inventory;
    private List<String> categoryNames;
    private List<List<Item>> groupedItems;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        categoryNames = inventory.getCategories();
        groupedItems = inventory.getItemsByCategory();
    }

    public void onBackBtnClick(ActionEvent actionEvent) {
    }

    public void onSearchAction(ActionEvent actionEvent) {
    }

    public void onPrevClick(ActionEvent actionEvent) {
    }

    public void onNextClick(ActionEvent actionEvent) {
    }
}
