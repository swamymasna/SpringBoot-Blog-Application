package com.swamy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.payload.CommentDTO;
import com.swamy.payload.PostDTO;
import com.swamy.service.ICommentService;

@RestController
@RequestMapping("/api")
public class CommentRestController {

	@Autowired
	private ICommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> saveComment(@PathVariable Integer postId, @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<CommentDTO>(commentService.saveComment(postId, commentDTO), HttpStatus.CREATED);
	}

	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable Integer postId) {
		return new ResponseEntity<List<CommentDTO>>(commentService.getAllCommentsByPostId(postId), HttpStatus.OK);
	}

	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> getCommentByPostId(@PathVariable Integer postId,
			@PathVariable Integer commentId) {
		return new ResponseEntity<CommentDTO>(commentService.getCommentByPostId(postId, commentId), HttpStatus.OK);
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable Integer postId, @PathVariable Integer commentId,
			@RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<CommentDTO>(commentService.updateComment(postId, commentId, commentDTO),
				HttpStatus.OK);
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<String>("Comment Deleted Successfully with Id : " + commentId, HttpStatus.OK);
	}
}
