package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.BoxArt;
import model.MovieList;
import util.DataUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
    Goal: Retrieve id, title, and a 150x200 box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": BoxArt)
*/
public class Kata4 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        //needs to be 150 x 200
        Function<List<BoxArt>, String> getBoxArtUrl = (boxarts) -> boxarts.stream()
                .filter(boxArt -> boxArt.getWidth()==150 && boxArt.getHeight()==200)
                .map(box -> box.getUrl()).findFirst().get();

        return movieLists.stream().map(movie -> movie.getVideos())
                .flatMap(videos -> videos.stream())
                .map(video -> Map.of("id",video.getId(),"title",video.getTitle(),"boxart",getBoxArtUrl.apply(video.getBoxarts())))
                .collect(Collectors.toList());

        //return ImmutableList.of(ImmutableMap.of("id", 5, "title", "Bad Boys", "boxart", new BoxArt(150, 200, "url")));
    }
}
