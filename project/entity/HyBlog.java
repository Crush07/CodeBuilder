package com.hysea.hyseaappapi.entity.po;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
import lombok.*;

/**
 * @author hysea
 */
@Data
public class HyBlog implements Serializable {


	/**
	 * 
	 */
	private Integer createTime;


	/**
	 * 
	 */
	private String blogContent;


	/**
	 * 
	 */
	private Integer updateTime;


	/**
	 * 
	 */
	private Integer blogViewCount;


	/**
	 * 
	 */
	private String blogId;


	/**
	 * 
	 */
	private String blogTitle;


}