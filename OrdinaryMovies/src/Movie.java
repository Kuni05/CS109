public abstract class Movie {

    private static int count = 0;
    private int id;
    private String name;
    private Time startTime;
    private int runtime;
    private double price;
    protected int ticketsLeft;
    private int hallNumber;

    public Movie( String name, Time startTime, int runtime, double price , int ticketsLeft) {
        this.id = ++count;
        this.name = name;
        this.startTime = startTime;
        this.runtime = runtime;
        this.price = price;
        this.ticketsLeft = ticketsLeft ;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public Time getStartTime() { return startTime; }
    public int getRuntime() { return runtime; }
    public double getPrice() { return price; }
    public int getTicketsLeft() { return ticketsLeft; }
    public int getHallNumber() { return hallNumber; }

    public abstract double purchase(int arg);

    public String toString(){
        return "id=" + id + ", name='" + name + "', startTime:" + startTime + ", runtime=" + runtime + ", price=" + price + ", ticketsLeft=" + ticketsLeft;
    }



}
