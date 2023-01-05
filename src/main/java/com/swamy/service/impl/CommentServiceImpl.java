package com.swamy.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swamy.entity.Comment;
import com.swamy.entity.Post;
import com.swamy.exception.BlogApiException;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.payload.CommentDTO;
import com.swamy.repository.CommentRepository;
import com.swamy.repository.PostRepository;
import com.swamy.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDTO saveComment(Integer postId, CommentDTO commentDTO) {

		// Convert DTO to Entity
		Comment comment = mapToEntity(commentDTO);

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found with ID : " + postId));

		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment);

		// Convert Entity to DTO
		CommentDTO commentResponse = mapToDTO(savedComment);

		return commentResponse;
	}

	@Override
	public List<CommentDTO> getAllCommentsByPostId(Integer postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found with ID : " + postId));
		List<Comment> comments = commentRepository.findAll();
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

	}

	@Override
	public CommentDTO getCommentByPostId(Integer postId, Integer commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found with ID : " + postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment Not Found with ID : " + commentId));
		
		post.setComments(Collections.singleton(comment));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException("Comment Does Not Belongs to Post");
		}

		return mapToDTO(comment);
	}

	@Override
	public CommentDTO updateComment(Integer postId, Integer commentId, CommentDTO commentDTO) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found with ID : " + postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment Not Found with ID : " + commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException("Comment Does Not Belongs to Post");
		}
		comment.setName(commentDTO.getName());
		comment.setEmail(commentDTO.getEmail());
		comment.setBody(commentDTO.getBody());
		comment.setPost(post);
		Comment updatedComment = commentRepository.save(comment);
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Integer postId, Integer commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found With ID : " + postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment Not Found With ID : " + commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException("Comment Does Not Belongs to Post");
		}

		commentRepository.delete(comment);
	}

	// ------Converting DTO to Entity & Vice versa------
	private Comment mapToEntity(CommentDTO commentDTO) {
		return mapper.map(commentDTO, Comment.class);
	}

	private CommentDTO mapToDTO(Comment comment) {
		return mapper.map(comment, CommentDTO.class);
	}
}
