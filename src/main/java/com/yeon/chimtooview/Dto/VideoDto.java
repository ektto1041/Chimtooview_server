package com.yeon.chimtooview.Dto;

import com.yeon.chimtooview.Entity.Video;
import lombok.*;

import javax.persistence.Column;
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
    private String duration;

    private int viewCount;
    private int commentCount;
    private int likeCount;
    private int dislikeCount;

    private double likeRate;
    private int likeGap;
    private double viewLikeRate;
    private int viewLikeGap;

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
        entity.setLikeRate((dislikeCount == 0 ? 0 : ((double)likeCount / dislikeCount)));
        entity.setLikeGap(likeCount - dislikeCount);
        entity.setViewLikeRate((viewCount == 0 ? 0 : ((double)likeCount / viewCount)));
        entity.setViewLikeGap(viewCount - likeCount);

        return entity;
    }
}
