package com.example.demo.controllers;

import com.example.demo.form.CreateCategoryForm;
import com.example.demo.form.UpdateCategoryForm;
import com.example.demo.models.PostCategory;
import com.example.demo.services.PostCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post-category")
public class CategoryController {

    @Autowired
    private PostCategoryService postCategoryService;

    @GetMapping()
    List<PostCategory> finAllPostCategory(){
        return postCategoryService.finAll();
    }

    @GetMapping("/{id}")
    private Object getById(@PathVariable(value = "id") Integer id) {
        PostCategory postCategory = postCategoryService.findById(id);
        return postCategory;
    }

    @PostMapping()
    private Object createCategory(@Valid @RequestBody CreateCategoryForm createCategoryForm){
        PostCategory postCategory = new PostCategory();
        postCategory.setName(createCategoryForm.getName());
        postCategoryService.savePostCategory(postCategory);

        return postCategory;
    }

    @PutMapping("/{id}")
    private Object updateCategory(@Valid @RequestBody UpdateCategoryForm updateCategoryForm){
        PostCategory postCategory = postCategoryService.findById(updateCategoryForm.getId());
        postCategory.setName(updateCategoryForm.getName());
        return postCategoryService.savePostCategory(postCategory);
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable(value = "id") Integer id) {
        PostCategory postCategory = postCategoryService.deleteById(id);
        //return "Delete ok!";
    }
}
