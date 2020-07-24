package com.example.servicito.domains.social.posts.controllers;

import com.example.servicito.domains.apartments.repositories.ApartmentRepository;
import com.example.coreweb.domains.bases.images.repositories.FileUploadRepository;
import com.example.servicito.domains.social.posts.models.dto.PostRequest;
import com.example.servicito.domains.social.posts.models.dto.mappers.PostMapper;
import com.example.servicito.domains.social.posts.models.entities.Post;
import com.example.servicito.domains.social.posts.repositories.PostRepository;
import com.example.servicito.domains.social.posts.services.PostService;
import com.example.servicito.domains.social.reactions.models.dto.ReactionRequest;
import com.example.servicito.domains.social.reactions.models.dto.ReactionStats;
import com.example.servicito.domains.social.reactions.models.entities.Reaction;
import com.example.servicito.domains.social.reactions.models.mappers.ReactionMapper;
import com.example.servicito.domains.social.reactions.services.ReactionService;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/social/posts")
public class PostController {
    private final PostService postService;
    private final ReactionService reactionService;
    private final PostMapper postMapper;
    private final ReactionMapper reactionMapper;

    private final PostRepository postRepo;
    private final FileUploadRepository fileUploadRep;
    private final ApartmentRepository apartmentRepo;

    @Autowired
    public PostController(PostService postService, ReactionService reactionService, PostMapper postMapper, ReactionMapper reactionMapper, PostRepository postRepo, FileUploadRepository fileUploadRep, ApartmentRepository apartmentRepo) {
        this.postService = postService;
        this.reactionService = reactionService;
        this.postMapper = postMapper;
        this.reactionMapper = reactionMapper;
        this.postRepo = postRepo;
        this.fileUploadRep = fileUploadRep;
        this.apartmentRepo = apartmentRepo;
    }

    @GetMapping("")
    private ResponseEntity getAllPosts(@RequestParam(value = "type", required = false) Post.Type type,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "20") Integer size) throws NotFoundException {
        Page<Post> posts;
        if (type == null)
            posts = this.postService.getNewsFeedPosts(page, size);
        else
            posts = this.postService.findPostsByType(type, page, size);
        return ResponseEntity.ok(posts.map(postMapper::map));
    }

    @PostMapping("")
    private ResponseEntity savePost(@Valid @RequestBody PostRequest postRequest) throws NotFoundException, ForbiddenException {
        Post post = null;
        if (postRequest.getId() != null)
            post = this.postService.find(postRequest.getId());
        post = this.postService.save(this.postMapper.map(postRequest, post));
        return ResponseEntity.ok(this.postMapper.map(post));
    }


    @GetMapping("/{id}")
    private ResponseEntity getPost(@PathVariable("id") Long postId) throws NotFoundException {
        Post post = this.postService.find(postId);
        return ResponseEntity.ok(this.postMapper.map(post));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deletePost(@PathVariable("id") Long postId) throws NotFoundException, ForbiddenException {
        this.postService.delete(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/comments")
    private ResponseEntity getComments(@PathVariable("id") Long postId,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "20") Integer size) throws NotFoundException, InvalidException {
        Post post = this.postService.find(postId);
        Page<Post> comments = this.postService.findComments(post, page, size);
        return ResponseEntity.ok(comments.map(this.postMapper::map));
    }

    /* REACTIONS */
    @GetMapping("/{id}/reactions/stats")
    private ResponseEntity getReactionsStats(@PathVariable("id") Long postId) throws NotFoundException {
        Post post = this.postService.find(postId);
        ReactionStats reactionStats = this.reactionService.getStatsForPost(post);
        return ResponseEntity.ok(reactionStats);
    }

    @GetMapping("/{id}/reactions")
    private ResponseEntity getReactions(@PathVariable("id") Long postId) throws NotFoundException {
        List<Reaction> reactions = this.reactionService.getPostReactions(postId);
        return ResponseEntity.ok(reactions.stream().map(this.reactionMapper::map));
    }

    @PostMapping("/{id}/reactions")
    private ResponseEntity addReaction(@PathVariable("id") Long postId,
                                       @Valid @RequestBody ReactionRequest reactionRequest) throws NotFoundException, InvalidException {
        Post post = this.postService.find(postId);
        Reaction reaction = this.reactionMapper.map(reactionRequest);
        reaction.setPost(post);
        reaction = this.reactionService.save(reaction);
        return ResponseEntity.ok(this.reactionMapper.map(reaction));
    }

//    @PostMapping("/fix_posts")
//    private ResponseEntity fixImages() {
//        Post post = this.postRepo.findFirstByFixedFalse();
//        if (post == null || post.getImages() == null)
//            return ResponseEntity.ok("Total " + postRepo.count() + " completed");
//
//        List<String> newImages = new ArrayList<>();
//        for (String image : post.getImages()) {
//            if (image.startsWith("/images/")) {
//                String uuid = image.replace("/images/", "");
//                UploadProperties properties = this.fileUploadRep.findByUuid(uuid);
//                newImages.add(properties.getStaticImagePath());
//            } else {
//                if (image.startsWith("/apartments")) {
//                    String[] ss = image.split("/");
//                    String zzz = ss[2];
//                    Apartment apartment = this.apartmentRepo.findById(Long.parseLong(zzz)).orElse(null);
//                    newImages = apartment.getImagePaths().stream().map(a -> a.replace("/var/appFiles/AppData", "")).collect(Collectors.toList());
//                }
//            }
//        }
//
//        List<String> newThumbs = new ArrayList<>();
//        for (String image : post.getImages()) {
//            if (image.startsWith("/images/")) {
//                String uuid = image.replace("/images/", "");
//                UploadProperties properties = this.fileUploadRep.findByUuid(uuid);
//                newImages.add(properties.getStaticThumbPath());
//            } else {
//                if (image.startsWith("/apartments")) {
//                    String[] ss = image.split("/");
//                    Apartment apartment = this.apartmentRepo.findById(Long.parseLong(ss[2])).orElse(null);
//                    newThumbs = apartment.getImagePaths().stream().map(a -> a.replace("/var/appFiles/AppData", "")).collect(Collectors.toList());
//                }
//            }
//        }
//
//        post.setThumbs(newThumbs);
//        post.setImages(newImages);
//        post.setFixed(true);
//        post = this.postRepo.save(post);
//        System.out.println(post.getId());
//        fixImages();
//        return ResponseEntity.ok("Total: " + postRepo.count() + " completed!");
//    }


}
