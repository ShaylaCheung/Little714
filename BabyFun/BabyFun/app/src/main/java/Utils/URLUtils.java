package Utils;

/**
 * 程序中所用到的接口地址
 * Created by chenyu on 2015/11/3.
 */
public class URLUtils {
    private static final String url_head = "http://192.168.1.114:8080/";
    //注册接口地址
    public static final String signin_url = url_head + "BabyFun/api/addUser";
    //登录接口地址
    public static final String login_url = url_head + "BabyFun/api/login";
    //获取某个手机号是否已经注册
    public static final String isPhoneSignIn_url = url_head + "BabyFun/api/isPhoneSignIn";
    //获取某个用户昵称是否已经注册
    public static final String isNameSignIn_url = url_head + "BabyFun/api/isNameSignIn";
    //更新用户信息
    public static final String updateUserInformation_url = url_head + "BabyFun/api/updateUserInformation";
    //获取某项音乐是否存在播放列表中
    public static final String isInPlayList_url = url_head + "BabyFun/api/isInPlayList";
    //获取播放列表地址
    public static final String prefer_music_url = url_head + "BabyFun/api/getPlayList";
    //删除播放列表某项的地址
    public static final String deleteUserMusic_url = url_head + "BabyFun/api/deleteUserMusic";
    //添加音乐到播放列表的地址
    public static final String addUserMusic_url = url_head + "BabyFun/api/addUserMusic";
    //获取服务器所有音乐的地址
    public static final String find_music_url = url_head + "BabyFun/api/getAllMusicList";
    //播放服务器端音乐的地址
    public static final String play_find_music_url = url_head + "BabyFun";

}
