package com.Kali.billingSoftware.controller;

import com.Kali.billingSoftware.io.CategoryRequest;
import com.Kali.billingSoftware.io.CategoryResponse;
import com.Kali.billingSoftware.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/admin/addCategory")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category")String categoryString,
                                        @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper=new ObjectMapper();
        CategoryRequest request=null;
        try{
            request=objectMapper.readValue(categoryString,CategoryRequest.class);
            return categoryService.add(request,file);
        }
        catch(JsonProcessingException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Exception occurred while Parsing json"+ex.getMessage());
        }
    }
    @GetMapping("/getAllCategories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse>fetchCategory(){
        return categoryService.read();
    }
    @DeleteMapping("/admin/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String id){
        try{
            categoryService.delete(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
