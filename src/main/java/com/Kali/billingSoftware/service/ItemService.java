package com.Kali.billingSoftware.service;

import com.Kali.billingSoftware.io.ItemRequest;
import com.Kali.billingSoftware.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    ItemResponse add(ItemRequest request, MultipartFile file);
    List<ItemResponse> fetchItems();
    void delete(String itemId);
}
