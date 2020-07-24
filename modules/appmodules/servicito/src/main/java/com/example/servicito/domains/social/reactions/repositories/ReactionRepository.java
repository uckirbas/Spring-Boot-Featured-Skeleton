package com.example.servicito.domains.social.reactions.repositories;

import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;
import com.example.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    List<Reaction> findByPostId(Long postId);

    Reaction findFirstByReactedByAndPost(User user, Post post);

    @Query("SELECT COUNT (r) FROM Reaction r WHERE r.post=:post AND r.type=:reactionType")
    Long getPostReactionCount(@Param("post") Post post, @Param("reactionType") Reaction.Type reaction);

    @Query("SELECT r FROM Reaction r WHERE r.post=:post AND r.reactedBy=:user")
    Reaction getUserReactionInPost(@Param("user") User user, @Param("post") Post post);

    @Modifying
    @Query("DELETE FROM Reaction r WHERE r.post=:post")
    void deleteByPost(@Param("post") Post post);
}
