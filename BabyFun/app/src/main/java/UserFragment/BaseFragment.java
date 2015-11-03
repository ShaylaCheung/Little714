
package UserFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/10/14.
 */
public abstract class BaseFragment extends Fragment {

    public Context context;
    public int layoutId;

    public BaseFragment(int layoutId) {

        this.layoutId = layoutId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        this.context = getActivity();
        View view = inflater.inflate(layoutId,null);
        initView(view);
        return view;
    }

//    public Context getFrgContext(){
//        return context;
//    }

    public abstract void initView(View view);

}
