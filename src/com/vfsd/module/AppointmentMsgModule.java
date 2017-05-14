package com.vfsd.module;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import com.vfsd.bean.Accountant;
import com.vfsd.bean.AppointmentMsg;
import com.vfsd.bean.User;

/**
 * 预约信息控制器
 * @author new
 *
 */
@IocBean // 声明为Ioc容器中的一个Bean
@At("/msg") // 整个模块的路径前缀
@Ok("json:{ignoreNull:true}") // 忽略password和salt属性,忽略空属性的json输出
@Fail("http:500") // 抛出异常的话,就走500页面
public class AppointmentMsgModule {
	
	@Inject
    protected Dao dao; // 就这么注入了,有@IocBean它才会生效

	
    /**
     * 退出登录
     * @param session
     */
    @At
    @Ok(">>:/")// 跟其他方法不同,这个方法完成后就跳转首页了
    public void logout(HttpSession session) {
        //session.invalidate();
    	session.removeAttribute("loginUser");
    }
    
    @At
    @Ok("jsp:jsp.user.main") // 真实路径是 /WEB-INF/jsp/user/list.jsp
    @Filters(@By(type=CheckSession.class, args={"loginUser", "/userLogin.jsp"}))
    public void toMain() {
    }
    
    @At
    @Ok("jsp:jsp.accountant.list1") // 真实路径是 /WEB-INF/jsp/accountant/list1.jsp
    @Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"}))
    public void goMsg(){
    	
    }
    
    @At
    @Filters(@By(type=CheckSession.class, args={"loginUser", "/userLogin.jsp"})) // 检查当前Session是否带me这个属性
    public Object receivePost(@Param("accountantId")int accountantId,@Param("thisTimeGroup")String thisTimeGroup,@Param("content")String content,HttpSession session) {
        NutMap re = new NutMap();
        
        AppointmentMsg msg = new AppointmentMsg();
        msg.setTimeGroup(thisTimeGroup);//时间组
        msg.setAccountantId(accountantId);//被预约财务人员ID
        msg.setContent(content);//预约内容
        //System.out.println("-=-=-=-=-=-=-=-=-=-="+content);
        //int state;//是否接收预约(0:未读   1:已接受  2:驳回)
        msg.setState(0);//该条预约信息状态，默认为未读
        User user = (User) session.getAttribute("loginUser");
        msg.setUserId(user.getId());//预约人的ID
        
        dao.insert(msg);
        
        //AccountantModule am = new AccountantModule();
        //am.updateTimeGroup(thisTimeGroup,accountantId);//更新财务管理人员时间表
        updateTimeGroup(thisTimeGroup,accountantId);//更新财务管理人员时间表
        
        return re.setv("ok", true);
    }
    
    /**
     * 修改财务管理人员信息
     * @param user
     * @return
     */
    //@At
    //@Filters(@By(type=CheckSession.class, args={"loginAccountant", "/"})) // 检查当前Session是否带loginAccountant这个属性
    public Object updateTimeGroup(String thisTimeGroup,int accountantId) {
        NutMap re = new NutMap();
        int iid =accountantId;
        Accountant user = (Accountant) queryObjById(iid);
        if(user!=null){
        	user.setTimeGroup(user.getTimeGroup()+thisTimeGroup);
        }
        dao.updateIgnoreNull(user);//
        return re.setv("ok", true);
    }
    
    /**
     * 当用户修改预约时间后，被预约的财务人员实践组数据也需要修改
     * @param thisTimeGroup
     * @param accountantId
     * @param oldTimeGroup
     * @return
     */
    public Object updateTimeGroup1(String thisTimeGroup,int accountantId,String oldTimeGroup) {
        NutMap re = new NutMap();
        int iid =accountantId;
        Accountant user = (Accountant) queryObjById(iid);
        if(user!=null){
        	user.setTimeGroup(user.getTimeGroup().replaceAll(oldTimeGroup, thisTimeGroup));
        }
        dao.updateIgnoreNull(user);//
        return re.setv("ok", true);
    }
    
    
    /**
     * 根据IID查询财务管理人员信息
     * @param iid
     * @return
     */
    public Object queryObjById(int iid){
    	System.out.println(iid);
    	Accountant user = dao.fetch(Accountant.class, Cnd.where("id", "=", iid));
    	if(user!=null){
    		return user;
    	}
    	return null;
    }
    
    /**
     * 添加预约信息
     * @param user
     * @return
     */
    @At
    //@Filters(@By(type=CheckSession.class, args={"loginUser", "/"})) // 检查当前Session是否带me这个属性
    public Object add(@Param("..")AppointmentMsg user) {
        NutMap re = new NutMap();
        String msg = checkUser(user, true);
        if (msg != null){
            return re.setv("ok", false).setv("msg", msg);
        }
        user = dao.insert(user);
        return re.setv("ok", true).setv("data", user);
    }
    
