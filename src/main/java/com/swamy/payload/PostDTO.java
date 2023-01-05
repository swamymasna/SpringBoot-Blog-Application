package com.swamy.payload;


import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

	private Integer id;

	
	//title should not be null or empty
	//title should have at least 2 charectors
	
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 charecters")
	private String title;

	@NotEmpty
	@Size(min = 2, message = "Post description should have at least 2 charecters")
	private String description;

	@NotEmpty
	@Size(min = 2, message = "Post content should have at least 2 charecters")
	private String content;
	
	private Set<CommentDTO>comments; 
}
