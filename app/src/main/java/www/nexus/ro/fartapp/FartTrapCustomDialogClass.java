package www.nexus.ro.fartapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class FartTrapCustomDialogClass extends Dialog implements
        android.view.View.OnClickListener{

    private ISoundPoolSounds mISoundPoolSounds;
    public Activity activity;
    public Button cancel;
    public TextView tv_count;

    public FartTrapCustomDialogClass(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.mISoundPoolSounds = new SoundPoolSounds();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fart_trap_custom_dialog);
        cancel = (Button) findViewById(R.id.btn_cancel);
        tv_count = (TextView) findViewById(R.id.tv_count);
        cancel.setOnClickListener(this);
        startCountDown();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void startCountDown(){

        CountDownTimer cT =  new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished/60000);
                int va = (int)( (millisUntilFinished%60000)/1000);
                tv_count.setText(v+":"+String.format("%02d",va));
            }

            public void onFinish() {
                mISoundPoolSounds.playSound(1, activity, true);
            }
        };
        cT.start();
    }
}

