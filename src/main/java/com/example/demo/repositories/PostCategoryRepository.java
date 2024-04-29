package com.example.demo.repositories;

import com.example.demo.models.PostCategory;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Integer>, JpaSpecificationExecutor<PostCategory> {
}
