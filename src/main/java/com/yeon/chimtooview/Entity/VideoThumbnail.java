package com.yeon.chimtooview.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yeon.chimtooview.Dto.ThumbnailDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "VIDEO_THUMBNAIL")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoThumbnail {
    @Id
    @GeneratedValue
    @Column(name = "VIDEO_THUMBNAIL_ID")
    private Long id;

    @Column(name = "DEFAULT_SIZE")
    private String defaultSize;

    @Column(name = "MEDIUM_SIZE")
    private String mediumSize;

    @Column(name = "HIGH_SIZE")
    private String highSize;

    @Column(name = "STANDARD_SIZE")
    private String standardSize;

    @Column(name = "NAXRES_SIZE")
    private String maxresSize;

    @JoinColumn(name = "VIDEO")
    @OneToOne
    private Video video;

    public ThumbnailDto toDto() {
        ThumbnailDto dto = new ThumbnailDto();
        dto.setId(id);
        dto.setDefaultSize(defaultSize);
        dto.setMediumSize(mediumSize);
        dto.setHighSize(highSize);
        dto.setStandardSize(standardSize);
        dto.setMaxresSize(maxresSize);

        return dto;
    }
}
