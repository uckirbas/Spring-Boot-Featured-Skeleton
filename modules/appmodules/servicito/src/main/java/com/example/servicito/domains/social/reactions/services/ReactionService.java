package com.example.servicito.domains.social.reactions.services;

import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.servicito.domains.social.reactions.models.dto.ReactionStats;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;
import com.example.auth.entities.User;
import com.example.common.exceptions.invalid.InvalidException;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ReactionService {
    Reaction save(Reaction reaction) throws InvalidException;

    Reaction find(Long id);

    List<Reaction> getPostReactions(Long postId);

    ReactionStats getStatsForPost(Post post);

    Reaction getUserReactionInPost(@NotNull User user, @NotNull Post post);

    void deletePostReactions(Post post);
}
