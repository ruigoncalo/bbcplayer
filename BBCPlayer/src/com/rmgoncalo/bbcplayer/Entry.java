/*
 * Class used to define an entry object
 * Instance variables are related to the html elements
 */

package com.rmgoncalo.bbcplayer;

import java.util.List;

public class Entry {
	
	private String title;
	private String id;
	private String updated;
	private String content;
	private List<String> categories;
	private List<String> links;
	private String thumb;
	private int rtscore;
	
	/*
	 * Constructors
	 */
	public Entry(){
		this.title = null;
		this.id = null;
		this.updated = null;
		this.content = null;
	}
	
	public Entry(String title, String id, String updated, String content) {
		this.title = title;
		this.id = id;
		this.updated = updated;
		this.content = content;
	}
	public Entry(String title, String id, String updated, String content,
			List<String> categories, List<String> links, String thumb,
			int rtscore) {
		this.title = title;
		this.id = id;
		this.updated = updated;
		this.content = content;
		this.categories = categories;
		this.links = links;
		this.thumb = thumb;
		this.rtscore = rtscore;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public int getRtscore() {
		return rtscore;
	}
	public void setRtscore(int rtscore) {
		this.rtscore = rtscore;
	}

}
