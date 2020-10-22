package com.yeon.chimtooview.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yeon.chimtooview.Dto.VideoDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VIDEO")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    @Column(name = "VIDEO_ID")
    private String id;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "PUBLISHED_AT")
    private LocalDateTime publishedAt;
    @Column(name = "DURATION")
    private int duration;

    @Column(name = "VIEW_COUNT")
    private int viewCount;
    @Column(name = "COMMENT_COUNT")
    private int commentCount;
    @Column(name = "LIKE_COUNT")
    private int likeCount;
    @Column(name = "DISLIKE_COUNT")
    private int dislikeCount;

    @Column(name = "LIKE_RATE")
    private double likeDislikeRate;
    @Column(name = "LIKE_GAP")
    private int likeDislikeGap;
    @Column(name = "VIEW_LIKE_RATE")
    private double viewLikeRate;
    @Column(name = "VIEW_LIKE_GAP")
    private int viewLikeGap;
    @Column(name = "LIKE_DURATION_RATE")
    private double likeDurationRate;

    @OneToOne(mappedBy = "video", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private VideoThumbnail videoThumbnail;

    @ManyToOne
    @JoinColumn(name = "PLAYLIST_ID")
    @JsonManagedReference
    private Playlist playlist;

    // Playlist 정보가 필요없는 Dto
    public VideoDto toDto() {
        VideoDto dto = new VideoDto();
        dto.setId(id);
        dto.setTitle(title);
        dto.setPublishedAt(publishedAt);
        dto.setDuration(duration);
        dto.setViewCount(viewCount);
        dto.setCommentCount(commentCount);
        dto.setLikeCount(likeCount);
        dto.setDislikeCount(dislikeCount);
        dto.setLikeDislikeRate(likeDislikeRate);
        dto.setLikeDislikeGap(likeDislikeGap);
        dto.setViewLikeRate(viewLikeRate);
        dto.setViewLikeGap(viewLikeGap);
        dto.setLikeDurationRate(likeDurationRate);

        dto.setPlaylistId(playlist.getId());
        dto.setPlaylistTitle(playlist.getTitle());

        dto.setPlaylistDto(null);

        return dto;
    }
}