    /**
     * 修改预约信息
     * @param user
     * @return
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginUser", "/userLogin.jsp"})) // 检查当前Session是否带me这个属性
    public Object update(@Param("id")int iid,@Param("accountantId")int accountantId,@Param("timeGroup")String timeGroup,@Param("thisTimeGroup")String thisTimeGroup,@Param("content")String content,HttpSession session) {
        NutMap re = new NutMap();
        if(iid==0){
        	return null;
        }
        AppointmentMsg msg = (AppointmentMsg) searchById(iid);
        //msg.setId(iid);
        if(thisTimeGroup!=null && !"".equals(thisTimeGroup)){
        	msg.setTimeGroup(thisTimeGroup);//时间组
        	updateTimeGroup1(thisTimeGroup,accountantId,timeGroup);//更新财务管理人员时间表
        }
        //msg.setAccountantId(accountantId);//被预约财务人员ID
        msg.setContent(content);//预约内容
        //System.out.println("-=-=-=-=-=-=-=-=-=-="+content);
        //int state;//是否接收预约(0:未读   1:已接受  2:驳回)
        msg.setState(0);//该条预约信息状态，默认为未读
        //User user = (User) session.getAttribute("loginUser");
        //msg.setUserId(user.getId());//预约人的ID
        
        //dao.insert(msg);
        
        //AccountantModule am = new AccountantModule();
        //am.updateTimeGroup(thisTimeGroup,accountantId);//更新财务管理人员时间表
        
        dao.updateIgnoreNull(msg);// 真正更新的其实只有password
        return re.setv("ok", true);
    }
    
    /**
     * 修改预约信息
     * @param user
     * @return
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"})) // 检查当前Session是否带me这个属性
    public Object update1(@Param("id")int id,@Param("state")int state) {
        NutMap re = new NutMap();
        AppointmentMsg a_msg = (AppointmentMsg) queryObjById1(id);
        a_msg.setState(state);
        dao.updateIgnoreNull(a_msg);// 真正更新的其实只有password
        return re.setv("ok", true);
    }
    
    /**
     * 根据IID查询财务管理人员信息
     * @param iid
     * @return
     */
    public Object queryObjById1(int iid){
    	System.out.println(iid);
    	AppointmentMsg user = dao.fetch(AppointmentMsg.class, Cnd.where("id", "=", iid));
    	if(user!=null){
    		return user;
    	}
    	return null;
    }
    
    /**
     * 删除预约信息
     * @param id
     * @param userId
     * @return
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginUser", "/userLogin.jsp"})) // 检查当前Session是否带me这个属性
    public Object delete(@Param("iid")int id) {
    	if(id!=0){
    		dao.delete(AppointmentMsg.class, id); // 再严谨一些的话,需要判断是否为>0
    	}
        return new NutMap().setv("ok", true);
    }
    
    /**
     * 用户查询预约信息
     * @param name
     * @param pager
     * @return
     * http://127.0.0.1:8080/nutzerp/msg/query?pageNumber=1&pageSize=2
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginUser", "/userLogin.jsp"})) // 检查当前Session是否带me这个属性
    public Object query(@Param("name")String name, @Param("..")Pager pager,HttpSession session) {
    	User user =(User) session.getAttribute("loginUser");
        //Cnd cnd = Strings.isBlank(name)? null : Cnd.where("name", "like", "%"+name+"%");
    	Cnd cnd =Cnd.where("userId", "=", user.getId());
        //cnd.and("userId", "=", user.getId());
        //cnd.and("accountantId", "=", accountant.getId());
        //this.userId = userId;
		//this.accountantId = accountantId;
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(AppointmentMsg.class, cnd, pager));
        pager.setRecordCount(dao.count(AppointmentMsg.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
    }
    
    /**
     * 财务人员查询预约信息
     * @param name
     * @param pager
     * @return
     * http://127.0.0.1:8080/nutzerp/user/query?pageNumber=1&pageSize=2
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"})) // 检查当前Session是否带me这个属性
    public Object query1(@Param("state")int state, @Param("..")Pager pager,HttpSession session) {
    	Accountant accountant = (Accountant) session.getAttribute("loginAccountant");
        //Cnd cnd = Strings.isBlank(name)? null : Cnd.where("name", "like", "%"+name+"%");
    	Cnd cnd =Cnd.where("accountantId", "=", accountant.getId());
    	if(state==0 || state==1 || state==2){
    		cnd.and("state","=",state);
    	}
    	
        //cnd.and("userId", "=", user.getId());
        //cnd.and("accountantId", "=", accountant.getId());
        //this.userId = userId;
		//this.accountantId = accountantId;
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(AppointmentMsg.class, cnd, pager));
        pager.setRecordCount(dao.count(AppointmentMsg.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
    }
    
    /**
     * 检测
     * @param user
     * @param create
     * @return
     */
    protected String checkUser(AppointmentMsg user, boolean create) {
        if (user == null) {
            return "空对象";
        }
        return null;
    }
    
    /**
     * 查询一条预约信息
     * @param id
     * @return
     */
    public Object searchById(int id) {
        //System.out.println("请求登录");
        NutMap re = new NutMap();
        AppointmentMsg user = dao.fetch(AppointmentMsg.class, Cnd.where("id", "=", id));
        return user;
    }

    /**
     * 用户跳转到个人预约记录界面
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginUser", "/userLogin.jsp"}))
    @Ok("jsp:jsp.msg.list")
    public void goHistory(){
    	
    }
    
    

}
