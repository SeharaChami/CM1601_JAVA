package tuktukjava;

import java.io.File;
import java.util.*;



public class RandomDealers {
    List<Dealer> formattedList;

    public RandomDealers(List<Dealer> dealers){
        this.formattedList = dealers;
    }
    public List<Dealer> randomDealers(){
        List<Dealer> randomDealersList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();

        if (formattedList.size() < 4) {
            return formattedList; // not enough to pick 4, return all
        }

        Random rand = new Random();
        int rounds = 0;
        while(rounds < 4){
            int index = rand.nextInt(formattedList.size());
            if (!numberList.contains(index)){
                randomDealersList.add(formattedList.get(index));
                numberList.add(index);
                rounds++;
            }
        }
        return randomDealersList;
    }
    public List<Dealer> getSortedByLocation(){

        List<Dealer> randomDealersList = randomDealers();

        for (int i = 0; i < randomDealersList.size()-1;i++){
            for (int j = 0; j<randomDealersList.size()-1;j++){
                if(randomDealersList.get(j).getLocation().compareTo(randomDealersList.get(j+1).getLocation())>0){
                    Dealer temp = randomDealersList.get(j);
                    randomDealersList.set(j,randomDealersList.get(j+1));
                    randomDealersList.set(j+1,temp);
                }
            }
        }
        return randomDealersList;
    }
}
