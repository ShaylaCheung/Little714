package com.baby.cy.babyfun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by chenyu on 15/11/4.
 */
public class SignOutReceiver extends BroadcastReceiver {

    private OnSignOutListener listener;
    public static final String ON_SIGNOUT_FINISH = "ON_SIGNOUT_FINISH";

    public SignOutReceiver(OnSignOutListener listener){
        super();
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ON_SIGNOUT_FINISH)){
            listener.onSignOutFinish();
            return;
        }
    }


    public interface OnSignOutListener{
        public void onSignOutFinish();
    }

}