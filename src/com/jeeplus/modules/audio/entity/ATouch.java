package com.jeeplus.modules.audio.entity;


import com.jeeplus.common.persistence.DataEntity;

/**
 * 触屏操作Entity
 * @author stephen
 * @version 2020-5-12
 */
public class ATouch extends DataEntity<ATouch> {
	
	private static final long serialVersionUID = 1L;
	private Integer touchId;		// 主键ID
	private Integer action;		// 动作
	private String name;	    //名称
	private String audio;	    //音频
	
	public ATouch() {
		super();
	}

	public ATouch(String id){
		super(id);
	}

	public Integer getTouchId() {
		return touchId;
	}

	public void setTouchId(Integer touchId) {
		this.touchId = touchId;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

}