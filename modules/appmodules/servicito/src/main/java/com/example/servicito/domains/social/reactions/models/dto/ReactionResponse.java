package com.example.servicito.domains.social.reactions.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.servicito.domains.social.posts.models.dto.PostResponse;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;

public class ReactionResponse {
    private Long id;

    @JsonProperty("reaction_type")
    private Reaction.Type type;

    @JsonProperty("reacted_by_id")
    private Long reactedById;

    @JsonProperty("post")
    private PostResponse postResponse;

    public PostResponse getPostResponse() {
        return postResponse;
    }

    public void setPostResponse(PostResponse postResponse) {
        this.postResponse = postResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reaction.Type getType() {
        return type;
    }

    public void setType(Reaction.Type type) {
        this.type = type;
    }

    public Long getReactedById() {
        return reactedById;
    }

    public void setReactedById(Long reactedById) {
        this.reactedById = reactedById;
    }
}
