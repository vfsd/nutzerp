package com.vfsd.module;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.vfsd.MainSetup;

import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;

@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={
		"*js", "js/",
		//"*css", "css/",
        "*anno", "com.vfsd",// 这个package下所有带@IocBean注解的类,都会登记上
        "*tx", // 事务拦截 aop
        "*async"}) // 异步执行aop

@Modules(scanPackage=true)
public class MainModule {

}
