package com.swamy.service;

import com.swamy.payload.PostDTO;
import com.swamy.payload.PostResponse;

public interface IPostService {

	public PostDTO savePost(PostDTO postDTO);

	public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy);

	public PostDTO getPostById(Integer id);

	public PostDTO updatePost(PostDTO postDTO, Integer id);

	public void deletePost(Integer id);
}
