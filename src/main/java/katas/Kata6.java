package katas;

import model.BoxArt;
import model.Movie;
import util.DataUtil;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/*
    Goal: Retrieve the url of the largest boxart using map() and reduce()
    DataSource: DataUtil.getMovieLists()
    Output: String
*/
public class Kata6 {
    public static String execute() {
        List<Movie> movies = DataUtil.getMovies();

        BiFunction<BoxArt, BoxArt, BoxArt> getLarger = (boxOne, boxTwo) -> boxOne.getWidth() >= boxTwo.getWidth() && boxOne.getHeight() >= boxTwo.getHeight()
                ? boxOne
                : boxTwo;

        return movies.stream().map(movie -> movie.getBoxarts())
                .flatMap(boxarts -> boxarts.stream())
                .reduce((boxOne, boxTwo) -> getLarger.apply(boxOne, boxTwo))
                .get()
                .getUrl();

    }
}
