import java.util.List;

public interface Cinema {

    public void addMovieHall(int capacity);

    public List<String> getAllMovieHallsCapacity();

    public void addMovie(String name, int runtime, int hallNumber, double price, int type, Time startTime);

    public List<Movie> getAllMovies();

    public List<Movie> getMoviesFromMovieHallOrderByStartTime(int hallNumber);

    public double reserveMovie(int movieId, int arg);

    public Movie getMovieById(int movieId);

    public double getOneMovieIncome(int movieId);

    public double getTotalIncome();

    public List<Movie> getAvailableMoviesByName(Time currentTime, String name);

}
