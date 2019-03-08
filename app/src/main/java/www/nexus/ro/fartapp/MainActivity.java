package www.nexus.ro.fartapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ISoundPoolSounds mISoundPoolSounds;
    private ImageButton btn_Fart;
    private AdView mAdView;

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

        btn_Fart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = getRandomNumber();
                mISoundPoolSounds.playSound(i, MainActivity.this,false);
            }
        });

//      RATE
        Rater.app_launched(this);

//      ADMOB
        MobileAds.initialize(this, "ca-app-pub-7794688344285417~5407186226");
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_fart_list:
                intent = new Intent(this, FartListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_crono:
                intent = new Intent(this, CronoActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_trap:
                intent = new Intent(this, FartTrapActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_feedback:
                intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public int getRandomNumber(){
        Random r = new Random();
        int Low = 0;
        int High = 23;
        int rndm = r.nextInt(High-Low) + Low;

        return  rndm;
    }

}
