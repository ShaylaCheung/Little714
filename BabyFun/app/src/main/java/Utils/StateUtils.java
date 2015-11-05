package Utils;

/**
 * Created by Administrator on 2015/11/3.
 */
public class StateUtils {
    public static boolean isLogin = false;
    public static Long user_id;
    public static String user_name;
    private static String user_phone;

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        StateUtils.isLogin = isLogin;
    }

    public static Long getUser_id() {
        return user_id;
    }

    public static void setUser_id(Long user_id) {
        StateUtils.user_id = user_id;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
        StateUtils.user_name = user_name;
    }

    public static String getUser_phone() {
        return user_phone;
    }

    public static void setUser_phone(String user_phone) {
        StateUtils.user_phone = user_phone;
    }
}
