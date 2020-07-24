package com.example.servicito.domains.social.posts.services;

import com.example.coreweb.utils.PageAttr;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.servicito.domains.social.posts.repositories.PostRepository;
import com.example.auth.entities.User;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepo;

    @Value("${applicationName}")
    private String appName;

    @Autowired
    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public Post find(Long id) throws NotFoundException {
        Post post = this.postRepo.findById(id).orElse(null);
        if (post == null) throw new NotFoundException("Could not find post for id: " + id);
        return post;
    }

    @Override
    public Page<Post> findPosts(int page, int size) {
        return this.postRepo.findPosts(PageAttr.getPageRequest(page, size));
    }

    @Override
    public Page<Post> findPostsByType(Post.Type type, int page, int size) {
        return this.postRepo.findPostsByType(type, PageAttr.getPageRequest(page, size));
    }

    @Override
    public Page<Post> getNewsFeedPosts(int page, int size) {
        return this.postRepo.getNewsFeedPosts(PageAttr.getPageRequest(page, size));
    }

    @Override
    public List<Post> findRentPosts(@NotNull Date fromDate, @NotNull Date toDate) {
        return this.postRepo.findRentPosts(fromDate, toDate);
    }

    @Override
    public Page<Post> findUserPosts(@NotNull User postedBy, int page, int size) throws InvalidException {
//        if (postedBy==null) throw new InvalidException("Could not find comments for post: null!");
        return this.postRepo.getUserPosts(postedBy, PageAttr.getPageRequest(page, size));
    }

    @Override
    public Page<Post> findComments(@NotNull Post parent, int page, int size) throws InvalidException {
//        if (parent==null) throw new InvalidException("Could not find post with parent null!");
        return this.postRepo.getComments(parent, PageAttr.getPageRequest(page, size));
    }

    @Override
    public Page<Post> findUserCommentsInPost(@NotNull User postedBy, @NotNull Post parent, int page, int size) {
        return this.postRepo.getUserComments(postedBy, parent, PageAttr.getPageRequest(page, size));
    }

    @Override
    public Post save(@NotNull Post post) throws ForbiddenException {
        if (post.getId() != null && !post.isAuthoizedToModify())
            throw new ForbiddenException("You're not authorized to do this action!");
        return this.postRepo.save(post);
    }

    @Override
    public Post createPostForNewRent(Apartment apartment) throws ForbiddenException {
        Post post = new Post();
        post.setType(Post.Type.HOME_RENT);
        post.setPostedBy(apartment.getBuilding().getLandlord());
        post.setImages(apartment.getImages());
        post.setThumbs(apartment.getImages());
        post.setContent("I made a new apartment available for rent");
        post.setActionLink(apartment.getLink());
        return this.save(post);
    }

    @Override
    public Post createPostForNewUserRegistration(User user) throws ForbiddenException {
        Post post = new Post();
        post.setType(Post.Type.AUTO_GENERATED);
        post.setPostedBy(user);
        post.setContent("Hi, I just joined " + this.appName + "! Give me a warm welcome :)");
        return this.save(post);
    }

    @Override
    @Transactional
    public void delete(Long postId) throws NotFoundException, ForbiddenException {
        Post post = this.find(postId);
        if (!post.isAuthoizedToModify())
            throw new ForbiddenException("You're not authorized to do this action.");
        post.setDeleted(true);
        this.save(post);
    }
}
