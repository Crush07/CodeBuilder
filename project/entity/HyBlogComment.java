package com.hysea.hyseaappapi.entity.po;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
import lombok.*;

/**
 * @author hysea
 */
@Data
public class HyBlogComment implements Serializable {


	/**
	 * 
	 */
	private Integer createTime;


	/**
	 * 
	 */
	private String commentId;


	/**
	 * 
	 */
	private Integer updateTime;


	/**
	 * 
	 */
	private String id;


	/**
	 * 
	 */
	private String blogId;


}