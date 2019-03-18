package com.optima.fartsy;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class FartTrapCustomDialogClass extends Dialog implements
        android.view.View.OnClickListener{

    public Activity activity;
    public Button cancel;
    public TextView tv_count;
    public IFartTrap mIFartTrap;

    public FartTrapCustomDialogClass(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        mIFartTrap = (FartTrapActivity)activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fart_trap_custom_dialog);
        cancel = (Button) findViewById(R.id.btn_cancel);
        tv_count = (TextView) findViewById(R.id.tv_count);
        cancel.setOnClickListener(this);
        mIFartTrap.startCountdown(tv_count);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                mIFartTrap.cancelCountDown();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

