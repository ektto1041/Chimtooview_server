package com.yeon.chimtooview.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RELOAD")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reload {
    @Id @GeneratedValue
    @Column(name = "RELOAD_ID")
    private long id;

    @Column(name = "TIME")
    private LocalDateTime time;
}
