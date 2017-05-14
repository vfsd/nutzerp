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

import com.vfsd.bean.Admin;

/**
 * 管理员控制器
 * @author wangyongfu
 *
 */
@IocBean // 声明为Ioc容器中的一个Bean
@At("/admin") // 整个模块的路径前缀
@Ok("json:{locked:'password',ignoreNull:true}") // 忽略password和salt属性,忽略空属性的json输出
@Fail("http:500") // 抛出异常的话,就走500页面
public class AdminModule {
	
    @Inject
    protected Dao dao; // 就这么注入了,有@IocBean它才会生效
    
    /**
     * 管理员登录
     * @param name
     * @param password
     * @param session
     * @return
     */
    @At
    //@Ok("jsp:jsp.user.main") // 真实路径是 /WEB-INF/jsp/user/list.jsp
    //@Ok(">>:/user/profile")
    public Object login(@Param("username")String name, @Param("password")String password, HttpSession session) {
        //System.out.println("请求登录");
        NutMap re = new NutMap();
        Admin user = dao.fetch(Admin.class, Cnd.where("name", "=", name).and("password", "=", password));
        if (user == null) {
            return false;
        } else {
            session.setAttribute("loginAdmin", user);
            session.setAttribute("loginAdminName", user.getName());
            return true;
            //return re.setv("ok", true);
        }
    }
    
    /**
     * 管理员退出登录
     * @param session
     */
    @At
    @Ok(">>:/")// 跟其他方法不同,这个方法完成后就跳转首页了
    public void logout(HttpSession session) {
        //session.invalidate();
    	session.removeAttribute("loginAdmin");
    }
    
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAdmin", "/adminLogin.jsp"}))
    @Ok("jsp:jsp.admin.main") // 真实路径是 /WEB-INF/jsp/admin/list.jsp
    public void toMain() {
    }
    
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAdmin", "/adminLogin.jsp"}))
    @Ok("jsp:jsp.admin.listUser") // 真实路径是 /WEB-INF/jsp/admin/list.jsp
    public void toUser(){
    	
    }
    
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAdmin", "/adminLogin.jsp"}))
    @Ok("jsp:jsp.admin.listAccount") // 真实路径是 /WEB-INF/jsp/admin/list.jsp
    public void toAccount(){
    	
    }

}
