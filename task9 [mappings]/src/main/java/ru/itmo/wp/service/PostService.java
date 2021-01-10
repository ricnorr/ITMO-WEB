package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.UserRepository;


import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post find(long id) {
        return postRepository.findById(id);
    }

    public void writeComment(User user, CommentForm commentForm, long postId) {
        Comment comment = new Comment();
        comment.setText(commentForm.getText());
        Post post = postRepository.findById(postId);
        comment.setPost(post);
        comment.setUser(user);
        post.addComment(comment);
        postRepository.save(post);
    }
}
