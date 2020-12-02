package com.jeeplus.modules.audio.entity;


import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 问卷调查Entity
 * @author stephen
 * @version 2020-11-28
 */
public class AQuestionnaire extends DataEntity<AQuestionnaire> {
	
	private static final long serialVersionUID = 1L;
	private Integer questionnaireId;		// 主键ID
	private String room;	    		// 房号
	private String name;	    		// 姓名
	private String inspector;	    	// 验房者编号
	private String question1;	    	// 总体满意度
	private String question2;	    	// 房屋质量满意度
	private String question3;	    	// 房屋设计整体满意度
	private String question4;	    	// 公共区域装饰及施工质量的满意度
	private String question5;	    	// 园区绿化及设计的满意度
	private String question6;	    	// 收楼的整体安排和服务的满意度
	private String remark;	    		// 备注
	private Date createTime;	    		// 创建时间
	
	
	public AQuestionnaire() {
		super();
	}

	public AQuestionnaire(String id){
		super(id);
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}
	
	@Length(min=0, max=50, message="房屋编号必须介于 0 和 50 之间")
	@ExcelField(title="房屋编号", align=2, sort=1)
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Length(min=0, max=50, message="姓名必须介于 0 和 50 之间")
	@ExcelField(title="姓名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=50, message="验房者编号必须介于 0 和 50 之间")
	@ExcelField(title="验房者编号", align=2, sort=3)
	public String getInspector() {
		return inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	@Length(min=0, max=50, message="总体满意度必须介于 0 和 50 之间")
	@ExcelField(title="总体满意度", align=2, sort=4)
	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	@Length(min=0, max=50, message="房屋质量满意度必须介于 0 和 50 之间")
	@ExcelField(title="房屋质量满意度", align=2, sort=5)
	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	@Length(min=0, max=50, message="房屋设计整体满意度必须介于 0 和 50 之间")
	@ExcelField(title="房屋设计整体满意度", align=2, sort=6)
	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	@Length(min=0, max=50, message="公共区域装饰及施工质量的满意度必须介于 0 和 50 之间")
	@ExcelField(title="公共区域装饰及施工质量的满意度", align=2, sort=7)
	public String getQuestion4() {
		return question4;
	}

	public void setQuestion4(String question4) {
		this.question4 = question4;
	}

	@Length(min=0, max=50, message="园区绿化及设计的满意度必须介于 0 和 50 之间")
	@ExcelField(title="园区绿化及设计的满意度", align=2, sort=8)
	public String getQuestion5() {
		return question5;
	}

	public void setQuestion5(String question5) {
		this.question5 = question5;
	}

	@Length(min=0, max=50, message="收楼的整体安排和服务的满意度必须介于 0 和 50 之间")
	@ExcelField(title="收楼的整体安排和服务的满意度", align=2, sort=9)
	public String getQuestion6() {
		return question6;
	}

	public void setQuestion6(String question6) {
		this.question6 = question6;
	}

	@Length(min=0, max=50, message="意见或建议必须介于 0 和 50 之间")
	@ExcelField(title="意见或建议", align=2, sort=10)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}