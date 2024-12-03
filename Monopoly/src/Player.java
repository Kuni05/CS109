public class Player {
    private int id;
    private double money;
    private boolean isActive;
    private int location;

    public Player(int id){
        this.id = id;
        this.money=100;
        this.isActive=true;
        this.location=0;
    }

    public int getId(){
        return id;
    }
    public double getMoney(){
        return money;
    }
    public boolean isActive(){
        return isActive;
    }
    public int getLocation(){
        return location;
    }

    public void setMoney(double money){
        this.money = money;
    }
    public void setActive(boolean active){
        isActive = active;
    }
    public void setLocation(int location){
        this.location = location;
    }

    public String toString(){
        if(isActive){
            return String.format("Player %d: at %d, has %.1f",id,location,money);
        }
        else{
            return String.format("Player %d: Failed",id);
        }
    }

    public double payRent(int housePrice){
        double transactionAmount=housePrice*0.5;
        if(money>=transactionAmount){
            money-=transactionAmount;
            return transactionAmount;
        }
        else{
            isActive=false;
            return money;
        }
    }

    public void collectRent(double rentPrice){
        money+=rentPrice;
    }

    public boolean buildHouse(Land land,int housePrice){
        if(land.getHouse()!=null || housePrice==0 || money<housePrice){
            return false;
        }
        else{
            money-=housePrice;
            land.setHouse(new House(this,housePrice));
            return true;
        }
    }
}
