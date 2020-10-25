package com.yeon.chimtooview.Repository;

import com.yeon.chimtooview.Entity.Playlist;
import com.yeon.chimtooview.Entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Optional<Video> findById(String id);

    List<Video> findAllByPlaylist(Playlist playlist);
}
