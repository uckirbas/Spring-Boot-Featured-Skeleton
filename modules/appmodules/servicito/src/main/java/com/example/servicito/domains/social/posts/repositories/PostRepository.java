package com.example.servicito.domains.social.posts.repositories;

import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.auth.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findFirstByFixedFalse();

    @Query("SELECT p FROM Post p WHERE p.parent IS NULL AND p.deleted=0")
    Page<Post> findPosts(Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.type=:post_type AND p.parent IS NULL AND p.deleted=0")
    Page<Post> findPostsByType(@Param("post_type") Post.Type type, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE (p.type='STATUS' OR p.type='HOME_RENT') AND p.parent IS NULL AND p.deleted=0")
    Page<Post> getNewsFeedPosts(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.type='HOME_RENT' AND ( p.created BETWEEN :fromDate AND :toDate) AND p.parent IS NULL AND p.deleted=0")
    List<Post> findRentPosts(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT p FROM Post  p WHERE p.postedBy=:user AND p.parent IS NULL AND p.deleted=0")
    Page<Post> getUserPosts(@Param("user") User user, Pageable pageable);

    @Query("SELECT p FROM Post  p WHERE p.parent=:parent AND p.deleted=0")
    Page<Post> getComments(@Param("parent") Post parent, Pageable pageable);

    @Query("SELECT p FROM Post  p WHERE p.postedBy=:user AND p.parent=:parent AND p.deleted=0")
    Page<Post> getUserComments(@Param("user") User user, @Param("parent") Post parent, Pageable pageable);

    @Query("SELECT COUNT (p) FROM Post p WHERE p.parent=:post AND p.deleted=0")
    Long getNoOfComments(@Param("post") Post post);

}
