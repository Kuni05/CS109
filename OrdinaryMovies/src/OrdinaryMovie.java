public class OrdinaryMovie extends Movie {

    public OrdinaryMovie(  String name , Time startTime , int runtime , double price , int ticketsLeft) {
        super(  name , startTime , runtime , price , ticketsLeft );
    }

    public double purchase(int arg){
        if(arg<=ticketsLeft){
            ticketsLeft-=arg;
            return arg * getPrice();
        }
        else{
            int sold = ticketsLeft;
            ticketsLeft=0;
            return sold*getPrice();
        }
    }

    public String toString(){
        return super.toString()+ " OrdinaryMovie";
    }
}


