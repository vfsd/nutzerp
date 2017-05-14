package com.vfsd;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.vfsd.bean.User;

public class MainSetup implements Setup {

	public void init(NutConfig nc) {
        Ioc ioc = nc.getIoc();
        Dao dao = ioc.get(Dao.class);
        // 如果提示没有createTablesInPackage方法,请确认用了最新版的nutz,且老版本的nutz已经删除干净
        Daos.createTablesInPackage(dao, "com.vfsd", false);

        // 初始化默认根用户
        if (dao.count(User.class) == 0) {
            User user = new User();
            user.setId(0);
            user.setName("admin");
            user.setPassword("123456");
            
            dao.insert(user);
        }
    }

    public void destroy(NutConfig nc) {
         // webapp销毁之前执行的逻辑
         // 这个时候依然可以从nc取出ioc, 然后取出需要的ioc 对象进行操作
    }

}