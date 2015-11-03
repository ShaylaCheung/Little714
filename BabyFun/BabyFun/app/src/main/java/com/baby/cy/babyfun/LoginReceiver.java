package com.baby.cy.babyfun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by chenyu on 15/11/2.
 */
public class LoginReceiver extends BroadcastReceiver{
    public static final String ON_LOGIN_FINISH = "ON_LOGIN_FINISH";
    private OnLoginListener listener;
    public LoginReceiver(OnLoginListener listener){
        super();
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ON_LOGIN_FINISH)){
            listener.onLoginFinished();
            return;

        }
    }

    public interface OnLoginListener{
        public void onLoginFinished();
    }
}
