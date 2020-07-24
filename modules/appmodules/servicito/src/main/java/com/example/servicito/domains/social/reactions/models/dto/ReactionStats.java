package com.example.servicito.domains.social.reactions.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReactionStats {
    @JsonProperty("total")
    private Long total;
    @JsonProperty("likes")
    private Long likes;
    @JsonProperty("love")
    private Long love;
    @JsonProperty("haha")
    private Long haha;
    @JsonProperty("wow")
    private Long wow;
    @JsonProperty("sad")
    private Long sad;
    @JsonProperty("angry")
    private Long angry;

    private Long comments;

    public ReactionStats(Long likes, Long love, Long haha, Long wow, Long sad, Long angry, Long comments) {
        this.likes = likes == null ? 0 : likes;
        this.love = love == null ? 0 : love;
        this.haha = haha == null ? 0 : haha;
        this.wow = wow == null ? 0 : wow;
        this.sad = sad == null ? 0 : sad;
        this.angry = angry == null ? 0 : angry;
        this.total = this.likes + this.love + this.haha + this.wow + this.sad + this.angry;
        this.comments = comments;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getLove() {
        return love;
    }

    public void setLove(Long love) {
        this.love = love;
    }

    public Long getHaha() {
        return haha;
    }

    public void setHaha(Long haha) {
        this.haha = haha;
    }

    public Long getWow() {
        return wow;
    }

    public void setWow(Long wow) {
        this.wow = wow;
    }

    public Long getSad() {
        return sad;
    }

    public void setSad(Long sad) {
        this.sad = sad;
    }

    public Long getAngry() {
        return angry;
    }

    public void setAngry(Long angry) {
        this.angry = angry;
    }
}
