package com.example.demo.controllers;

import com.example.demo.form.CreateCategoryForm;
import com.example.demo.form.UpdateCategoryForm;
import com.example.demo.models.PostCategory;
import com.example.demo.services.PostCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        if (null == postCategory)
            throw new UserNotFoundException("id-" + id);

        EntityModel<PostCategory> entityModel = EntityModel.of(postCategory);
        Link link = WebMvcLinkBuilder.linkTo(
                methodOn(this.getClass()).finAllPostCategory()).withRel("all-users");
        entityModel.add(link);
        return entityModel;
    }

    @PostMapping()
    private Object createCategory(@Valid @RequestBody CreateCategoryForm createCategoryForm){
        PostCategory postCategory = new PostCategory();
        postCategory.setId(createCategoryForm.getId());
        postCategory.setFirstName(createCategoryForm.getFirstName());
        postCategory.setLastName(createCategoryForm.getLastName());
//        user.setNow(createForm.getNow());
        postCategoryService.savePostCategory(postCategory);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createCategoryForm.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    private Object updateCategory(@Valid @RequestBody UpdateCategoryForm updateCategoryForm){
        PostCategory postCategory = postCategoryService.findById(updateCategoryForm.getId());
        postCategory.setFirstName(updateCategoryForm.getFirstName());
        postCategory.setLastName(updateCategoryForm.getLastName());
        return postCategoryService.savePostCategory(postCategory);
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable(value = "id") Integer id) {
        PostCategory postCategory = postCategoryService.deleteById(id);
        //return "Delete ok!";
        if (null == postCategory)
            throw new UserNotFoundException("id-" + id);
    }
}
