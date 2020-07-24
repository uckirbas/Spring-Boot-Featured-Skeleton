package com.example.servicito.domains.social.reactions.services;

import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.servicito.domains.social.posts.repositories.PostRepository;
import com.example.servicito.domains.social.reactions.models.dto.ReactionStats;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;
import com.example.servicito.domains.social.reactions.repositories.ReactionRepository;
import com.example.auth.entities.User;
import com.example.common.exceptions.invalid.InvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepo;
    private final PostRepository postRepo;

    @Autowired
    public ReactionServiceImpl(ReactionRepository reactionRepo, PostRepository postRepo) {
        this.reactionRepo = reactionRepo;
        this.postRepo = postRepo;
    }

    @Override
    public Reaction save(@NotNull Reaction reaction) throws InvalidException {
        Reaction ex = this.reactionRepo.findFirstByReactedByAndPost(reaction.getReactedBy(), reaction.getPost());
        if (ex != null) {
            ex.setType(reaction.getType());
            return this.reactionRepo.save(ex);
        }
        return this.reactionRepo.save(reaction);
    }

    @Override
    public Reaction find(@NotNull Long id) {
        return this.reactionRepo.findById(id).orElse(null);
    }

    @Override
    public List<Reaction> getPostReactions(@NotNull Long postId) {
        return this.reactionRepo.findByPostId(postId);
    }

    @Override
    public ReactionStats getStatsForPost(Post post) {
        Long likes = this.reactionRepo.getPostReactionCount(post, Reaction.Type.LIKE);
        Long love = this.reactionRepo.getPostReactionCount(post, Reaction.Type.LOVE);
        Long haha = this.reactionRepo.getPostReactionCount(post, Reaction.Type.HAHA);
        Long wow = this.reactionRepo.getPostReactionCount(post, Reaction.Type.WOW);
        Long sad = this.reactionRepo.getPostReactionCount(post, Reaction.Type.SAD);
        Long angry = this.reactionRepo.getPostReactionCount(post, Reaction.Type.ANGRY);

        Long comments = this.postRepo.getNoOfComments(post);
        return new ReactionStats(likes, love, haha, wow, sad, angry, comments);
    }

    @Override
    public Reaction getUserReactionInPost(@NotNull User user, @NotNull Post post) {
        return this.reactionRepo.getUserReactionInPost(user, post);
    }

    @Override
    public void deletePostReactions(Post post) {
        this.deletePostReactions(post);
    }


}
