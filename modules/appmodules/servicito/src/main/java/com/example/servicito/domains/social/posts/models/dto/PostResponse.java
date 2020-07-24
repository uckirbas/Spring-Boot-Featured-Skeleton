package com.example.servicito.domains.social.posts.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.servicito.domains.social.reactions.models.dto.ReactionStats;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class PostResponse {

    private Long id;
    @NotNull
    @Size(min = 1)
    private String content;

    private String type;

    @JsonProperty("parent_id")
    private Long parentId;

    private List<String> images;
    private List<String> thumbs;

    @JsonProperty(value = "posted_by_id")
    private Long postedById;

    @JsonProperty("posted_by_name")
    private String postedByName;

    @JsonProperty("posted_by_photo")
    private String postedByPhoto;

    @JsonProperty("self_reaction_type")
    private Reaction.Type selfReactionType;

    @JsonProperty("action_link")
    private String actionLink;

    @JsonProperty("reaction_stats")
    private ReactionStats reactionStats;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("last_updated")
    private Date lastUpdated;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getThumbs() {
        return thumbs;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }

    public Reaction.Type getSelfReactionType() {
        return selfReactionType;
    }

    public void setSelfReactionType(Reaction.Type selfReactionType) {
        this.selfReactionType = selfReactionType;
    }

    public String getPostedByPhoto() {
        return postedByPhoto;
    }

    public void setPostedByPhoto(String postedByPhoto) {
        this.postedByPhoto = postedByPhoto;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getActionLink() {
        return actionLink;
    }

    public void setActionLink(String actionLink) {
        this.actionLink = actionLink;
    }

    public String getPostedByName() {
        return postedByName;
    }

    public void setPostedByName(String postedByName) {
        this.postedByName = postedByName;
    }

    public ReactionStats getReactionStats() {
        return reactionStats;
    }

    public void setReactionStats(ReactionStats reactionStats) {
        this.reactionStats = reactionStats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostedById() {
        return postedById;
    }

    public void setPostedById(Long postedById) {
        this.postedById = postedById;
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
