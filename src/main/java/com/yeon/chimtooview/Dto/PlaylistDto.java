package com.yeon.chimtooview.Dto;

import com.yeon.chimtooview.Entity.Playlist;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaylistDto {
    private String id;

    private String title;
    private String description;
    private LocalDateTime publishedAt;

    private int viewCountSum;
    private double viewCountAvg;
    private int likeCountSum;
    private double likeCountAvg;
    private int dislikeCountSum;
    private double dislikeCountAvg;

    private ThumbnailDto thumbnails;

    private List<VideoDto> videoList = new ArrayList<>();

    public Playlist toEntity() {
        Playlist entity = new Playlist();
        entity.setId(id);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setPublishedAt(publishedAt);
        entity.setViewCountSum(viewCountSum);
        entity.setViewCountAvg(viewCountAvg);
        entity.setLikeCountSum(likeCountSum);
        entity.setLikeCountAvg(likeCountAvg);
        entity.setDislikeCountSum(dislikeCountSum);
        entity.setDislikeCountAvg(dislikeCountAvg);

        return entity;
    }
}
