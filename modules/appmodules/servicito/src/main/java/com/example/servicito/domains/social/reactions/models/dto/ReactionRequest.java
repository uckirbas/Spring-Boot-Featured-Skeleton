package com.example.servicito.domains.social.reactions.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;

import javax.validation.constraints.NotNull;

public class ReactionRequest {
    private Long id;

    @JsonProperty("reaction_type")
    @NotNull
    private Reaction.Type type;

    @JsonProperty("post_id")
    @NotNull
    private Long postId;

    @JsonProperty("reacted_by_id")
    @NotNull
    private Long reactedById;

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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getReactedById() {
        return reactedById;
    }

    public void setReactedById(Long reactedById) {
        this.reactedById = reactedById;
    }
}
