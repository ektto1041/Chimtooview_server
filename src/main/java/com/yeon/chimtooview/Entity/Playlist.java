package com.yeon.chimtooview.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yeon.chimtooview.Dto.PlaylistDto;
import com.yeon.chimtooview.Dto.VideoDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PLAYLIST")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    @Id
    @Column(name = "PLAYLIST_ID")
    private String id;
    @Column(name = "OWNERS")
    private int owners;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PUBLISHED_AT")
    private LocalDateTime publishedAt;

    // 아래 여섯 데이터는 제일 처음 Posting 될 때 PlaylistService 에서 대입함
    @Column(name = "VIEW_COUNT_SUM")
    private int viewCountSum;
    @Column(name = "VIEW_COUNT_AVG")
    private double viewCountAvg;
    @Column(name = "LIKE_COUNT_SUM")
    private int likeCountSum;
    @Column(name = "LIKE_COUNT_AVG")
    private double likeCountAvg;
    @Column(name = "DISLIKE_COUNT_SUM")
    private int dislikeCountSum;
    @Column(name = "DISLIKE_COUNT_AVG")
    private double dislikeCountAvg;

    @OneToOne(mappedBy = "playlist", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private PlaylistThumbnail playlistThumbnail;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Video> videoList = new ArrayList<>();

    public PlaylistDto toDto() {
        PlaylistDto dto = new PlaylistDto();

        dto.setId(id);
        dto.setOwners(owners);
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setPublishedAt(publishedAt);

        dto.setViewCountSum(viewCountSum);
        dto.setViewCountAvg(viewCountAvg);
        dto.setLikeCountSum(likeCountSum);
        dto.setLikeCountAvg(likeCountAvg);
        dto.setDislikeCountSum(dislikeCountSum);
        dto.setDislikeCountAvg(dislikeCountAvg);

        List<VideoDto> videoDtoList = new ArrayList<>();
        for(Video video : videoList) {
            videoDtoList.add(video.toDto());
        }

        dto.setVideoList(videoDtoList);

        return dto;
    }
}
