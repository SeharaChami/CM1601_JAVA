package tuktukjava;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DealerCleaner extends DirtyDataCleaner{
    public DealerCleaner(File myfile, int fieldcount) {
        super(myfile, fieldcount);
    }
    public List<Dealer> returnDealers(){
        List<Dealer> formattedDealers = new ArrayList<>();

        try {
            for (int i = 0; i < formattedList.size(); i++) {
                String[] tempList = new String[4];
                for (int j = 0; j < 4; j++) {
                    tempList[j] = formattedList.get(i)[j];
                }
                Dealer dealer = new Dealer(tempList);
                formattedDealers.add(dealer);
            }
        } catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("System issue identified..");
        }
        return formattedDealers;
    }


    @Override
    public void validateRow(String[] newLine) {

    }
}
