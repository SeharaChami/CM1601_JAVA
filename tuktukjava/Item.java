package tuktukjava;

public class Item {
    String[] item;
    public Item(String line){
        this.item = line.split("[;,|]");
        for(int i = 0;i< item.length;i++){
            System.out.printf("%-30s|",item[i]);
        }
    }

    public void LowStockMon(){
        int limit = 10;
        int qty = Integer.parseInt(item[4].trim());
        if(qty<limit){
            System.out.println(item[0]);
        }
    }
    public void Add(){

    }
    public void Delete(){

    }
    public void Update(){

    }

}
