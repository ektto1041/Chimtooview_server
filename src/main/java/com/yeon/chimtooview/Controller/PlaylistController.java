package com.yeon.chimtooview.Controller;

import com.yeon.chimtooview.Dto.PlaylistDto;
import com.yeon.chimtooview.Dto.ThumbnailDto;
import com.yeon.chimtooview.Dto.VideoDto;
import com.yeon.chimtooview.Entity.Playlist;
import com.yeon.chimtooview.Entity.PlaylistThumbnail;
import com.yeon.chimtooview.Entity.Video;
import com.yeon.chimtooview.Entity.VideoThumbnail;
import com.yeon.chimtooview.Exceptions.ExceptionMessage;
import com.yeon.chimtooview.Exceptions.NotFoundPlaylistException;
import com.yeon.chimtooview.Service.PlaylistService;
import com.yeon.chimtooview.Service.PlaylistThumbnailService;
import com.yeon.chimtooview.Service.VideoService;
import com.yeon.chimtooview.Service.VideoThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistThumbnailService playlistThumbnailService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoThumbnailService videoThumbnailService;

    @RequestMapping(
            value = "/getPlaylistAll",
            method = RequestMethod.GET
    )
    public ResponseEntity getPlaylistAll() {
        List<Playlist> playlistList = playlistService.getPlaylistAll();

        List<PlaylistDto> result = new ArrayList<>();
        for(Playlist playlist : playlistList) {
            PlaylistDto playlistDto = playlist.toDto();

            ThumbnailDto playlistThumbnailDto = playlistThumbnailService.getPlaylistThumbnailByPlaylist(playlist).toDto();

            playlistDto.setThumbnails(playlistThumbnailDto);

            List<VideoDto> videoDtoList = new ArrayList<>();
            for(Video video : playlist.getVideoList()) {
                VideoDto videoDto = video.toDto();

                ThumbnailDto videoThumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

                videoDto.setThumbnails(videoThumbnailDto);

                videoDtoList.add(videoDto);
            }

            playlistDto.setVideoList(videoDtoList);

            result.add(playlistDto);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/getPlaylistAllCountByFilter/{searchWord}",
            method = RequestMethod.GET
    )
    public ResponseEntity getPlaylistAllCountByFilter(@PathVariable("searchWord") String searchWord) {
        long result = playlistService.getPlaylistAllCountByFilter(searchWord);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/getPlaylistAllOrderByPaging/{category}/{order}/{searchWord}/{pageCurrent}",
            method = RequestMethod.GET
    )
    public ResponseEntity getPlaylistAllOrderByPaging(@PathVariable("category") int category, @PathVariable("order") int order, @PathVariable("searchWord") String searchWord, @PathVariable("pageCurrent") int pageCurrent) {
        List<Playlist> playlistList = playlistService.getPlaylistAllOrderByPaging(category, order, searchWord, pageCurrent);

        List<PlaylistDto> result = new ArrayList<>();
        for(Playlist playlist : playlistList) {
            PlaylistDto playlistDto = playlist.toDto();

            List<VideoDto> videoDtoList = new ArrayList<>();
            List<Video> videoList = videoService.getVideoAllByPlaylist(playlist);
            for(Video video : videoList) {
                VideoDto videoDto = video.toDto();

                ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

                videoDto.setThumbnails(thumbnailDto);

                videoDtoList.add(videoDto);
            }
            playlistDto.setVideoList(videoDtoList);

            ThumbnailDto thumbnailDto = playlistThumbnailService.getPlaylistThumbnailByPlaylist(playlist).toDto();
            playlistDto.setThumbnails(thumbnailDto);

            result.add(playlistDto);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/postPlaylist",
            method = RequestMethod.POST
    )
    public ResponseEntity postPlaylist(@RequestBody PlaylistDto newPlaylistDto) {
        int viewCountSum = 0;
        int likeCountSum = 0;
        int dislikeCountSum = 0;
        for(VideoDto videoDto : newPlaylistDto.getVideoList()) {
            viewCountSum += videoDto.getViewCount();
            likeCountSum += videoDto.getLikeCount();
            dislikeCountSum += videoDto.getDislikeCount();
        }

        double viewCountAvg = newPlaylistDto.getVideoList().size() == 0 ? 0 : (double) viewCountSum / newPlaylistDto.getVideoList().size();
        double likeCountAvg = newPlaylistDto.getVideoList().size() == 0 ? 0 : (double) likeCountSum / newPlaylistDto.getVideoList().size();
        double dislikeCountAvg = newPlaylistDto.getVideoList().size() == 0 ? 0 : (double) dislikeCountSum / newPlaylistDto.getVideoList().size();

        newPlaylistDto.setViewCountSum(viewCountSum);
        newPlaylistDto.setViewCountAvg(viewCountAvg);
        newPlaylistDto.setLikeCountSum(likeCountSum);
        newPlaylistDto.setLikeCountAvg(likeCountAvg);
        newPlaylistDto.setDislikeCountSum(dislikeCountSum);
        newPlaylistDto.setDislikeCountAvg(dislikeCountAvg);

        // Playlist
        Playlist newPlaylist = newPlaylistDto.toEntity();
        Playlist savedPlaylist = playlistService.postPlaylist(newPlaylist);

        // Playlist Thumbnails
        PlaylistThumbnail playlistThumbnail = newPlaylistDto.getThumbnails().toEntityInPlaylist();
        playlistThumbnail.setPlaylist(savedPlaylist);
        playlistThumbnailService.postPlaylistThumbnail(playlistThumbnail);

        for(VideoDto videoDto : newPlaylistDto.getVideoList()) {
            // Video
            Video video = videoDto.toEntity();
            video.setPlaylist(newPlaylist);
            Video savedVideo = videoService.postVideo(video);

            // Video Thumbnails
            VideoThumbnail videoThumbnail = videoDto.getThumbnails().toEntityInVideo();
            videoThumbnail.setVideo(savedVideo);
            videoThumbnailService.postVideoThumbnail(videoThumbnail);
        }

        // Playlist 2차 데이터 계산


        try{
            Playlist playlist = playlistService.getPlaylistById(newPlaylistDto.getId());

            // Dto로 변환
            PlaylistDto result = playlist.toDto();

            List<VideoDto> videoDtoList = new ArrayList<>();
            List<Video> videoList = videoService.getVideoAllByPlaylist(playlist);
            for(Video video : videoList) {
                VideoDto videoDto = video.toDto();

                ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

                videoDto.setThumbnails(thumbnailDto);

                videoDtoList.add(videoDto);
            }
            result.setVideoList(videoDtoList);

            ThumbnailDto thumbnailDto = playlistThumbnailService.getPlaylistThumbnailByPlaylist(playlist).toDto();
            result.setThumbnails(thumbnailDto);

            return ResponseEntity.ok(result);
        }catch (NotFoundPlaylistException e) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NF_PLAYLIST);
        }
    }

    @RequestMapping(
            value = "/postPlaylistAll",
            method = RequestMethod.POST
    )
    public ResponseEntity postPlaylistAll(@RequestBody List<PlaylistDto> playlistDtoList) {
        System.out.println(playlistDtoList.toString());

        List<Playlist> savedPlaylistList = new ArrayList<>();
        for(PlaylistDto playlistDto : playlistDtoList) {
            // Playlist
            Playlist playlist = playlistDto.toEntity();
            Playlist savedPlaylist = playlistService.postPlaylist(playlist);

            // Playlist Thumbnails
            PlaylistThumbnail playlistThumbnail = playlistDto.getThumbnails().toEntityInPlaylist();
            playlistThumbnail.setPlaylist(savedPlaylist);
            PlaylistThumbnail savedPlaylistThumbnail = playlistThumbnailService.postPlaylistThumbnail(playlistThumbnail);

            System.out.println("### " + savedPlaylistThumbnail.toString());

            List<Video> savedVideoList = new ArrayList<>();
            for(VideoDto videoDto : playlistDto.getVideoList()) {
                // Video
                Video video = videoDto.toEntity();
                video.setPlaylist(playlist);
                Video savedVideo = videoService.postVideo(video);

                // Video Thumbnails
                VideoThumbnail videoThumbnail = videoDto.getThumbnails().toEntityInVideo();
                videoThumbnail.setVideo(savedVideo);
                VideoThumbnail savedVideoThumbnail = videoThumbnailService.postVideoThumbnail(videoThumbnail);

                savedVideoList.add(savedVideo);
            }

            savedPlaylistList.add(savedPlaylist);
        }

        List<Playlist> playlistList = playlistService.getPlaylistAll();

        System.out.println(playlistList.toString());

        // Dto로 변환
        List<PlaylistDto> result = new ArrayList<>();
        for(Playlist playlist : playlistList) {
            PlaylistDto playlistDto = playlist.toDto();

            List<VideoDto> videoDtoList = new ArrayList<>();
            List<Video> videoList = videoService.getVideoAllByPlaylist(playlist);
            for(Video video : videoList) {
                VideoDto videoDto = video.toDto();

                ThumbnailDto thumbnailDto = videoThumbnailService.getVideoThumbnailByVideo(video).toDto();

                videoDto.setThumbnails(thumbnailDto);

                videoDtoList.add(videoDto);
            }
            playlistDto.setVideoList(videoDtoList);

            ThumbnailDto thumbnailDto = playlistThumbnailService.getPlaylistThumbnailByPlaylist(playlist).toDto();
            playlistDto.setThumbnails(thumbnailDto);

            result.add(playlistDto);
        }

        System.out.println(result);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            value = "/deletePlaylistAll",
            method = RequestMethod.DELETE
    )
    public ResponseEntity deletePlaylistAll() {
        playlistService.deletePlaylistAll();

        return ResponseEntity.ok("COMPLETE");
    }
}
