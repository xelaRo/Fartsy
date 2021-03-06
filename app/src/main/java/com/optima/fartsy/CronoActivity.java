package com.optima.fartsy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Alex on 2/10/2019.
 */

public class CronoActivity extends AppCompatActivity {

    private ISoundPoolSounds mISoundPoolSounds;
    private CountDownTimer cT;
    private TextView tv_count;
    private Button btnStart;
    private Button btnStop;
    private AdView mAdView;
    public CronoActivity(){
        mISoundPoolSounds = new SoundPoolSounds();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    public void initUI(){
        setContentView(R.layout.activity_crono);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tv_count = findViewById(R.id.tv_count);
        final EditText et_time = findViewById(R.id.et_time);
        btnStart = findViewById(R.id.btn_crono_start);
        btnStop = findViewById(R.id.btn_crono_stop);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String stringTime = et_time.getText().toString();
               if(!stringTime.isEmpty())
               {
                   int time = Integer.parseInt(stringTime);
                   startCountDown(time * 1000);
                   btnStart.setEnabled(false);
                   btnStop.setEnabled(true);
                   et_time.setText("");
                   hideKeyboard(CronoActivity.this);
               }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mISoundPoolSounds.resetSounds();
                mISoundPoolSounds.setFartSounds(CronoActivity.this);
                cT.cancel();
                tv_count.setText("00:00");
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                et_time.setText("");
                hideKeyboard(CronoActivity.this);
            }
        });

        mISoundPoolSounds.setFartSounds(CronoActivity.this);
        btnStop.setEnabled(false);

        //      ADMOB
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        mISoundPoolSounds.resetSounds();
//        mISoundPoolSounds.setFartSounds(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mISoundPoolSounds.resetSounds();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void startCountDown(int milis){

         cT =  new CountDownTimer(milis, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished/60000);
                int va = (int)( (millisUntilFinished%60000)/1000);
                tv_count.setText(v+":"+String.format("%02d",va));
            }

            public void onFinish() {
                int i = mISoundPoolSounds.getRandomNumber();
                mISoundPoolSounds.playSound(i, CronoActivity.this, true);
            }
        };
        cT.start();
    }

    public static void hideKeyboard( Activity activity ) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService( Context.INPUT_METHOD_SERVICE );
        View f = activity.getCurrentFocus();
        if( null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom( f.getClass() ) )
            imm.hideSoftInputFromWindow( f.getWindowToken(), 0 );
        else
            activity.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
    }
}
