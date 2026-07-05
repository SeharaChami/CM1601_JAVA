package tuktukjava;

import java.io.*;


public class Main {
    public static void main(String[] args) {

        File itemfile = new File("inventory_legacy.txt");
        File dealerfile = new File("dealers_legacy.txt");

        Inventory inventory = new Inventory(itemfile);
        RandomDealers randomDealers = new RandomDealers(dealerfile);
        inventory.getInventory();
        //randomDealers.getAllDealers();
        //System.out.println("___________________");
        //randomDealers.getRandomDealers();
    }
}
