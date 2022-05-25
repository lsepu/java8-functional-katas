package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.Movie;
import util.DataUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
    Goal: use map() to project an array of videos into an array of {id, title}-pairs
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys")
*/
public class Kata1 {
    public static List<Map> execute() {
        List<Movie> movies = DataUtil.getMovies();

        Function <Movie, Map> getIdTitle = (movie) -> Map.of(movie.getId(), movie.getTitle());

        return movies.stream().map(movie -> getIdTitle.apply(movie)).collect(Collectors.toList());

        //return ImmutableList.of(ImmutableMap.of("id", 5, "title", "Bad Boys"));
    }

}
