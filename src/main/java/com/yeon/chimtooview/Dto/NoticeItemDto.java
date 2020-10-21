package com.yeon.chimtooview.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeItemDto {
    private long id;

    private String type;

    private String title;
    private String content;

    private LocalDateTime publishedAt;
}
