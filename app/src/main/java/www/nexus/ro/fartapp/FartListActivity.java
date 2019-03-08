package www.nexus.ro.fartapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import www.nexus.ro.fartapp.model.FartListModel;


/**
 * Created by Alex on 1/21/2019.
 */

public class FartListActivity extends AppCompatActivity
{
    private ISoundPoolSounds mISoundPoolSounds;
    private AdView mAdView;

    public FartListActivity(){
        mISoundPoolSounds = new SoundPoolSounds();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        mISoundPoolSounds.setFartSounds(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI(){
        setContentView(R.layout.activity_fart_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.grid_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Construct the data source
        ArrayList<FartListModel> arrayOfUsers = new ArrayList<FartListModel>();
        // Create the adapter to convert the array to views
        FartListAdapter adapter = new FartListAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lv_fartList);
        listView.setAdapter(adapter);
        adapter.addAll(SetButonText());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        mISoundPoolSounds.playSound(0,FartListActivity.this,false);
                        break;
                    case 1:
                        mISoundPoolSounds.playSound(1,FartListActivity.this,false);
                        break;
                    case 2:
                        mISoundPoolSounds.playSound(2,FartListActivity.this,false);
                        break;
                    case 3:
                        mISoundPoolSounds.playSound(3,FartListActivity.this,false);
                        break;
                    case 4:
                        mISoundPoolSounds.playSound(4,FartListActivity.this,false);
                        break;
                    case 5:
                        mISoundPoolSounds.playSound(5,FartListActivity.this,false);
                        break;
                    case 6:
                        mISoundPoolSounds.playSound(6,FartListActivity.this,false);
                        break;
                    case 7:
                        mISoundPoolSounds.playSound(7,FartListActivity.this,false);
                        break;
                    case 8:
                        mISoundPoolSounds.playSound(8,FartListActivity.this,false);
                        break;
                    case 9:
                        mISoundPoolSounds.playSound(9,FartListActivity.this,false);
                        break;
                    case 10:
                        mISoundPoolSounds.playSound(10,FartListActivity.this,false);
                        break;
                    case 11:
                        mISoundPoolSounds.playSound(11,FartListActivity.this,false);
                        break;
                    case 12:
                        mISoundPoolSounds.playSound(12,FartListActivity.this,false);
                        break;
                    case 13:
                        mISoundPoolSounds.playSound(13,FartListActivity.this,false);
                        break;
                    case 14:
                        mISoundPoolSounds.playSound(14,FartListActivity.this,false);
                        break;
                    case 15:
                        mISoundPoolSounds.playSound(15,FartListActivity.this,false);
                        break;
                    case 16:
                        mISoundPoolSounds.playSound(16,FartListActivity.this,false);
                        break;
                    case 17:
                        mISoundPoolSounds.playSound(17,FartListActivity.this,false);
                        break;
                    case 18:
                        mISoundPoolSounds.playSound(18,FartListActivity.this,false);
                        break;
                    case 19:
                        mISoundPoolSounds.playSound(19,FartListActivity.this,false);
                        break;
                    case 20:
                        mISoundPoolSounds.playSound(20,FartListActivity.this,false);
                        break;
                    case 21:
                        mISoundPoolSounds.playSound(21,FartListActivity.this,false);
                        break;
                    case 22:
                        mISoundPoolSounds.playSound(22,FartListActivity.this,false);
                        break;
                    case 23:
                        mISoundPoolSounds.playSound(23,FartListActivity.this,false);
                        break;
                }
            }
        });

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

    public ArrayList<FartListModel> SetButonText(){
        ArrayList<FartListModel> arrayList = new ArrayList<>();

        arrayList.add(new FartListModel("Mosquito"));
        arrayList.add(new FartListModel("Fartsy"));
        arrayList.add(new FartListModel("Etc"));
        arrayList.add(new FartListModel("Bomb"));
        arrayList.add(new FartListModel("Pineapple"));
        arrayList.add(new FartListModel("Hey"));
        arrayList.add(new FartListModel("Wow"));
        arrayList.add(new FartListModel("Cute"));
        arrayList.add(new FartListModel("Mosquito"));
        arrayList.add(new FartListModel("Fartsy"));
        arrayList.add(new FartListModel("Etc"));
        arrayList.add(new FartListModel("Bomb"));
        arrayList.add(new FartListModel("Pineapple"));
        arrayList.add(new FartListModel("Hey"));
        arrayList.add(new FartListModel("Wow"));
        arrayList.add(new FartListModel("Cute"));
        return arrayList;
    }
}
