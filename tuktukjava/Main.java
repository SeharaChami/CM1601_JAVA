package tuktukjava;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
            File myfile = new File("inventory_legacy.txt");



        for (int i = 0; i < lineList.size(); i++) {
            Item item = new Item(lineList.get(i));
            System.out.println(item);
            formattedList.add(item);


    }
}
