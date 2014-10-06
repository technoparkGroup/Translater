package group.technopark.translater.network;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import group.technopark.translater.Constants;

public class MyBroadcastReciever extends BroadcastReceiver {

    private TextViewSetter mCallback;

    public MyBroadcastReciever(TextViewSetter callback) {
        mCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int code = intent.getIntExtra(Constants.BUNDLE_CODE, Constants.CODE_FAIL);
        if(code == Constants.CODE_OK) {
            String text = intent.getStringExtra(Constants.BUNDLE_TEXT);
            if (text != null)
                mCallback.setText(text);
        } else
            mCallback.notifyError();
    }

    public interface TextViewSetter {
        void setText(String text);
        void notifyError();
    }
}
