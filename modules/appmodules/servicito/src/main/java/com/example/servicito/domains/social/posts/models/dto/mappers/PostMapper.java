package com.example.servicito.domains.social.posts.models.dto.mappers;

import com.example.auth.config.security.SecurityContext;
import com.example.servicito.domains.social.posts.models.dto.PostRequest;
import com.example.servicito.domains.social.posts.models.dto.PostResponse;
import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.servicito.domains.social.posts.services.PostService;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;
import com.example.servicito.domains.social.reactions.services.ReactionService;
import com.example.auth.entities.User;
import com.example.common.exceptions.notfound.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostMapper {
    private final ModelMapper modelMapper;
    private final PostService postService;
    private final ReactionService reactionService;

    @Autowired
    public PostMapper(ModelMapper modelMapper, PostService postService, ReactionService reactionService) {
        this.modelMapper = modelMapper;
        this.postService = postService;
        this.reactionService = reactionService;
    }

    public PostResponse map(Post post) {
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        postResponse.setId(post.getId());
        if (post.getParent() != null) postResponse.setParentId(post.getParent().getId());
        postResponse.setPostedById(post.getPostedBy().getId());
        postResponse.setPostedByName(post.getPostedBy().getName());
        postResponse.setActionLink(post.getActionLink());
        postResponse.setReactionStats(this.reactionService.getStatsForPost(post));
        postResponse.setPostedByPhoto("/api/v1/profiles/profilePic/" + post.getPostedBy().getUsername());
        postResponse.setCreatedAt(post.getCreated());
        Reaction selfReaction = this.reactionService.getUserReactionInPost(new User(SecurityContext.getCurrentUser()), post);
        postResponse.setSelfReactionType(selfReaction == null ? null : selfReaction.getType());
        postResponse.setLastUpdated(post.getLastUpdated());
        return postResponse;
    }

    public Post map(PostRequest postRequest, Post post) throws NotFoundException {
        if (post == null) post = new Post();
        post.setContent(postRequest.getContent());
        post.setType(postRequest.getType() != null ? postRequest.getType() : Post.Type.STATUS);
        if (postRequest.getParentId() != null)
            post.setParent(this.postService.find(postRequest.getParentId()));
        if (postRequest.getImages() != null && !postRequest.getImages().isEmpty())
            post.setImages(postRequest.getImages());
        if (postRequest.getThumbs() != null && !postRequest.getThumbs().isEmpty())
            post.setThumbs(postRequest.getThumbs());
        if (post.getId() == null) // only when new post is created
            post.setPostedBy(new User(SecurityContext.getCurrentUser()));
        return post;
    }

}
