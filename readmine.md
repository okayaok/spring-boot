##项目简介

###框架
该项目使用SpringBoot框架用来简化新Spring应用的初始搭建以及开发过程；使用Spring Data Jpa用来和进行数据库的查询；使用Thymeleaf前端模版解析
动态解析HTML5页面；使用Spring Data Dialect标签用来完成分页功能，该标签只能配合Spring Data Jpa中的Page以及Pageable使用；使用Gradle来管理
所加载的依赖jar包。

###项目环境

####准备工作
1. JDK版本：JDK1.8及以上，若需低版本请自行修改；
2. Gradle：由于该项目是通过Gradle来管理依赖的jar包，因此需要本地安装Gradle。Gradle版本推荐使用Gradle-2.5及以上，本项目使用的版本为Gradle-3.5；
3. Git：该项目是通过Git进行版本控制的，项目的提交、更新操作需要使用Git命令进行，已方便获取服务器最新代码。Git版本推荐：Git-2.8；
4. Intellij Idea：项目是由Idea开发工具进行开发，若使用Eclipse，请先将项目中的".idea"文件删除后，自行导入。 Idea版本推荐：Idea Ultimate-2017.1。
5. MySql：项目使用MySql数据库作为运行数据库，若您没有安装，请至MySql官网下载安装。

####开始导入

#####项目下载
1. 项目下载地址：https://git.oschina.net/okayaok/books-system.git
2. 项目下载方式有两种，其一是直接下载Zip文件，解压后可以直接导入Idea，该方法不做过多介绍。下面主要介绍第二中方式，使用Git进行项目下载。
    1. 若您是初次安装Git并使用，请在使用Git时，首先设置用户名和邮箱以便识别您的信息。命令如下：
    ```git config --global user.name '您的姓名或者昵称' git config --global user.email '您的邮箱账号'```
    2. 使用Git Clone命令将服务器代码克隆至您的机器，命令如下：
    ```git clone https://git.oschina.net/okayaok/books-system.git '您想存放项目的绝对路径'```
        
#####项目导入
1. 打开Idea,若您的Idea没有注册，请自行搜索'Idea 2017 注册码'进行注册。
2. 在Idea的欢迎页面点击'Open',然后在弹出的路径选择中找到您下载的项目，点击确认。
3. 在项目的设置页面选择'Use Auto Import'和'Use Local Gradle'，选择您安装的Gradle路径后，点击确认。
4. 若以上步骤准确无误后，等待项目自动部署完成即可。

###项目启动
在配置完成后，运行BooksApplication类启动该项目，启动完成后在浏览器地址输入'http://localhost:8080/login'即可成功访问。