package com.spdb.training.socket.trans.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月18日 下午1:26:25 
*   类说明: rpc服务注解
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransCodeAnno {

	String value();
}
