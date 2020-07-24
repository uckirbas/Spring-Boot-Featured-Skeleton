package com.example.servicito.domains.social.posts.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.servicito.domains.social.posts.models.entities.Post;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class PostRequest {

    private Long id;
    @NotNull
    @Size(min = 1)
    private String content;

    private Post.Type type;

    @JsonProperty("parent_id")
    private Long parentId;

    private List<String> images;
    private List<String> thumbs;

    public Post.Type getType() {
        return type;
    }

    public void setType(Post.Type type) {
        this.type = type;
    }

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
