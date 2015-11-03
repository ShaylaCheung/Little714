package com.baby.cy.babyfun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Potato on 15/11/2.
 */
public class SignInReceiver extends BroadcastReceiver{

    private OnSignInListener listener;
    public static final String ON_SIGNIN_FINISH = "ON_SIGNIN_FINISH";

    public SignInReceiver(OnSignInListener listener){
        super();
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ON_SIGNIN_FINISH)){
            listener.onSignInFinish();
            return;
        }
    }


    public interface OnSignInListener{
        public void onSignInFinish();
    }

}


