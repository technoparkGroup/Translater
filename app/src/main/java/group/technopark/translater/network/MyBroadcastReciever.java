package group.technopark.translater.network;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReciever extends BroadcastReceiver {

    private TextViewSetter mCallback;

    public MyBroadcastReciever(TextViewSetter callback) {
        mCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra(TranslatingService.TEXT);
        if(text != null)
            mCallback.setText(text);
    }

    public interface TextViewSetter {
        void setText(String text);
    }
}
