package ru.mirea.web3.web3javaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.mirea.web3.web3javaserver.entity.Post;
import ru.mirea.web3.web3javaserver.entity.User;
import ru.mirea.web3.web3javaserver.entity.dto.PostDTO;
import ru.mirea.web3.web3javaserver.repository.PostRepository;
import ru.mirea.web3.web3javaserver.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    private PostRepository postRepository;

    private UserRepository userRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/post")
    @Transactional
    public void postPost(@RequestBody PostDTO postDTO) {
        postRepository.save(new Post(null, userRepository.findById(postDTO.getUserToken()).orElse(null), postDTO.getPicture(), postDTO.getDate(), postDTO.getDescription()));
    }

    @GetMapping("/postDelete/{id}")
    @Transactional
    public void deletePost(@PathVariable("id") Integer id) {
        postRepository.deletePostById(id);
    }

    @GetMapping(value = "/post")
    @Transactional
    public List<PostDTO> getPosts(@RequestParam("token") String token) {
        User user = userRepository.findById(token).orElse(null);
        if (user != null) {
            List<PostDTO> postDTOList = new ArrayList<>();
            for (Post post : user.getPostList()) {
                postDTOList.add(new PostDTO(post.getId(), post.getPicture(), post.getDate(), post.getDescription(), post.getUser().getToken()));
            }
            return postDTOList;
        }
        return null;
    }

    @GetMapping(value = "/postDepth")
    @Transactional
    public List<PostDTO> getPosts(@RequestParam("token") String token, @RequestParam("depth") Integer depth) {
        Page<Post> postPage = postRepository.findByUser_TokenOrderByDateDesc(token, PageRequest.of(depth, 5));
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postPage) {
            postDTOList.add(new PostDTO(post.getId(), post.getPicture(), post.getDate(), post.getDescription(), post.getUser().getToken()));
        }
        return postDTOList;
    }

    @GetMapping("/posts")
    @Transactional
    public List<PostDTO> getLatestPosts(@RequestParam("depth") Integer depth) {
        Page<Post> postPage = postRepository.findByOrderByDateDesc(PageRequest.of(depth, 5));
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postPage) {
            postDTOList.add(new PostDTO(post.getId(), post.getPicture(), post.getDate(), post.getDescription(), post.getUser().getToken()));
        }
        return postDTOList;
    }

//    @GetMapping("/subPosts")
//    @Transactional
//    public List<PostDTO> getSubPosts(@RequestParam("token") String token, @RequestParam("depth") Integer depth) {
//        User user = userRepository.findById(token).orElse(null);
//        if (user != null) {
//            List<PostDTO> postDTOList = new ArrayList<>();
//            for (String sub : user.getSubs()) {
//                Page<Post> postPage = postRepository.findByUser_TokenOrderByDateDesc(sub, PageRequest.of(depth, 1));
//                for (Post post : postPage) {
//                    postDTOList.add(new PostDTO(post.getId(), post.getPicture(), post.getDate(), post.getDescription(), post.getUser().getToken()));
//                }
//            }
//            return postDTOList;
//        }
//        return null;
//    }

    @GetMapping("/countPosts")
    public Long countPosts(@RequestParam("token") Optional<String> token) {
        if (token.isPresent()) {
            User user = userRepository.findById(token.orElse(null)).orElse(null);
            if (user != null) {
                Long sum = 0L;
                for (String sub : user.getSubs()) {
                    sum += postRepository.countByUser_Token(sub);
                }
                return sum;
            }
        }
        return postRepository.count();
    }

    @GetMapping("/countSubs")
    public Long countPosts(@RequestParam("token") String token) {
        return postRepository.countByUser_Token(token);
    }

    @GetMapping("/post/{id}")
    public PostDTO getPost(@PathVariable("id") Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            return new PostDTO(post.getId(), post.getPicture(), post.getDate(), post.getDescription(), post.getUser().getToken());
        }
        return null;
    }
}
