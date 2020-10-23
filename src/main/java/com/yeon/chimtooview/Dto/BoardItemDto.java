package com.yeon.chimtooview.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardItemDto {
    private long id;

    private String userId;
    private LocalDateTime publishedAt;

    private String title;
    private String content;

    private int viewCount;

    private List<CommentItemDto> commentItemList = new ArrayList<>();
}
