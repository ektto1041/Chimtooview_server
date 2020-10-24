package com.yeon.chimtooview.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yeon.chimtooview.Dto.BoardItemDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOARD_ITEM")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardItem {
    @Id @GeneratedValue
    @Column(name = "BOARD_ITEM_ID")
    private long id;

    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USER_PW")
    private String userPw;
    @Column(name = "SALT")
    private String salt;
    @Column(name = "PUBLISHED_AT")
    private LocalDateTime publishedAt;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "VIEW_COUNT")
    private int viewCount;

    @OneToMany(mappedBy = "boardItem", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<CommentItem> commentItemList = new ArrayList<>();

    public BoardItemDto toDto() {
        BoardItemDto dto = new BoardItemDto();
        dto.setId(id);
        dto.setUserId(userId);
        dto.setUserPw(userPw);
        dto.setSalt(salt);
        dto.setPublishedAt(publishedAt);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setViewCount(viewCount);
        dto.setCommentItemCount(commentItemList.size());

        return dto;
    }
}
