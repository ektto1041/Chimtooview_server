package com.yeon.chimtooview.Repository;

import com.yeon.chimtooview.Entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    Optional<Playlist> findById(String id);
}
