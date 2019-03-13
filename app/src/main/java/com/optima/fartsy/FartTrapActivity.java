package com.optima.fartsy;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Alex on 2/23/2019.
 */

public class FartTrapActivity extends AppCompatActivity implements IFartTrap, SensorEventListener {

    private ISoundPoolSounds mISoundPoolSounds;
    private AdView mAdView;

    public SensorManager sensorManager;
    private Sensor accelerometer;
    private FartTrapCustomDialogClass fartTrapCustomDialogClass;
    private CountDownTimer cT;
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    public FartTrapActivity() {
        mISoundPoolSounds = new SoundPoolSounds();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    public void initUI() {
        setContentView(R.layout.activity_farttrap);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//      SoundPool
        mISoundPoolSounds.setFartSounds(FartTrapActivity.this);

//      Senzors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

//      Btn Click
        ImageButton fartTrapBtn = findViewById(R.id.fart_trap_btn);
        fartTrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fartTrapCustomDialogClass = new FartTrapCustomDialogClass(FartTrapActivity.this);
                fartTrapCustomDialogClass.show();
            }
        });

//      ADMOB
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mISoundPoolSounds.resetSounds();
        mISoundPoolSounds.setFartSounds(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mISoundPoolSounds.resetSounds();
    }

    @Override
    public void startCountdown(final View tv_count) {
         cT = new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished / 60000);
                int va = (int) ((millisUntilFinished % 60000) / 1000);
                TextView count = (TextView) tv_count;
                count.setText(v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
                sensorManager.registerListener(FartTrapActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
            }
        };
        cT.start();
    }

    @Override
    public void cancelCountDown() {
        cT.cancel();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = sensorEvent.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if (mAccel > 0.5f) {
                int i = mISoundPoolSounds.getRandomNumber();
                mISoundPoolSounds.playSound(i, FartTrapActivity.this, false);
                sensorManager.unregisterListener(this);
                fartTrapCustomDialogClass.dismiss();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
