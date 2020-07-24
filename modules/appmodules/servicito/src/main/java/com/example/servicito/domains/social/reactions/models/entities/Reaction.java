package com.example.servicito.domains.social.reactions.models.entities;

import com.example.coreweb.domains.base.entities.BaseEntity;
import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.auth.entities.User;

import javax.persistence.*;

@Entity
@Table(name = "social_reactions")
public class Reaction extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User reactedBy;


    public enum Type {
        LIKE("like"),
        LOVE("love"),
        HAHA("haha"),
        WOW("wow"),
        SAD("sad"),
        ANGRY("angry");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getReactedBy() {
        return reactedBy;
    }

    public void setReactedBy(User reactedBy) {
        this.reactedBy = reactedBy;
    }
}
