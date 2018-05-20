#开发笔记
#Android   
------
###开发思路
解耦:通过接口回调方式，解耦业务逻辑、数据处理、ui更新。整合在Activity中，通过业务逻辑模块决策事件的走向，通过数据处理模块推动事件的进度，通过ui更新让使用者进行下一步业务，形成闭环。

----
####优化listview


1. 首先，虽然大家都知道，还是提一下，利用好 convertView 来重用 View，切忌每次 getView() 都新建。ListView 的核心原理就是重用 View。ListView 中有一个回收器，Item 滑出界面的时候 View 会回收到这里，需要显示新的 Item 的时候，就尽量重用回收器里面的 View。   
2. 利用好 View Type，例如你的 ListView 中有几个类型的 Item，需要给每个类型创建不同的 View，这样有利于 ListView 的回收，当然类型不能太多；
3. 尽量让 ItemView 的 Layout 层次结构简单，这是所有 Layout 都必须遵循的； 
4. 善用自定义 View，自定义 View 可以有效的减小 Layout 的层级，而且对绘制过程可以很好的控制；
5. 尽量能保证 Adapter 的 hasStableIds() 返回 true，这样在 notifyDataSetChanged() 的时候，如果 id 不变，ListView 将不会重新绘制这个 View，达到优化的目的；
6. 每个 Item 不能太高，特别是不要超过屏幕的高度，可以参考 Facebook 的优化方法，把特别复杂的 Item 分解成若干小的 Item
7. 为了保证 ListView 滑动的流畅性，getView() 中要做尽量少的事情，不要有耗时的操作。特别是滑动的时候不要加载图片，停下来再加载，这个库可以帮助你 [Glide](https://github.com/bumptech/glide)
8. 使用 RecycleView 代替。 ListView 每次更新数据都要 notifyDataSetChanged()，有些太暴力了。RecycleView 在性能和可定制性上都有很大的改善，推荐使用。
9. 有时候，需要从根本上考虑，是否真的要使用 ListView 来实现你的需求，或者是否有其他选择？  
- 选择性刷新部分view重写hasStableIds() 返回值为true 重写getItemId(int position) 确保每个item有唯一的id参考文章:[RecyclerView带刺的玫瑰](https://blog.csdn.net/xiaozhiwz/article/details/46664719)


-----
###AdapterContextMenuInfo的相关用法  
参考文章:[AdapterContextMenuInfo的使用](https://blog.csdn.net/zl594389970/article/details/14145753)  

1.当显示 AdapterView 的上下文菜单时，为 onCreateContextMenu(ContextMenu, View, ContextMenuInfo) 回调函数提供的额外的菜单信息。  
menu.add方法可以指定为list中某一view创造的itemid方便在触发事件中进行处理  
2.onContextItemSelected(MenuItem item)选择上下文时给出相应处理
  
------
###按位“或”赋值运算符 (|=)
参考文章:[按位“或”赋值运算符 (|=)](https://blog.csdn.net/qingheshijiyuan/article/details/53056939)  

-----
###迭代器
关于Iterator主要有三个方法：hasNext()、next()、remove()

 hasNext:没有指针下移操作，只是判断是否存在下一个元素

 next：指针下移，返回该指针所指向的元素

 remove：删除当前指针所指向的元素，一般和next方法一起用，这时候的作用就是删除next方法返回的元素     

关于迭代器 Iterator<E> iterator();方法 创建的是对原有list的引用集合 对迭代器进行remove操作代表从原有集合中移除指定元素  
关于remove的报错参见文章:[Iterator的remove()和Collection的remove()](https://blog.csdn.net/qh_java/article/details/50154405)  


-----
###TIM SDK
- 腾讯云SDK很蛋疼不支持Gradle集成，所以只能手动导入sdk，该sdk依赖v4支持包，所以需要导入对应gradle版本的v4包
- 导入sdk包还不算OK，需要在app层级的下的gradle文件中加入so文件的依赖(不添加依赖的后果就是永远6013错误，难以想象的是腾讯的文档居然没有写出这步，仅仅在常见问题中提了一下6013错误常见问题存在未依赖so库问题)  
示例代码如下:   
``` 
	sourceSets {
	        main {
	            jniLibs.srcDirs('libs/imso', 'libs')
	        }
	    }
``` 
 
-----
###动态权限申请问题
* 当sdk版本大于等于23，等于Android M版本以上时需要动态申请权限  
1.检查是否有权限： ActivityCompat.checkSelfPermission  
2.是否重新请求授权（用户之前拒绝过）： shouldShowRequestPermissionRationale()
eg:`ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS) `  
3.请求授权： ActivityCompat.requestPermissions   
4.搜权结果，通过 ActivityCompat.OnRequestPermissionsResultCallback 回调获取授权结果，判断是否授权  
详细参考这篇文章 [Android M动态申请获取权限](https://blog.csdn.net/fenggering/article/details/53432401)  
ps:主要权限申请例如访问外部文件等应当在app主入口初始化时进行申请，次要权限例如相机图库访问sms等可以在具体业务逻辑中进行申请


------
###Activity中界面跳转问题
1.正常Intent跳转 `startActivity()`  
2.有数据传递需求的跳转 `startActivityForResult() `同时需要重载父Activity的`onActivityResult()`方法，主要用于便于大量子Activity时，可以通过手动指定resultcode来判断当前父Activity由谁唤醒，通过switch方法降低开销，减少代码量  

----

###FragmentTabHost + Fragment 构建app主界面以及底部导航  

- 参考教程:  
&ensp;[FragmentTabHost + Fragment 实现底部菜单](https://www.jianshu.com/p/491386d6435c)  


- 排坑:  
&ensp;


- 应用databinding的tips：
&ensp;




----


###databinding

- MVVM设计模式下，流程大致为view与viewmodel双向绑定，viewmodle与model进行双向数据交互，viewmodel中更新ui  
拓展到Android中大致为：  
>view层：xml(展示ui)+Activity(渲染ui)  
modle：处理数据的类  
viewmodel：为view层提供数据  
  
- 启用databinding支持后,实现xml与viewmodel的绑定，Activity专职渲染ui，进行解耦,减少臃肿的控件绑定代码,在viewmodel中绑定封装监听事件，留出接口供Activity调用，实现解耦.  

- 应用到Android开发中 viewmodel与view间的简单互动可以不通过Activity 但是复杂的交互例如RecycleView等还是需要Activity介入进行渲染ui viewmodel只负责填充数据 最后的更新ui放在Activity中执行  

**关于databind的一些坑：**  
1.绑定后获取实例 必须使用binding关键字get获得实例，重新new出的实例与binding中的实例完全不同


#####一些错误

	Error:Execution failed for task ':app:compileDebugJavaWithJavac'.
	 android.databinding.tool.util.LoggedErrorException: Found data binding errors.
	  ****/ data binding error ****msg:The expression user.getUsername() cannot be inverted: Two-way binding cannot resolve a setter for java.lang.String property 'username'
	  file:H:\Android\BYSJDEMO01\app\src\main\res\layout\user_login.xml
	  loc:38:33 - 38:45
	  ****\ data binding error ****   
解决：bean类中补全getter setter  

	Error:Execution failed for task ':app:compileDebugJavaWithJavac'.
	> android.databinding.tool.util.LoggedErrorException: Found data binding errors.
	  ****/ data binding error ****msg:Identifiers must have user defined types from the XML file. login_model is missing it
	  file:H:\Android\BYSJDEMO01\app\src\main\res\layout\user_login.xml
	  loc:83:42 - 83:52
	  ****\ data binding error ****  
解决：检查是否输入错误变量   

----

######开启双向绑定
>xml语法 `@=`  
>java代码:bean类继承BaseObservalbe ，对get方法添加注解 `@Bindale`，在set中添加`notifyPropertyChanged(BR.xxx);`  

代码示例:
	
	public void setUsername(String username) {
	        this.username = username;
	        notifyPropertyChanged(BR.username);
	    }
	@Bindable
	    public String getUsername() {
	        return username;
	    }
>或者  继承BaseObservable+notifyChange();  

代码示例:  

	public class TestUser extends BaseObservable{
	    private String firstName;
	    public String getFirstName() {
	        return firstName;
	    }
	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	        notifyChange();
	    }
	}
####tips
1. 空间背景设置透明度 alpha  
2. xx分之xx布局 use LinearLayout
3. 设置无标题界面  
`<style name="AppTheme.NoActionBar">`  
    `<item name="windowNoTitle">true</item>`  
    `<item name="windowActionBar">false</item>`   
    `<item name="android:windowFullscreen">true</item>`  
    `<item name="android:windowContentOverlay">@null</item>`  
`</style>`

----

####关于接口回调
方式1
>通过含参方式，使用匿名方法的方式回调，适合单函数回调

方式2
>通过接口约束方式，添加setter方法，设置回调关系，适合类中大量回调方法




附上RelativeLayout布局属性  

	// 相对于给定ID控件
	android:layout_above 将该控件的底部置于给定ID的控件之上;
	
	android:layout_below 将该控件的底部置于给定ID的控件之下;
	
	android:layout_toLeftOf    将该控件的右边缘与给定ID的控件左边缘对齐;
	
	android:layout_toRightOf  将该控件的左边缘与给定ID的控件右边缘对齐;
	
	android:layout_alignBaseline  将该控件的baseline与给定ID的baseline对齐;
	
	android:layout_alignTop        将该控件的顶部边缘与给定ID的顶部边缘对齐;
	
	android:layout_alignBottom   将该控件的底部边缘与给定ID的底部边缘对齐;
	
	android:layout_alignLeft        将该控件的左边缘与给定ID的左边缘对齐;
	
	android:layout_alignRight      将该控件的右边缘与给定ID的右边缘对齐;
	
	// 相对于父组件
	
	android:layout_alignParentTop      如果为true,将该控件的顶部与其父控件的顶部对齐;
	
	android:layout_alignParentBottom 如果为true,将该控件的底部与其父控件的底部对齐;
	
	android:layout_alignParentLeft      如果为true,将该控件的左部与其父控件的左部对齐;
	
	android:layout_alignParentRight    如果为true,将该控件的右部与其父控件的右部对齐;
	
	// 居中
	
	android:layout_centerHorizontal 如果为true,将该控件的置于水平居中;
	
	android:layout_centerVertical     如果为true,将该控件的置于垂直居中;
	
	android:layout_centerInParent   如果为true,将该控件的置于父控件的中央;
	
	// 指定移动像素
	
	android:layout_marginTop      上偏移的值;
	
	android:layout_marginBottom 下偏移的值;
	
	android:layout_marginLeft 　　左偏移的值;
	
	android:layout_marginRight 　 右偏移的值;

----

###Server
腾讯云的centos7  
切换到 root 用户  
`sudo su`  
1.安装并启动 Nginx  
`yum install nginx`  
`systemctl start nginx.service   # 开启 nginx 服务`  
`systemctl enable nginx.service   # 跟随系统启动`  
此时使用 localhost 或者输入服务器ip，(腾讯云下安全组配置开放80端口)应该会出现 Welcome to nginx 的欢迎页面了

2.安装并启动 MySQL（这里我用的是 MariaDB，就是 MySQL 换个壳，没多大区别…
	
	yum install mariadb-server mariadb
	systemctl start mariadb   # 开启 mysql 服务
	systemctl enable mariadb   # 跟随系统启动
3.初始化 MySQL 安全策略

`mysql_secure_installation`  
会提示输入 root 密码，默认为空，直接回车即可
然后就问是否重新设置 root 密码，当然是要重新设置的啦。设置完后还有几个问题都输入 y 同意就行了  

4.修改centos默认的py2为py3  


####tips：

以 php-fpm 为例，安装其7.1版本。 
通过yum search php-fpm,可以查到要用php71-php-fpm这个名称来安装。

`yum install php71-php-fpm`

提示安装完成.

这时，先查出其对应的rpm包名。

`rpm -qa|grep php-fpm`  
指令 `rpm -qa|grep`+包名  
得到如下结果：

	[root]# rpm -qa|grep php-fpm 
	php-fpm-5.6.17-1.el6.remi.x86_64 
	php71-php-fpm-7.1.2-1.el6.remi.x86_64

然后执行：

`rpm -ql php71-php-fpm-7.1.2-1.el6.remi.x86_64`  
指令`rpm -ql`+完整包名

得到：

	[root]# rpm -ql php71-php-fpm-7.1.2-1.el6.remi.x86_64 
	/etc/logrotate.d/php71-php-fpm 
	/etc/opt/remi/php71/php-fpm.conf 
	/etc/opt/remi/php71/php-fpm.d 
	/etc/opt/remi/php71/php-fpm.d/www.conf 
	/etc/opt/remi/php71/sysconfig/php-fpm 
	/etc/rc.d/init.d/php71-php-fpm 
	/opt/remi/php71/root/usr/sbin/php-fpm 
	/opt/remi/php71/root/usr/share/doc/php71-php-fpm-7.1.2 
	/opt/remi/php71/root/usr/share/doc/php71-php-fpm-7.1.2/fpm_LICENSE 
	/opt/remi/php71/root/usr/share/doc/php71-php-fpm-7.1.2/php-fpm.conf.default 
	/opt/remi/php71/root/usr/share/doc/php71-php-fpm-7.1.2/www.conf.default 
	/opt/remi/php71/root/usr/share/fpm 
	/opt/remi/php71/root/usr/share/fpm/status.html 
	/opt/remi/php71/root/usr/share/man/man8/php-fpm.8.gz 
	/var/opt/remi/php71/lib/php/opcache 
	/var/opt/remi/php71/lib/php/session 
	/var/opt/remi/php71/lib/php/wsdlcache 
	/var/opt/remi/php71/log/php-fpm 
	/var/opt/remi/php71/run/php-fpm

- 配置nginx：  
我们可以找到conf文件夹下的 nginx.conf 文件， 将其修改 :

	server {
	    listen 80;
	    server_name _;
	    location /{
	        proxy_pass http://127.0.0.1:8000;
	    }
	}
所有访问ip80端口的访问都会被代理到本机Gunicorn启动的Flask项目中  

- 启动服务  
`gunicorn -D -w 3 -b 127.0.0.1:8000 RunServer:app`  

- 重启gunicorn服务  
寻找进程树`pstree -ap|grep gunicorn`  
使用`kill -HUP PID`

- 关于flask:  
json.dumps()方法支持字典，支持list  
关于 安装sqlchemy之后的warning 按提示修改指定配置文件(__init__.py),none改为false

- 部署服务器:  
执行sql相关语句  
nomoudle mysqldxxx  
unbuntu参考 github的一个[fork](https://github.com/PyMySQL/mysqlclient-python) 开发了针对py3的支持
 

