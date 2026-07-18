package tuktukjava.controllers;

import tuktukjava.Inventory;
import tuktukjava.Item;

import java.util.List;

public class InventoryController {
    private  List<Item> items;
    private Inventory inventory;
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
