package com.yeon.chimtooview.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentItemDto {
    private long id;

    private String userId;
    private String userPw;
    private String salt;
    private LocalDateTime publishedAt;

    private String content;

    private BoardItemDto boardItemDto;
}
