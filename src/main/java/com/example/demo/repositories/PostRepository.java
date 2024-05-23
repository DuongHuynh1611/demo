package com.example.demo.repositories;

import com.example.demo.models.Post;
import com.example.demo.models.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post,Integer>, JpaSpecificationExecutor<Post> {
}
