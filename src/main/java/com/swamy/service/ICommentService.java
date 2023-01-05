package com.swamy.service;

import java.util.List;

import com.swamy.payload.CommentDTO;

public interface ICommentService {

	public CommentDTO saveComment(Integer postId, CommentDTO commentDTO);
	
	public List<CommentDTO> getAllCommentsByPostId(Integer postId);
	
	public CommentDTO getCommentByPostId(Integer postId, Integer commentId);
	
	public CommentDTO updateComment(Integer postId, Integer commentId, CommentDTO commentDTO);
	
	public void deleteComment(Integer postId, Integer commentId);
}

