propmanage
==========

基于web程序，用来管理项目中使用的properties配置文件，并且修改后不需重启服务，使配置信息立即生效。

使用步骤：
1.将dist文件夹下的propmanage_1.0.0.jar引入项目
2.在web.xml注册PropManageServlet，配置如下：
<servlet>
<servlet-name>PropManageServlet</servlet-name>
<servlet-class>com.prop.servlet.PropManageServlet</servlet-class>
<init-param>
<!--登录配置管理的用户名-->
<param-name>loginUsername</param-name>
<param-value>admin</param-value>
</init-param>
<init-param>
<!--登录配置管理的密码-->
<param-name>loginPwd</param-name>
<param-value>dascom</param-value>
</init-param>
<init-param>
<!--要管理的配置文件-->
<param-name>configFile</param-name>
<param-value>cfg.properties</param-value>
</init-param>
</servlet>
<servlet-mapping>
<servlet-name>PropManageServlet</servlet-name>
<!--不可修改-->
<url-pattern>/prop/*</url-pattern>
</servlet-mapping>
3.启动项目，访问管理程序，输入http://127.0.0.1:端口/项目名/prop
4.代码中使用PropertiesUtil.get(key)方法获取配置信息。
