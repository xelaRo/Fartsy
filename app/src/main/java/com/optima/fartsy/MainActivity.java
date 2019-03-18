package com.optima.fartsy;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vorlonsoft.android.rate.AppRate;
import com.vorlonsoft.android.rate.OnClickButtonListener;
import com.vorlonsoft.android.rate.StoreType;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ISoundPoolSounds mISoundPoolSounds;
    private ImageButton btn_Fart;
    private ShareButton shareButton;
    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private SharePhoto mSharePhoto;
    public MainActivity(){
        mISoundPoolSounds = new SoundPoolSounds();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
    }

    public void initUI(){
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mISoundPoolSounds.setFartSounds(this);

        btn_Fart = findViewById(R.id.btn_Fart);
        shareButton = findViewById(R.id.fb_share_button);



        btn_Fart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = mISoundPoolSounds.getRandomNumber();
                mISoundPoolSounds.playSound(i, MainActivity.this,false);
            }
        });

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.optima.fartsy"))
                .build();


//      Firebase
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//      RATE
        appRate();
//      ADMOB
        MobileAds.initialize(this, "ca-app-pub-4123877177396127~6931692281");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            mISoundPoolSounds.resetSounds();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        mISoundPoolSounds.resetSounds();
        mISoundPoolSounds.setFartSounds(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
            mISoundPoolSounds.resetSounds();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.optima.fartsy"))
                    .build();
            shareButton.setShareContent(content);
            shareButton.performClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Bundle params = new Bundle();
        params.putInt("ButtonID",item.getItemId());
        String btnName = "null";

        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_fart_list:
                btnName = "Fart List Menu Button";
                intent = new Intent(this, FartListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_crono:
                btnName = "Fart Prank Menu Button";
                intent = new Intent(this, CronoActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_trap:
                btnName = "Fart Trap Menu Button";
                intent = new Intent(this, FartTrapActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_donate:
                btnName = "Donate Menu Button";
                intent = new Intent(this, DonateActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_feedback:
                btnName = "Feedback Menu Button";
                intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
                break;
            default:
                btnName = "Other Button";
                break;
        }

        mFirebaseAnalytics.logEvent(btnName,params);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void appRate(){
        AppRate.with(this)
                .setStoreType(StoreType.GOOGLEPLAY) //default is GOOGLEPLAY (Google Play), other options are
                //           AMAZON (Amazon Appstore) and
                //           SAMSUNG (Samsung Galaxy Apps)
                .setInstallDays((byte) 0) // default 10, 0 means install day
                .setLaunchTimes((byte) 3) // default 10
                .setRemindInterval((byte) 2) // default 1
                .setRemindLaunchTimes((byte) 2) // default 1 (each launch)
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                //Java 8+: .setOnClickButtonListener(which -> Log.d(MainActivity.class.getName(), Byte.toString(which)))
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(byte which) {
                        Log.d(MainActivity.class.getName(), Byte.toString(which));
                    }
                })
                .setTitle(R.string.rate_app_title)
                .setMessage(R.string.rate_app_message)
                .monitor();

        if (AppRate.with(this).getStoreType() == StoreType.GOOGLEPLAY) {
            //Check that Google Play is available
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) != ConnectionResult.SERVICE_MISSING) {
                // Show a dialog if meets conditions
                AppRate.showRateDialogIfMeetsConditions(this);
            }
        } else {
            // Show a dialog if meets conditions
            AppRate.showRateDialogIfMeetsConditions(this);
        }
    }
}
