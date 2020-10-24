package com.yeon.chimtooview.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "REPORTS")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reports {
    @Id @GeneratedValue
    @Column(name = "REPORTS_ID")
    private long id;

    @Column(name = "Type")
    private String type;
    @Column(name = "ITEM_ID")
    private long itemId;
    @Column(name = "CONTENT")
    private String content;
}
