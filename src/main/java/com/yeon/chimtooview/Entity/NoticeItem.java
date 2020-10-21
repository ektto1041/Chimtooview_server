package com.yeon.chimtooview.Entity;

import com.yeon.chimtooview.Dto.NoticeItemDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTICE_ITEM")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeItem {
    @Id @GeneratedValue
    @Column(name = "NOTICE_ITEM_ID")
    private long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "PUBLISHED_AT")
    private LocalDateTime publishedAt;

    public NoticeItemDto toDto() {
        NoticeItemDto dto = new NoticeItemDto();
        dto.setId(id);
        dto.setType(type);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setPublishedAt(publishedAt);

        return dto;
    }
}
