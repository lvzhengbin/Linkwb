package com.ice.wb.util;
/**
 * 常量表示类
 * @author ice
 *
 */
public class Constants {

	// 应用的key 请到官方申请正式的appkey替换APP_KEY
    public static final String APP_KEY = "3362367431";

    // 替换为开发者REDIRECT_URL
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    // 新支持scope（想要获取的权限）：支持传入多个scope权限，用逗号分隔
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "invitation_write";
    
    // 获取当前登录用户及其所关注用户的最新微博URL
    public static final String HOME_TIMELINE_URL = "https://api.weibo.com/2/statuses/home_timeline.json";
	
    // 根据用户ID获取用户信息
    public static final String USERS_SHOW = "https://api.weibo.com/2/users/show.json";
	
}
