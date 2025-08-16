package com.Kali.billingSoftware.io;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
@Data
@Builder
public class CategoryResponse {
    private String name;
    private String description;
    private String bgColor;
    private String categoryId;
    private String imgUrl;
    private Timestamp createdAt;
    private Timestamp updatedAT;
    private Integer items;
}
