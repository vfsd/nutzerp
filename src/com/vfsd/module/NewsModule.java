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
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import com.vfsd.bean.Accountant;
import com.vfsd.bean.AppointmentMsg;
import com.vfsd.bean.News;

@IocBean // 声明为Ioc容器中的一个Bean
@At("/news") // 整个模块的路径前缀
@Ok("json:{ignoreNull:true}") // 忽略password和salt属性,忽略空属性的json输出
@Fail("http:500") // 抛出异常的话,就走500页面
public class NewsModule {
	@Inject
    protected Dao dao; // 就这么注入了,有@IocBean它才会生效

	@At
    @Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"}))
    @Ok("jsp:jsp.news.list") // 真实路径是 /WEB-INF/jsp/user/list.jsp
    public void toList() {
    }
	
	/**
     * 添加新闻公告
     * @param content
     * @return
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"}))  // 检查当前Session是否带loginAccountant这个属性
    public Object add(@Param("content")String content,HttpSession session) {
        NutMap re = new NutMap();
        News news = new News();
        news.setContent(content);
        Accountant accountant = (Accountant) session.getAttribute("loginAccountant");
        news.setAccountId(accountant.getId());
        news.setState(0);//新闻状态(0:正常显示  1:禁止显示)
        news = dao.insert(news);
        return re.setv("ok", true).setv("data", news);
    }
    
    /**
     * 修改公告内容
     * @param user
     * @return
     */
    @At
    //@Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"})) 
    public Object update(@Param("content")String content,@Param("state")int state,@Param("id")int id) {
        News news = (News) searchById(id);
        
        if(content!=null && !"".equals(content)){
        	news.setContent(content);
        }
        
        if(state==0 || state==1){
        	news.setState(state);
        }
        dao.updateIgnoreNull(news);// 真正更新的其实只有password
        return new NutMap().setv("ok", true);
    }
    
    /**
     * 删除公告
     * @param id
     * @param userId
     * @return
     */
    @At
    //@Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"})) 
    public Object delete(@Param("id")int id) {
        dao.delete(News.class, id); // 再严谨一些的话,需要判断是否为>0
        return new NutMap().setv("ok", true);
    }
    
    /**
     * 查询公告
     * @param name
     * @param pager
     * @return
     * http://127.0.0.1:8080/nutzerp/news/query?pageNumber=1&pageSize=2
     */
    @At
    @Filters(@By(type=CheckSession.class, args={"loginAccountant", "/accountantLogin.jsp"})) 
    public Object query(@Param("name")String name, @Param("..")Pager pager,HttpSession session) {
        //Cnd cnd = Strings.isBlank(name)? null : Cnd.where("content", "like", "%"+name+"%");//模糊查询
    	Accountant accountant = (Accountant) session.getAttribute("loginAccountant");
    	Cnd cnd =Cnd.where("accountId", "=", accountant.getId());
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(News.class, cnd, pager));
        pager.setRecordCount(dao.count(News.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
    }
    
    /**
     * 查询公告
     * @param name
     * @param pager
     * @return
     * http://127.0.0.1:8080/nutzerp/news/query?pageNumber=1&pageSize=2
     */
    @At
    public Object query1(@Param("name")String name, @Param("..")Pager pager) {
        //Cnd cnd = Strings.isBlank(name)? null : Cnd.where("content", "like", "%"+name+"%");//模糊查询
        //cnd.and("state","=","0");
    	Cnd cnd =Cnd.where("state","=","0");
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(News.class, cnd, pager));
        pager.setRecordCount(dao.count(News.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
    }
    
    /**
     * 检测
     * @param user
     * @param create
     * @return
     */
    protected String checkNews(News user, boolean create) {
        if (user == null) {
            return "空对象";
        }
        if (create) {
            if (Strings.isBlank(user.getContent()))
                return "用户名/密码不能为空";
        }
        return null;
    }
    
    
    /**
     * 查询一条公告信息
     * @param id
     * @return
     */
    public Object searchById(int id) {
        //System.out.println("请求登录");
        NutMap re = new NutMap();
        News news = dao.fetch(News.class, Cnd.where("id", "=", id));
        return news;
    }


}
