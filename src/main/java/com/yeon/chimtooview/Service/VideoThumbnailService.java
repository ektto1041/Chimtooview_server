package com.yeon.chimtooview.Service;

import com.yeon.chimtooview.Entity.Video;
import com.yeon.chimtooview.Entity.VideoThumbnail;
import com.yeon.chimtooview.Repository.VideoThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoThumbnailService {
    @Autowired
    private VideoThumbnailRepository videoThumbnailRepository;

    public VideoThumbnail getVideoThumbnailByVideo(Video video) {
        VideoThumbnail videoThumbnail = videoThumbnailRepository.findByVideo(video).orElse(null);

        return videoThumbnail;
    }


    public VideoThumbnail postVideoThumbnail(VideoThumbnail videoThumbnail) {
        VideoThumbnail videoThumbnailFromDb = null;

        if(videoThumbnail.getVideo() != null) {
            videoThumbnailFromDb = videoThumbnailRepository.findByVideo(videoThumbnail.getVideo()).orElse(null);
        }

        VideoThumbnail result = null;

        if(videoThumbnailFromDb == null) {
            result = videoThumbnailRepository.save(videoThumbnail);
        } else {
            result = videoThumbnailFromDb;
        }

        return result;
    }
}
