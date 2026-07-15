package tuktukjava;

import java.io.File;
import java.util.*;



public class RandomDealers {
    List<Dealer> formattedList = new ArrayList<>();
    List<Dealer> randomDealersList = new ArrayList<>();
    int fieldcount = 4;

    public RandomDealers(File dealerfile){
        DataCleaner dealerset = new DataCleaner(dealerfile,fieldcount);
        this.formattedList = dealerset.returnDealers();
    }

    public void getAllDealers(){
        for(int i = 0 ; i< formattedList.size();i++){
            for (int j = 0; j < formattedList.get(i).dealer.length; j++){
                System.out.print(formattedList.get(i).dealer[j]);
            }
            System.out.println();
        }
    }
    public void getRandomDealers(){
        randomDealers();
        for(int i = 0 ; i< randomDealersList.size();i++){
            for (int j = 0; j < randomDealersList.get(i).dealer.length; j++){
                System.out.print(randomDealersList.get(i).dealer[j]);
            }
            System.out.println();
        }
    }
    public void randomDealers(){
        List<Integer> numberList = new ArrayList<>();

        Dealer dealer;
        Random rand = new Random();
        int rounds = 0;
        while(rounds < 4){
            int index = rand.nextInt(formattedList.size());
            if (numberList.contains(index)!=true){
                System.out.println(formattedList.get(index).dealer[0]);
                randomDealersList.add(formattedList.get(index));
                numberList.add(index);
                rounds++;
            }
        }
    }
}
