package com.Kali.billingSoftware.service;

import com.Kali.billingSoftware.io.CategoryRequest;
import com.Kali.billingSoftware.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryResponse add(CategoryRequest request, MultipartFile file);
    List<CategoryResponse>read();
    void delete(String categoryId);
}
