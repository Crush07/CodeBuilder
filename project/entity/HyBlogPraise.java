package com.hysea.hyseaappapi.entity.po;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
import lombok.*;

/**
 * @author hysea
 */
@Data
public class HyBlogPraise implements Serializable {


	/**
	 * 
	 */
	private String praiseUserId;


	/**
	 * 
	 */
	private Integer createTime;


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