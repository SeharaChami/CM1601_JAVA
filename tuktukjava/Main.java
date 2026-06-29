package tuktukjava;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

            File itemfile = new File("inventory_legacy.txt");
           // File dealerfile = new File("dealers_legacy.txt");

            DataCleaner dataset = new DataCleaner(itemfile);
           // DataCleaner dealerset = new DataCleaner(dealerfile);
            Inventory inventory = new Inventory(dataset);
    }
}
