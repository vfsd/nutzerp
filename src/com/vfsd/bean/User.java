package com.vfsd.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
/**
 * 用户
 * @author wangyongfu
 *
 */
@Table("t_user")
public class User {
	@Id
	private int id;//
	@Name
    @Column("name")
	private String name;//用户名
	@Column("password")
	private String password;//登录密码
	@Column("state")
	private int state;//状态
	
	public User(){}
	
	public User(String name,String password,int state){
		this.name=name;
		this.password=password;
		this.state=state;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
}
