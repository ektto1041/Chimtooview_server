package com.yeon.chimtooview.Controller;

import com.querydsl.core.QueryResults;
import com.yeon.chimtooview.Dto.ThumbnailDto;
import com.yeon.chimtooview.Dto.VideoDto;
import com.yeon.chimtooview.Entity.Video;
import com.yeon.chimtooview.Service.VideoService;
import com.yeon.chimtooview.Service.VideoThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoThumbnailService videoThumbnailService;

    @RequestMapping(
            value = "/getVideoAll",
            method = RequestMethod.GET
    )
    public ResponseEntity getVideoAll() {
        List<Video> videoList = videoService.getVideoAll();

        List<VideoDto> result = new ArrayList<>();
        for(Video video : videoList) {
            VideoDto videoDto = video.toDto();

            ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

            videoDto.setThumbnails(thumbnailDto);

            result.add(videoDto);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/getVideoAllForTopFive",
            method = RequestMethod.GET
    )
    public ResponseEntity getVideoAllForTopFive() {
        List<List<Video>> topFiveVideoList = videoService.getVideoAllForTopFive();

        List<List<VideoDto>> result = new ArrayList<>();
        for(List<Video> videoList : topFiveVideoList) {

            List<VideoDto> videoDtoList = new ArrayList<>();
            for(Video video : videoList) {
                VideoDto videoDto = video.toDto();

                ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

                videoDto.setThumbnails(thumbnailDto);

                videoDtoList.add(videoDto);
            }

            result.add(videoDtoList);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/getVideoAllCountByFilter/" +
                    "{startDate}/{endDate}/" +
                    "{startViewCount}/{endViewCount}/" +
                    "{startLikeCount}/{endLikeCount}/" +
                    "{startDislikeCount}/{endDislikeCount}/" +
                    "{searchWord}/{searchWordPlaylist}",
            method = RequestMethod.GET
    )
    public ResponseEntity getVideoAllCountByFilter(
            @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,
            @PathVariable("startViewCount") int startViewCount, @PathVariable("endViewCount") int endViewCount,
            @PathVariable("startLikeCount") int startLikeCount, @PathVariable("endLikeCount") int endLikeCount,
            @PathVariable("startDislikeCount") int startDislikeCount, @PathVariable("endDislikeCount") int endDislikeCount,
            @PathVariable("searchWord") String searchWord, @PathVariable("searchWordPlaylist") String searchWordPlaylist) {
        long result = videoService.getVideoAllCountByFilter(
                startDate, endDate,
                startViewCount, endViewCount,
                startLikeCount, endLikeCount,
                startDislikeCount, endDislikeCount,
                searchWord, searchWordPlaylist);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/getVideoAllOrderByPaging/" +
                    "{category}/{order}/" +
                    "{startDate}/{endDate}/" +
                    "{startViewCount}/{endViewCount}/" +
                    "{startLikeCount}/{endLikeCount}/" +
                    "{startDislikeCount}/{endDislikeCount}/" +
                    "{searchWord}/{searchWordPlaylist}/" +
                    "{pageCurrent}",
            method = RequestMethod.GET
    )
    public ResponseEntity getVideoAllOrderByPaging(
            @PathVariable("category") int category, @PathVariable("order") int order,
            @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,
            @PathVariable("startViewCount") int startViewCount, @PathVariable("endViewCount") int endViewCount,
            @PathVariable("startLikeCount") int startLikeCount, @PathVariable("endLikeCount") int endLikeCount,
            @PathVariable("startDislikeCount") int startDislikeCount, @PathVariable("endDislikeCount") int endDislikeCount,
            @PathVariable("searchWord") String searchWord, @PathVariable("searchWordPlaylist") String searchWordPlaylist,
            @PathVariable("pageCurrent") int pageCurrent) {
        QueryResults<Video> queryResults = videoService.getVideoAllOrderByPaging(
                category, order,
                startDate, endDate,
                startViewCount, endViewCount,
                startLikeCount, endLikeCount,
                startDislikeCount, endDislikeCount,
                searchWord, searchWordPlaylist,
                pageCurrent);
        List<Video> videoList = queryResults.getResults();

        List<VideoDto> result = new ArrayList<>();
        for(Video video : videoList) {
            VideoDto videoDto = video.toDto();

            ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

            videoDto.setThumbnails(thumbnailDto);

            result.add(videoDto);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/getVideoOrderByViewCountLimit10",
            method = RequestMethod.GET
    )
    public ResponseEntity getVideoOrderByViewCountLimit10() {
        List<Video> videoList = videoService.getVideoOrderByViewCountLimit10();

        List<VideoDto> result = new ArrayList<>();
        for(Video video : videoList) {
            VideoDto videoDto = video.toDto();

            ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

            videoDto.setThumbnails(thumbnailDto);

            result.add(videoDto);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/getVideoOrderByLikeCountLimit10",
            method = RequestMethod.GET
    )
    public ResponseEntity getVideoOrderByLikeCountLimit10() {
        List<Video> videoList = videoService.getVideoOrderByLikeCountLimit10();

        List<VideoDto> result = new ArrayList<>();
        for(Video video : videoList) {
            VideoDto videoDto = video.toDto();

            ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

            videoDto.setThumbnails(thumbnailDto);

            result.add(videoDto);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/getVideoOrderByDislikeCountLimit10",
            method = RequestMethod.GET
    )
    public ResponseEntity getVideoOrderByDislikeCountLimit10() {
        List<Video> videoList = videoService.getVideoOrderByDislikeCountLimit10();

        List<VideoDto> result = new ArrayList<>();
        for(Video video : videoList) {
            VideoDto videoDto = video.toDto();

            ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

            videoDto.setThumbnails(thumbnailDto);

            result.add(videoDto);
        }

        return ResponseEntity.ok(result);
    }
}
