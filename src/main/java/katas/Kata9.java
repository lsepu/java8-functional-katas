package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.Bookmark;
import model.InterestingMoment;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
    Goal: Retrieve each video's id, title, middle interesting moment time, and smallest box art url
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", 5, "title", "some title", "time", new Date(), "url", "someUrl")
*/
public class Kata9 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        Function<List<InterestingMoment>, Date> getMiddleInterestingMoment = (interestingMoments) ->
                interestingMoments.stream().filter(interestingMoment -> interestingMoment.getType().equals("Middle"))
                        .map(interestingMoment -> interestingMoment.getTime()).findFirst().get();

        return movieLists.stream().map(movie -> movie.getVideos())
                .flatMap(videos -> videos.stream())
                .map(video -> Map.of("id", video.getId(), "title", video.getTitle(),
                        "time", getMiddleInterestingMoment.apply(video.getInterestingMoments()),
                        "url", video.getUri()))
                .collect(Collectors.toList());

    }
}
