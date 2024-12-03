public class Land {
    private House house;
    private LandColor color;

    public Land(LandColor color) {
        this.color = color;
    }
    public void setHouse(House house) {
        this.house = house;
    }

    public House getHouse() {
        return house;
    }
    public LandColor getColor() {
        return color;
    }

    public String toString(){
        return String.format("Land %s: %s", color, house != null ? house.toString() : "");
    }
}
