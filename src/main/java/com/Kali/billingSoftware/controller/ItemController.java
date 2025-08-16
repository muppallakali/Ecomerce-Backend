package com.Kali.billingSoftware.controller;

import com.Kali.billingSoftware.io.ItemRequest;
import com.Kali.billingSoftware.io.ItemResponse;
import com.Kali.billingSoftware.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

   @PostMapping("/admin/items")
   public ItemResponse addItems(@RequestPart("item") String itemString,@RequestPart("file") MultipartFile file){
       ObjectMapper objectMapper=new ObjectMapper();
       ItemRequest requestItem=null;
       try{
           requestItem=objectMapper.readValue(itemString,ItemRequest.class);
           return itemService.add(requestItem,file);
       }
       catch (JsonProcessingException ex){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Adding Item failed");
       }
   }
    @GetMapping("/items")
    public List<ItemResponse> readItems(){
        return itemService.fetchItems();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
@DeleteMapping("/admin/items/{itemId}")
    public void removeItem(@PathVariable String itemId){
        try {
            itemService.delete(itemId);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item not found");
        }
}
}
