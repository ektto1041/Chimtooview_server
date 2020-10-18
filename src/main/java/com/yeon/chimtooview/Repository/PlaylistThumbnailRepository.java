package com.yeon.chimtooview.Repository;

import com.yeon.chimtooview.Entity.Playlist;
import com.yeon.chimtooview.Entity.PlaylistThumbnail;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistThumbnailRepository extends JpaRepository<PlaylistThumbnail, Long> {
    Optional<PlaylistThumbnail> findById(Long id);

    Optional<PlaylistThumbnail> findByPlaylist(Playlist playlist);
}
