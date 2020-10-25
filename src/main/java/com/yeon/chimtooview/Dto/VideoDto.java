package com.yeon.chimtooview.Dto;

import com.yeon.chimtooview.Entity.Video;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VideoDto {
    private String id;

    private String title;
    private LocalDateTime publishedAt;
    private int duration;

    private int viewCount;
    private int commentCount;
    private int likeCount;
    private int dislikeCount;

    private double likeDislikeRate;
    private int likeDislikeGap;
    private double viewLikeRate;
    private int viewLikeGap;
    private double likeDurationRate;

    private ThumbnailDto thumbnails;

    private PlaylistDto playlistDto;
    private String playlistId;
    private String playlistTitle;

    public Video toEntity() {
        Video entity = new Video();
        entity.setId(id);
        entity.setTitle(title);
        entity.setPublishedAt(publishedAt);
        entity.setDuration(duration);
        entity.setViewCount(viewCount);
        entity.setCommentCount(commentCount);
        entity.setLikeCount(likeCount);
        entity.setDislikeCount(dislikeCount);
        // 이 메소드는 처음 데이터를 디비에 입력할 때만 호출되므로 2차 수치들은 여기서 작업을 해줘야 함
        entity.setLikeDislikeRate((dislikeCount == 0 ? 0 : ((double)likeCount / dislikeCount)));
        entity.setLikeDislikeGap(likeCount - dislikeCount);
        entity.setViewLikeRate((viewCount == 0 ? 0 : ((double)likeCount / viewCount)));
        entity.setViewLikeGap(viewCount - likeCount);
        entity.setLikeDurationRate(duration == 0 ? 0 : ((double)likeCount / duration));

        return entity;
    }
}
