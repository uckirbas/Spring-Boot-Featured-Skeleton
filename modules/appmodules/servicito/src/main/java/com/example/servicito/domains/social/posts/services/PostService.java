package com.example.servicito.domains.social.posts.services;

import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.auth.entities.User;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public interface PostService {
    Post find(Long id) throws NotFoundException;

    Page<Post> findPosts(int page, int size);

    Page<Post> findPostsByType(Post.Type type, int page, int size);

    Page<Post> getNewsFeedPosts(int page, int size);

    List<Post> findRentPosts(@NotNull Date fromDate, @NotNull Date toDate);

    Page<Post> findUserPosts(User postedBy, int page, int size) throws InvalidException;

    Page<Post> findComments(Post parent, int page, int size) throws InvalidException;

    Page<Post> findUserCommentsInPost(User postedBy, Post parent, int page, int size);

    Post save(Post post) throws ForbiddenException;

    Post createPostForNewRent(Apartment apartment) throws ForbiddenException;

    Post createPostForNewUserRegistration(User user) throws ForbiddenException;

    void delete(Long postId) throws NotFoundException, ForbiddenException;
}
