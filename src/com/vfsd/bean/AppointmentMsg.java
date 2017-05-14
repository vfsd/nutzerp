package com.vfsd.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
/**
 * 预约信息
 * @author wangyongfu 
 *
 */
@Table("t_appointmentMsg")
public class AppointmentMsg {
	@Id
	private int id;//
	
    @Column("content")
	private String content;//预约内容
	
	@Column("startTime")
	private Date startTime;//开始时间
	
	@Column("endTime")
	private Date endTime;//结束时间
	
	@Column("state")
	private int state;//是否接收预约(0:未读   1:已接受  2:驳回)
	
	@Column("userId")
	private int userId;//预约人ID
	
	@Column("accountantId")
	private int accountantId;//被预约人ID
	
	@Column("timeGroup")
	private String timeGroup;//时间组
	
	
	public AppointmentMsg(){}

	public AppointmentMsg(String content, Date startTime, Date endTime, int state, int userId,int accountantId,String timeGroup) {
		super();
		this.content = content;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
		this.userId = userId;
		this.accountantId = accountantId;
		this.timeGroup = timeGroup;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccountantId() {
		return accountantId;
	}

	public void setAccountantId(int accountantId) {
		this.accountantId = accountantId;
	}

	public String getTimeGroup() {
		return timeGroup;
	}

	public void setTimeGroup(String timeGroup) {
		this.timeGroup = timeGroup;
	}
	
	
}
