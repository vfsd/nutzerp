package com.vfsd.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
/**
 * 管理员
 * @author wangyongfu
 *
 */
@Table("t_admin")
public class Admin {
	@Id
	private int id;//
	@Name
    @Column("name")
	private String name;//用户名
	@Column("password")
	private String password;//登录密码
	
	public Admin(){}
	
	public Admin(String name,String password){
		this.name=name;
		this.password=password;
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
	
}
