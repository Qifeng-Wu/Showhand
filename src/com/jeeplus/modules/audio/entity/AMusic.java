package com.jeeplus.modules.audio.entity;


import com.jeeplus.common.persistence.DataEntity;

/**
 * 音乐Entity
 * @author stephen
 * @version 2020-5-12
 */
public class AMusic extends DataEntity<AMusic> {
	
	private static final long serialVersionUID = 1L;
	private Integer musicId;		// 主键ID
	private String title;	    	// 标题
	private String singer;	     //演唱者
	private String composer;	 //作曲者
	private String lyricist;	 //作词者
	private String lyric;	    //歌词
	private String picture;	    //图片
	private String link;	    //音乐路径
	
	public AMusic() {
		super();
	}

	public AMusic(String id){
		super(id);
	}

	public Integer getMusicId() {
		return musicId;
	}

	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public String getLyricist() {
		return lyricist;
	}

	public void setLyricist(String lyricist) {
		this.lyricist = lyricist;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}