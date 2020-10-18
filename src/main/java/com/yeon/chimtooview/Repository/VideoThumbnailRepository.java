package com.yeon.chimtooview.Repository;

import com.yeon.chimtooview.Entity.Video;
import com.yeon.chimtooview.Entity.VideoThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoThumbnailRepository extends JpaRepository<VideoThumbnail, Long> {
    Optional<VideoThumbnail> findById(Long id);

    Optional<VideoThumbnail> findByVideo(Video video);
}
