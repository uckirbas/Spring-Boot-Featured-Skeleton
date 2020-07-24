package com.example.servicito.domains.social.reactions.models.mappers;

import com.example.servicito.domains.social.posts.models.dto.mappers.PostMapper;
import com.example.servicito.domains.social.posts.services.PostService;
import com.example.servicito.domains.social.reactions.models.dto.ReactionRequest;
import com.example.servicito.domains.social.reactions.models.dto.ReactionResponse;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;
import com.example.auth.entities.User;
import com.example.acl.domains.users.services.UserService;
import com.example.common.exceptions.notfound.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionMapper {
    private final ModelMapper modelMapper;
    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;

    @Autowired
    public ReactionMapper(ModelMapper modelMapper, PostService postService, UserService userService, PostMapper postMapper) {
        this.modelMapper = modelMapper;
        this.postService = postService;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    public Reaction map(ReactionRequest reactionRequest) throws NotFoundException {
        Reaction reaction = this.modelMapper.map(reactionRequest, Reaction.class);
        reaction.setId(reactionRequest.getId());
        reaction.setPost(this.postService.find(reactionRequest.getPostId()));
        User reactedBy = this.userService.find(reactionRequest.getReactedById()).orElseThrow(() -> new NotFoundException("Couldn't find user with id: " + reactionRequest.getReactedById()));
        reaction.setReactedBy(reactedBy);
        return reaction;
    }

    public ReactionResponse map(Reaction reaction) {
        ReactionResponse reactionResponse = this.modelMapper.map(reaction, ReactionResponse.class);
        reactionResponse.setId(reaction.getId());
        reactionResponse.setPostResponse(this.postMapper.map(reaction.getPost()));
        reactionResponse.setReactedById(reaction.getReactedBy().getId());
        return reactionResponse;
    }
}
