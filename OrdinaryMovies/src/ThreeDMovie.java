public class ThreeDMovie extends Movie {
    private final int GLASS_PRICE = 20 ;
    private int glass=0;

    public ThreeDMovie(String name, Time startTime, int runtime, double price , int ticketsLeft) {
        super( name, startTime, runtime, price, ticketsLeft);
    }

    public double purchase(int arg) {
        if( arg == 1 && ticketsLeft > 0 ) {
            ticketsLeft--;
            glass++;
            return  (getPrice() + GLASS_PRICE) ;
        }
        else if( arg == 0 && ticketsLeft > 0 ) {
            ticketsLeft--;
            return getPrice();
        }
        System.out.println("Wrong");
        return 0;
    }

    public int getGlassSold() {
        return 20*glass;
    }

    public String toString() {
        return super.toString() + " ThreeDMovie" ;
    }



}
