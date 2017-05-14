package com.vfsd.module;

import java.util.List;

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
import com.vfsd.util.TimeGroup;

/**
 * 财务管理人员控制器
 * @author wangyongfu
 *
 */
@IocBean // 声明为Ioc容器中的一个Bean
@At("/accountant") // 整个模块的路径前缀
@Ok("json:{locked:'password',ignoreNull:true}") // 忽略password和salt属性,忽略空属性的json输出
@Fail("http:500") // 抛出异常的话,就走500页面
public class AccountantModule {
	@Inject
    protected Dao dao; // 就这么注入了,有@IocBean它才会生效

    /**
     * 统计
     * @return
     */
    @At
    public int count() {
        return dao.count(Accountant.class);
    }
    
    /**
     * 财务管理人员登录
     * @param name
     * @param password
     * @param session
     * @return
     */
    @At
    //@Ok("jsp:jsp.user.main") // 真实路径是 /WEB-INF/jsp/accountant/list.jsp
    //@Ok(">>:/user/profile")
    public Object login(@Param("username")String name, @Param("password")String password, HttpSession session) {
        System.out.println("请求登录");
        NutMap re = new NutMap();
        Accountant user = dao.fetch(Accountant.class, Cnd.where("name", "=", name).and("password", "=", password).and("state","=",0));
        if (user == null) {
            return false;
        } else {
            session.setAttribute("loginAccountant", user);
            session.setAttribute("loginAccountantName", user.getName());
            return true;
            //return re.setv("ok", true);
        }
    }
    
    /**
     * 财务管理人员退出登录
     * @param session
     */
    @At
    @Ok(">>:/")// 跟其他方法不同,这个方法完成后就跳转首页了
    public void logout(HttpSession session) {
        //session.invalidate();
    	session.removeAttribute("loginAccountant");
    }
    
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"})) 
    @Ok("jsp:jsp.accountant.main") // 真实路径是 /WEB-INF/jsp/user/list.jsp
    public void toMain() {
    }
    
    @At
    @Filters(@By(type=CheckSession.class, args={"loginUser", "/userLogin.jsp"})) 
    @Ok("jsp:jsp.accountant.list")
    public void toList(){}
    /**
     * 添加财务管理人员
     * @param user
     * @return
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAdmin", "/adminLogin.jsp"}))
    public Object add(@Param("..")Accountant user) {
        NutMap re = new NutMap();
        String msg = checkUser(user, true);
        if (msg != null){
            return re.setv("ok", false).setv("msg", msg);
        }
        user.setState(0);
        user = dao.insert(user);
        return re.setv("ok", true).setv("data", user);
    }
    
    /**
     * 修改财务管理人员信息
     * @param user
     * @return
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAccountant", "/"})) // 检查当前Session是否带loginAccountant这个属性
    public Object update(@Param("..")Accountant user) {
        NutMap re = new NutMap();
        String msg = checkUser(user, false);
        if (msg != null){
            return re.setv("ok", false).setv("msg", msg);
        }
        //user.setName(null);// 不允许更新用户名
        dao.updateIgnoreNull(user);// 真正更新的其实只有password
        return re.setv("ok", true);
    }
    
    /**
     * 管理员修改财务管理人员信息
     * @param name
     * @param state
     * @param id
     * @return
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAdmin", "/adminLogin.jsp"}))
    public Object update1(@Param("name")String name,@Param("state")int state,@Param("id")int id) {
    	Accountant news = (Accountant) queryObjById(id);
        
        if(name!=null && !"".equals(name)){
        	news.setName(name);
        }
        
        if(state==0 || state==1){
        	news.setState(state);
        }
        news.setPassword(null);
        dao.updateIgnoreNull(news);// 
        return new NutMap().setv("ok", true);
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
     * 删除财务管理人员
     * @param id
     * @param userId
     * @return
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAdmin", "/adminLogin.jsp"})) // 检查当前Session是否带loginAccountant这个属性
    public Object delete(@Param("id")int id, @Attr("loginAccountant")int userId) {
        if (userId == id) {
            return new NutMap().setv("ok", false).setv("msg", "不能删除当前用户!!");
        }
        dao.delete(Accountant.class, id); // 再严谨一些的话,需要判断是否为>0
        return new NutMap().setv("ok", true);
    }
    
    /**
     * 查询财务管理人员
     * @param name
     * @param pager
     * @return
     * http://127.0.0.1:8080/nutzerp/accountant/query?pageNumber=1&pageSize=2
     */
    @At
    //@Filters(@By(type=CheckSession.class, args={"loginUser", "/"})) // 检查当前Session是否带me这个属性
    public Object query(@Param("name")String name, @Param("..")Pager pager) {
        Cnd cnd = Strings.isBlank(name)? null : Cnd.where("name", "like", "%"+name+"%");
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(Accountant.class, cnd, pager));
        pager.setRecordCount(dao.count(Accountant.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
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
     * 检测
     * @param user
     * @param create
     * @return
     */
    protected String checkUser(Accountant user, boolean create) {
        if (user == null) {
            return "空对象";
        }
        if (create) {
            if (Strings.isBlank(user.getName()) || Strings.isBlank(user.getPassword()))
                return "用户名/密码不能为空";
        } else {
            if (Strings.isBlank(user.getPassword()))
                return "密码不能为空";
        }
        String passwd = user.getPassword().trim();
        if (6 > passwd.length() || passwd.length() > 12) {
            return "密码长度错误";
        }
        user.setPassword(passwd);
        if (create) {
            int count = dao.count(Accountant.class, Cnd.where("name", "=", user.getName()));
            if (count != 0) {
                return "用户名已经存在";
            }
        } else {
            if (user.getId() < 1) {
                return "用户Id非法";
            }
        }
        if (user.getName() != null)
            user.setName(user.getName().trim());
        return null;
    }

    /**
     * 根据ID查询财务管理人员已经被预约的时间组
     * @param id
     * @return
     */
    @At
    public Object getTimeGroup(@Param("iid")int id){
    	Accountant user = dao.fetch(Accountant.class, Cnd.where("id", "=", id));
    	//List<TimeGroup> timeGroups = user.getAppointment();
    	String timeGroup = user.getTimeGroup();
    	return timeGroup;
    }

}
