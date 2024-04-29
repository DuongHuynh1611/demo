package com.example.demo.services;

import com.example.demo.models.PostCategory;
import com.example.demo.models.User;
import com.example.demo.repositories.PostCategoryRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class PostCategoryService {
    @Autowired
    private PostCategoryRepository postCategoryRepository;

    public PostCategory savePostCategory(PostCategory postCategory) {
        return postCategoryRepository.save(postCategory);
    }

    public List<PostCategory> finAll(){
        return postCategoryRepository.findAll();
    }


    public PostCategory findById(Integer id) {
        return postCategoryRepository.findById(id).orElse(null);
    }

    public PostCategory deleteById(int id) {
        postCategoryRepository.deleteById(id);
        Iterator<PostCategory> iterator = postCategoryRepository.findAll().iterator();
        while (iterator.hasNext()){
            PostCategory postCategory = iterator.next();
            if (postCategory.getId()==id){
                iterator.remove();
                return postCategory;
            }
        }
        return null;
    }
}
