# VideoDemoQcl
一行代码快速实现视频播放，Android视频播放，AndroidMP3播放，安卓视频播放一行代码搞定，真正实现Android的全屏功能，立志成为Android平台使用最广泛的视频播放控件

#先看效果图
![image](https://github.com/qiushi123/VideoDemoQcl/blob/master/qcl_images/qcl.png?raw=true) 

#一，主要特点
#####1,全屏时启动新Activity实现播放器真正的全屏功能
#####2,能在ListView、ViewPager和ListView、ViewPager和Fragment等多重嵌套模式下全屏工作
#####3,ListView的拖拽和ViewPager的滑动时如果划出屏幕会自动重置视频
#####4,视频大小的屏幕适配，宽或长至少有两个对边是充满屏幕的，另外两个方向居中
#####5,可以在加载、暂停、播放等各种状态中正常进入全屏和退出全屏
#####6,根据自己应用的颜色风格换肤
#####7,播放MP3时显示缩略图片
	
#二，使用步骤

##（一，导入到项目（建议用第二种方式）
		1.引入类库
			compile 'fm.jiecao:jiecaovideoplayer:1.8'//引入类库时有个bug所以建议直接引入源码lib
		2.引入源码
			将jcvideoplayer-lib作为类库引入到你的项目中
###如下图
![image](https://github.com/qiushi123/VideoDemoQcl/blob/master/qcl_images/qcl2.png?raw=true) 
##（二.添加布局
		<fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
			android:id="@+id/videocontroller1"
			android:layout_width="match_parent"
			android:layout_height="200dp" />
##（三.设置视频地址、缩略图地址、标题
		JCVideoPlayer videoController = (JCVideoPlayer) findViewById(R.id.videocontroller);
		videoController.setUp("http://2449.vod.myqcloud.com/2449_43b6f696980311e59ed467f22794e792.f20.mp4",
			"http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640",
			"一行代码实现视频播放");
##（四.在包含播放器的Fragment或Activity的onPause()方法中调用JCVideoPlayer.releaseAllVideos();


#五.其他接口

###1，设置皮肤（主题），这里设置的是整个项目里的全局皮肤，优先级:全局皮肤>默认皮肤
		//设置全局皮肤
		JCVideoPlayer.setGlobleSkin(R.color.titleColor, R.color.timeColor, R.drawable.skin_seek_progress,
                R.color.bottom_bg, R.drawable.skin_enlarge_video, R.drawable.skin_shrink_video);
				
###2，修改缩略图的scalType，默认的缩略图的scaleType是fitCenter，
		这时候图片如果和屏幕尺寸不同的话左右或上下会有黑边，可以根据客户端需要改成fitXY或者其他模式
		JCVideoPlayer.setThumbImageViewScalType(ImageView.ScaleType.FIT_XY);
		
###3.直接进入全屏，比如在webview中视频播放的适配很难做，调用此接口直接全屏播放
		JCVideoPlayer.toFullscreenActivity(this,
			"http://2449.vod.myqcloud.com/2449_43b6f696980311e59ed467f22794e792.f20.mp4",
			"http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640",
			"一行代码实现视频播放");
###4.不显示标题
		videoController.setUp("http://2449.vod.myqcloud.com/2449_ded7b566b37911e5942f0b208e48548d.f20.mp4",//
			"http://p.qpic.cn/videoyun/0/2449_ded7b566b37911e5942f0b208e48548d_2/640",
			"一行代码实现视频播放", false);
###5.在ListView和ViewPager中将视频移除屏幕外，会在onDetachedFromWindow时重置视频。
		目标是在库外只需要添加布局，添加配置，其他的问题都在库内判断和操作。

		
#六，混淆
	##Eventbus混淆
	-keepclassmembers class ** {
		public void onEvent*(***);
	}
	# Only required if you use AsyncExecutor
	-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
		public <init>(java.lang.Throwable);
	}
	# Don't warn for missing support classes
	-dontwarn de.greenrobot.event.util.*$Support
	-dontwarn de.greenrobot.event.util.*$SupportManagerFragment
	
	
	
#我的个人博客
## http://blog.csdn.net/qiushi_1990
