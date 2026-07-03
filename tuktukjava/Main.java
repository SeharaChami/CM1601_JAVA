package tuktukjava;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

            Inventory inventory = new Inventory();
            RandomDealers randomDealers = new RandomDealers();
            //randomDealers.displayDealers();
            //inventory.displayInventory();
        inventory.generateItemCode();
    }
}
