package com.yeon.chimtooview.Service;

import com.yeon.chimtooview.Entity.Playlist;
import com.yeon.chimtooview.Entity.PlaylistThumbnail;
import com.yeon.chimtooview.Repository.PlaylistThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistThumbnailService {
    @Autowired
    private PlaylistThumbnailRepository playlistThumbnailRepository;

    public PlaylistThumbnail getPlaylistThumbnailByPlaylist(Playlist playlist) {
        PlaylistThumbnail playlistThumbnail = playlistThumbnailRepository.findByPlaylist(playlist).orElse(null);

        return playlistThumbnail;
    }

    public PlaylistThumbnail postPlaylistThumbnail(PlaylistThumbnail playlistThumbnail) {
        PlaylistThumbnail playlistThumbnailFromDb = null;

        if(playlistThumbnail.getPlaylist() != null) {
            playlistThumbnailFromDb = playlistThumbnailRepository.findByPlaylist(playlistThumbnail.getPlaylist()).orElse(null);
        }

        PlaylistThumbnail result = null;

        if(playlistThumbnailFromDb == null) {
            result = playlistThumbnailRepository.save(playlistThumbnail);
        } else {
            result = playlistThumbnailFromDb;
        }

        return result;
    }
}
