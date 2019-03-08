package www.nexus.ro.fartapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

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
               }else{

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
            }
        });

        mISoundPoolSounds.setFartSounds(CronoActivity.this);
        btnStop.setEnabled(false);

        //      ADMOB
        MobileAds.initialize(this, "ca-app-pub-7794688344285417~5407186226");
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

    public void startCountDown(int milis){

         cT =  new CountDownTimer(milis, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished/60000);
                int va = (int)( (millisUntilFinished%60000)/1000);
                tv_count.setText(v+":"+String.format("%02d",va));
            }

            public void onFinish() {
                mISoundPoolSounds.playSound(1, CronoActivity.this, true);
            }
        };
        cT.start();
    }
}
