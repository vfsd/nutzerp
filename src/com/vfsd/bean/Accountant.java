package com.vfsd.bean;

import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import com.vfsd.util.TimeGroup;
/**
 * 财务管理人员
 * @author wangyongfu
 *
 */
@Table("t_accountant")
public class Accountant {
	
	@Id
	private int id;//
	
	@Name
    @Column("name")
	private String name;//用户名
	
	@Column("password")
	private String password;//登录密码
	
	@Column("workShift")
	private Date workShift;//上班时间
	
	@Column("closeShift")
	private Date closeShift;//下班时间
	
	private List<TimeGroup> appointment;//预约时间组
	
	@Column("timeGroup")
	private String timeGroup;//时间组
	
	@Column("state")
	private int state;//目前是否被预约
	
	@Column("windowNum")
	private int windowNum;//窗口号
	
	public Accountant(){}
	
	public Accountant(String name, String password, Date workShift, Date closeShift, String timeGroup, int state,int windowNum) {
		super();
		this.name = name;
		this.password = password;
		this.workShift = workShift;
		this.closeShift = closeShift;
		this.timeGroup= timeGroup;
		this.state = state;
		this.windowNum=windowNum;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getWorkShift() {
		return workShift;
	}
	public void setWorkShift(Date workShift) {
		this.workShift = workShift;
	}
	public Date getCloseShift() {
		return closeShift;
	}
	public void setCloseShift(Date closeShift) {
		this.closeShift = closeShift;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<TimeGroup> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<TimeGroup> appointment) {
		this.appointment = appointment;
	}

	public int getWindowNum() {
		return windowNum;
	}

	public void setWindowNum(int windowNum) {
		this.windowNum = windowNum;
	}

	public String getTimeGroup() {
		return timeGroup;
	}

	public void setTimeGroup(String timeGroup) {
		this.timeGroup = timeGroup;
	}
	
	
	

}
