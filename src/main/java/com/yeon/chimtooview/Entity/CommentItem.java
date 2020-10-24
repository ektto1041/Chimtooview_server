package com.yeon.chimtooview.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yeon.chimtooview.Dto.CommentItemDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENT_ITEM")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentItem {
    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ITEM_ID")
    private long id;

    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USER_PW")
    private String userPw;
    @Column(name = "SALT")
    private String salt;
    @Column(name = "PUBLISHED_AT")
    private LocalDateTime publishedAt;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "BOARD_ITEM_ID")
    @JsonManagedReference
    private BoardItem boardItem;

    public CommentItemDto toDto() {
        CommentItemDto dto = new CommentItemDto();
        dto.setId(id);
        dto.setUserId(userId);
        dto.setUserPw(userPw);
        dto.setSalt(salt);
        dto.setPublishedAt(publishedAt);
        dto.setContent(content);

        return dto;
    }
}
