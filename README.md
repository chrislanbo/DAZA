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
