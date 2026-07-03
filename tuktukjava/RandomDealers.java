package tuktukjava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RandomDealers {
    File dealerfile = new File("dealers_legacy.txt");
    DataCleaner dealerset = new DataCleaner(dealerfile);
    List<Dealer> formattedList = new ArrayList<>();

    public RandomDealers(){
        this.formattedList = dealerset.returnDealers();
    }

    public void displayDealers(){
        for(int i = 0 ; i< formattedList.size();i++){
            for (int j = 0; j < formattedList.get(i).dealer.length; j++){
                System.out.print(formattedList.get(i).dealer[j]);
            }
            System.out.println();
        }
    }
}
