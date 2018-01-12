package com.mfu.entity.survey;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.util.DateTimeDeserializer;
import com.mfu.util.DateTimeSerializer;

@Entity 
@JsonIgnoreProperties(ignoreUnknown = true)
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Key key;
	
	private String title;
	private String description;
	private String target;
	private String photo;
	private String level;
	private String caption;
	
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date date;
	
	
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}

	public void setKeyString(String keyString) {
		key = KeyFactory.stringToKey(keyString);
	}
}
