package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.BoxArt;
import util.DataUtil;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
    Goal: Create a datastructure from the given data:

    This time we have 4 seperate arrays each containing lists, videos, boxarts, and bookmarks respectively.
    Each object has a parent id, indicating its parent.
    We want to build an array of list objects, each with a name and a videos array.
    The videos array will contain the video's id, title, bookmark time, and smallest boxart url.
    In other words we want to build the following structure:

    [
        {
            "name": "New Releases",
            "videos": [
                {
                    "id": 65432445,
                    "title": "The Chamber",
                    "time": 32432,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"
                },
                {
                    "id": 675465,
                    "title": "Fracture",
                    "time": 3534543,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"
                }
            ]
        },
        {
            "name": "Thrillers",
            "videos": [
                {
                    "id": 70111470,
                    "title": "Die Hard",
                    "time": 645243,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"
                },
                {
                    "id": 654356453,
                    "title": "Bad Boys",
                    "time": 984934,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg"
                }
            ]
        }
    ]

    DataSource: DataUtil.getLists(), DataUtil.getVideos(), DataUtil.getBoxArts(), DataUtil.getBookmarkList()
    Output: the given datastructure
*/
public class Kata11 {
    public static List<Map> execute() {
        List<Map> lists = DataUtil.getLists();
        List<Map> videos = DataUtil.getVideos();
        List<Map> boxArts = DataUtil.getBoxArts();
        List<Map> bookmarkList = DataUtil.getBookmarkList();


        //get the smallest boxart
        BiFunction<Map, Map, Map> getSmallest = (boxOne, boxTwo) ->
                (Integer) boxOne.get("width") >= (Integer) boxTwo.get("width") && (Integer) boxOne.get("height") >= (Integer) boxTwo.get("height")
                        ? boxTwo
                        : boxOne;

        //get the url of boxart
        Function<String, String> getSmallestBoxArtUrl = (videoId) -> boxArts.stream()
                .filter(boxArt -> boxArt.get("videoId").toString().equals(videoId))
                .reduce((boxArtOne, boxArtTwo) -> getSmallest.apply(boxArtOne, boxArtTwo))
                .get().get("url").toString();

        //get the time
        Function<String, String> getTime = (videoId) -> bookmarkList.stream()
                .filter(bookmark -> bookmark.get("videoId").toString().equals(videoId))
                .findFirst().get().get("time").toString();

        //get the videos
        Function<String, List<Map>> getVideos = (listId) -> videos.stream()
                .filter(video -> video.get("listId").toString().equals(listId))
                .map(video -> Map.of("id", video.get("id"), "title", video.get("title"),
                        "time", getTime.apply(video.get("id").toString()),
                        "boxart", getSmallestBoxArtUrl.apply(video.get("id").toString())))
                .collect(Collectors.toList());

        //return new map
       return lists.stream().map(list -> Map.of("name", list.get("name"), "videos", getVideos.apply(list.get("id").toString())))
                .collect(Collectors.toList());


    }
}
