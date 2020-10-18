package com.yeon.chimtooview.Dto;

import com.yeon.chimtooview.Entity.PlaylistThumbnail;
import com.yeon.chimtooview.Entity.VideoThumbnail;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ThumbnailDto {
    private Long id;

    private String defaultSize;
    private String mediumSize;
    private String highSize;
    private String standardSize;
    private String maxresSize;

    public PlaylistThumbnail toEntityInPlaylist() {
        PlaylistThumbnail entity = new PlaylistThumbnail();
        if(id != null) { entity.setId(id); }
        entity.setDefaultSize(defaultSize);
        entity.setMediumSize(mediumSize);
        entity.setHighSize(highSize);
        entity.setStandardSize(standardSize);
        entity.setMaxresSize(maxresSize);

        return entity;
    }

    public VideoThumbnail toEntityInVideo() {
        VideoThumbnail entity = new VideoThumbnail();
        if(id != null) { entity.setId(id); }
        entity.setDefaultSize(defaultSize);
        entity.setMediumSize(mediumSize);
        entity.setHighSize(highSize);
        entity.setStandardSize(standardSize);
        entity.setMaxresSize(maxresSize);

        return entity;
    }
}
