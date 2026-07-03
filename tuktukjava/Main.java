package tuktukjava;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        File itemfile = new File("inventory_legacy.txt");

        Inventory inventory = new Inventory(itemfile);
        RandomDealers randomDealers = new RandomDealers();
        //randomDealers.displayDealers();
        //inventory.displayInventory();
        inventory.Add();
        inventory.displayInventory();
    }
}
