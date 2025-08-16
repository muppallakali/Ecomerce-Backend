package com.Kali.billingSoftware.service.impl;

import com.Kali.billingSoftware.entity.CategoryEntity;
import com.Kali.billingSoftware.io.CategoryRequest;
import com.Kali.billingSoftware.io.CategoryResponse;
import com.Kali.billingSoftware.repository.CategoryRepository;
import com.Kali.billingSoftware.repository.ItemRepository;
import com.Kali.billingSoftware.service.CategoryService;
import com.Kali.billingSoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    private final ItemRepository itemRepository;
    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {
        String imgUrl=fileUploadService.uploadFile(file);
        CategoryEntity newCategory=convertTOEntity(request);
        newCategory.setImgUrl(imgUrl);
        newCategory= categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll().stream()
                .map(this::convertToResponse)//map(this::convertToResponse(categoryEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity existingCategory=categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(()->new RuntimeException("category not found"+categoryId));
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        categoryRepository.delete(existingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        Integer itemsCount= itemRepository.countByCategoryId(newCategory.getId());
        return CategoryResponse.builder().categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAT(newCategory.getUpdatedAT())
                .items(itemsCount)
                .build();
    }

    private CategoryEntity convertTOEntity(CategoryRequest request) {
        return CategoryEntity.builder().categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
}
