package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.Bookmark;
import model.BoxArt;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
    Goal: Retrieve the id, title, and smallest box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": "url)
*/
public class Kata7 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        BiFunction<BoxArt, BoxArt, BoxArt> compareBoxArt = (boxOne, boxTwo) -> boxOne.getWidth() > boxTwo.getWidth() && boxOne.getHeight() > boxTwo.getHeight()
                ? boxTwo
                : boxOne;

        Function<List<BoxArt>, BoxArt> getSmallestBoxArt = (boxArts) -> boxArts.stream().reduce((boxOne, boxTwo) -> compareBoxArt.apply(boxOne,boxTwo)).get();

       return movieLists.stream().map(movieList -> movieList.getVideos())
                .flatMap(videos -> videos.stream())
                .map(video -> Map.of("id",video.getId(), "title", video.getTitle(), "boxart", getSmallestBoxArt.apply(video.getBoxarts())))
               .collect(Collectors.toList());
    }
}
