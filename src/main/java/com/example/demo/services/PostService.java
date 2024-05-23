package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.models.PostCategory;
import com.example.demo.repositories.PostCategoryRepository;
import com.example.demo.repositories.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> finAll(){
        return postRepository.findAll();
    }


    public Post findById(Integer id) {
        return postRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Post not found with Id: "+id));
    }

    public Post deleteById(int id) {
        postRepository.deleteById(id);
        Iterator<Post> iterator = postRepository.findAll().iterator();
        while (iterator.hasNext()){
            Post post = iterator.next();
            if (post.getId()==id){
                iterator.remove();
                return post;
            }
        }
        return null;
    }
}
