package com.Kali.billingSoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
    private String name;
    private String categoryId;
    private String description;
    private BigDecimal price;
    private String imgUrl;
    private String itemId;
    private String categoryName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
