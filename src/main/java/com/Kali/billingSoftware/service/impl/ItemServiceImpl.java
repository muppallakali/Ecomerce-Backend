package com.Kali.billingSoftware.service.impl;

import com.Kali.billingSoftware.entity.CategoryEntity;
import com.Kali.billingSoftware.entity.ItemEntity;
import com.Kali.billingSoftware.io.ItemRequest;
import com.Kali.billingSoftware.io.ItemResponse;
import com.Kali.billingSoftware.repository.CategoryRepository;
import com.Kali.billingSoftware.repository.ItemRepository;
import com.Kali.billingSoftware.service.FileUploadService;
import com.Kali.billingSoftware.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String imgUrl=fileUploadService.uploadFile(file);
        ItemEntity newItem=convertToEntity(request);
        CategoryEntity existingCategory=categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(()->new RuntimeException("Category Not found with id: "+request.getCategoryId()));
        newItem.setCategory(existingCategory);
        newItem.setImgUrl(imgUrl);
        newItem=itemRepository.save(newItem);
        return convertToResponse(newItem);

    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .name(newItem.getName())
                .description(newItem.getDescription())
                .imgUrl(newItem.getImgUrl())
                .price(newItem.getPrice())
                .itemId(newItem.getItemId())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryId())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return  itemRepository.findAll()
                .stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String itemId) {
        ItemEntity existingItem=itemRepository.findByItemId(itemId).orElseThrow(
                ()->new RuntimeException("Item Not Found: "+itemId));
        boolean isFileDelete=fileUploadService.deleteFile(existingItem.getImgUrl());
        if(isFileDelete){
            itemRepository.delete(existingItem);
        }
        else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to delete an item");
        }
    }
}
