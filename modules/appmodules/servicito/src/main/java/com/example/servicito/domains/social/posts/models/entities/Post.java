package com.example.servicito.domains.social.posts.models.entities;

import com.example.auth.config.security.SecurityContext;
import com.example.coreweb.domains.base.entities.BaseEntity;
import com.example.auth.entities.UserAuth;
import com.example.auth.entities.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "social_posts")
public class Post extends BaseEntity {
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "social_posts_images")
    private List<String> images;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "social_posts_thumbs")
    private List<String> thumbs;

    @Column(name = "action_link")
    private String actionLink;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Post parent;

    @ManyToOne
    @JoinColumn(name = "posted_by_id", nullable = false)
    private User postedBy;

    private boolean fixed;

    public boolean isAuthoizedToModify() {
        UserAuth loggedInUser = SecurityContext.getCurrentUser();
        if (loggedInUser == null || postedBy == null) return false;
        if (loggedInUser.isAdmin()) return true;
        return loggedInUser.getId().equals(postedBy.getId());
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public enum Type {
        STATUS, AUTO_GENERATED, HOME_RENT
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }

    public String getActionLink() {
        return actionLink;
    }

    public void setActionLink(String actionLink) {
        this.actionLink = actionLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Post getParent() {
        return parent;
    }

    public void setParent(Post parent) {
        this.parent = parent;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }
}
