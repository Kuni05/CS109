import java.util.ArrayList;
import java.util.List;

public class ConcreteCinema implements Cinema{

    private List<Movie> movies;
    private List<Integer> movieHalls;

    public ConcreteCinema() {
        movies = new ArrayList<>();
        movieHalls = new ArrayList<>();
    }


    public void addMovieHall(int capacity){
        movieHalls.add(capacity);
    }


    public List<String> getAllMovieHallsCapacity(){
        List<String> NumCapacities = new ArrayList<>();
        for(int i = 0; i < movieHalls.size(); i++ ){
            NumCapacities.add ( (i+1) + "-" + movieHalls.get(i) ) ;
        }
        return NumCapacities;
    }

    public void addMovie(String name, int runtime, int hallNumber, double price, int type, Time startTime) {
        int capacity = movieHalls.get(hallNumber - 1);
        int startMinutes = startTime.toMinutes();
        int endMinutes = startMinutes + runtime;
        for (Movie movie : movies) {
            int movieStartMinutes = movie.getStartTime().toMinutes();
            int movieEndMinutes = movieStartMinutes + movie.getRuntime();
            if (hallNumber == movie.getHallNumber() &&
                    !(movieEndMinutes + 20 <= startMinutes || endMinutes + 20 <= movieStartMinutes)) {
                return;
            }
        }
        Movie movie;
        if (type == 0) {
            movie = new OrdinaryMovie(name, startTime, runtime, price, capacity);
        } else {
            movie = new ThreeDMovie(name, startTime, runtime, price, capacity);
        }
        movie.setHallNumber(hallNumber);
        movies.add(movie);
    }


    public List<Movie> getAllMovies(){
        return movies;
    }

    public List<Movie> getMoviesFromMovieHallOrderByStartTime(int hallNumber) {
        List<Movie> hallMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (hallNumber == movie.getHallNumber()) {
                hallMovies.add(movie);
            }
        }
        hallMovies.sort((m1, m2) -> m1.getStartTime().toMinutes() - m2.getStartTime().toMinutes());
        return hallMovies;
    }


    public double reserveMovie(int movieId, int arg) {
        Movie movie = getMovieById(movieId);
        if (movie == null) {
            return 0.0;
        }
        return movie.purchase(arg);
    }

    public Movie getMovieById(int movieId){
        for (Movie movie : movies) {
            if (movie.getId() == movieId) {
                return movie;
            }
        }
        return null;
    }

    public double getOneMovieIncome(int movieId) {
        Movie movie = getMovieById(movieId);
        if (movie == null) return 0;
        if (movie instanceof ThreeDMovie) {
            return movie.getPrice() * (getHallCapacity(movie.getHallNumber()) - movie.getTicketsLeft())+((ThreeDMovie) movie).getGlassSold();
        }
        return movie.getPrice() * (getHallCapacity(movie.getHallNumber()) - movie.getTicketsLeft());
    }

    public double getTotalIncome() {
        double totalIncome = 0;
        for (Movie movie : movies) {
            totalIncome += getOneMovieIncome(movie.getId());
        }
        return totalIncome;
    }

    private int getHallCapacity(int hallNumber) {
        return movieHalls.get(hallNumber - 1);
    }
    public List<Movie> getAvailableMoviesByName(Time currentTime, String name) {
        List<Movie> availableMovies = new ArrayList<>();
        int currentMinutes = currentTime.toMinutes();
        for (Movie movie : movies) {
            if (movie.getName().equals(name) && movie.getStartTime().toMinutes() > currentMinutes && movie.getTicketsLeft() > 0) {
                availableMovies.add(movie);
            }
        }
        availableMovies.sort((m1, m2) -> m1.getStartTime().toMinutes() - m2.getStartTime().toMinutes());
        return availableMovies;
    }

}
