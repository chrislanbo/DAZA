框架搭建


修改昵称
http://app.mituomi.com/Mobile/user/update_name.html
参数 user_name wwwwww

修改图片
http://app.mituomi.com/Mobile/user/uploadPic.html

修改微信号码
http://app.mituomi.com/Mobile/user/update_weixinCode.html
参数 weixinCode 23231321321

修改性别
http://app.mituomi.com/Mobile/user/update_sex.html
post 表单提交
sex[]:1

查询地址
http://app.mituomi.com/Mobile/user/site_admin

添加新地址
http://app.mituomi.com/Mobile/user/add_address.html

查询市
http://app.mituomi.com/Mobile/User/cat_id.html
id 1

查询县/区
http://app.mituomi.com/Mobile/User/er_id.html
id 35

删除地址
post
http://app.mituomi.com/Mobile/user/ajax_delete_d.html
参数id 19621

修改密码
http://app.mituomi.com/Mobile/user/reset_password.html


添加单个商品到购物车
http://app.mituomi.com/index.php?m=Home&c=Cart&a=ajaxAddCart
goods_id=157
goods_num=1
goods_spec=0

添加多个商品到购物车
http://app.mituomi.com/index.php?m=Home&c=Cart&a=ajaxAddCart
goods_id=157
goods_num=21
goods_spec=0
sub=0

fragment 和 activity 生命周期
![](http://img.my.csdn.net/uploads/201211/29/1354170699_6619.png)

Fragment与Activity生命周期对比图：

!(http://img.my.csdn.net/uploads/201211/29/1354170682_3824.png)

1. 当一个fragment被创建的时候，它会经历以下状态.

onAttach()
onCreate()
onCreateView()
onActivityCreated()
2. 当这个fragment对用户可见的时候，它会经历以下状态。

onStart()
onResume()
3. 当这个fragment进入“后台模式”的时候，它会经历以下状态。

onPause()
onStop()
4. 当这个fragment被销毁了（或者持有它的activity被销毁了），它会经历以下状态。

onPause()
onStop()
onDestroyView()
onDestroy()
onDetach()
5. 就像activitie一样，在以下的状态中，可以使用Bundle对象保存一个fragment的对象。

onCreate()
onCreateView()
onActivityCreated()
6. fragments的大部分状态都和activitie很相似，但fragment有一些新的状态。

onAttached() —— 当fragment被加入到activity时调用（在这个方法中可以获得所在的activity）。
onCreateView() —— 当activity要得到fragment的layout时，调用此方法，fragment在其中创建自己的layout(界面)。
onActivityCreated() —— 当activity的onCreated()方法返回后调用此方法
onDestroyView() —— 当fragment中的视图被移除的时候，调用这个方法。
onDetach() —— 当fragment和activity分离的时候，调用这个方法。
一旦activity进入resumed状态（也就是running状态），你就可以自由地添加和删除fragment了。因此，只有当activity在resumed状态时，fragment的生命周期才能独立的运转，其它时候是依赖于activity的生命周期变化的。