package tuktukjava;


public class Dealer {
    String id;
    String name;
    String phone;
    String location;
    String[] dealer;
    public Dealer(String[] line){
        this.dealer = line;
        setId();
        setName();
        setPhone();
        setLocation();
    }
    public String getId() {
        return id;
    }

    public void setId() {
        this.id = dealer[0];
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = dealer[1];
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone() {
        this.phone = dealer[2];
    }

    public String getLocation() {
        return location;
    }

    public void setLocation() {
        this.location = dealer[3];
    }

    public String[] getDealer() {
        return dealer;
    }
}
