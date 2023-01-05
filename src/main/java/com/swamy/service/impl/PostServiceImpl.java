package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swamy.entity.Post;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.payload.PostDTO;
import com.swamy.payload.PostResponse;
import com.swamy.repository.PostRepository;
import com.swamy.service.IPostService;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public PostDTO savePost(PostDTO postDTO) {

		// Convert DTO to Entity
		Post post = mapToEntity(postDTO);
		Post savedPost = postRepository.save(post);

		// Convert Entity to DTO
		PostDTO postResponse = mapToDTO(savedPost);
		return postResponse;

	}

	@Override
	public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy) {

//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();

		// create Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Post> posts = postRepository.findAll(pageable);

		// get content for page object
		List<Post> listOfPosts = posts.getContent();

		List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());

		return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer id) {

		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post Record Not Found with ID : " + id));
		return mapToDTO(post);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer id) {

		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post Record Not Found with ID : " + id));

		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());

		Post updatedPost = postRepository.save(post);
		return mapToDTO(updatedPost);
	}

	@Override
	public void deletePost(Integer id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post Record Not Found with ID : " + id));
		postRepository.delete(post);
	}

	public PostDTO mapToDTO(Post post) {

		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getId());
		postDTO.setTitle(post.getTitle());
		postDTO.setDescription(post.getDescription());
		postDTO.setContent(post.getContent());

		return postDTO;
	}

	public Post mapToEntity(PostDTO postDTO) {
		Post post = new Post();
		post.setId(postDTO.getId());
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		return post;
	}

}
