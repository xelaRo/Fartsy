package com.optima.fartsy;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Alex on 2/10/2019.
 */

public class SoundPoolSounds implements ISoundPoolSounds {

    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> mSoundMap;

    public void setFartSounds(Context context) {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        mSoundMap = new HashMap<>();

        mSoundMap.put(0, mSoundPool.load(context, R.raw.fart01, 1));
        mSoundMap.put(1, mSoundPool.load(context, R.raw.fart02, 1));
        mSoundMap.put(2, mSoundPool.load(context, R.raw.fart03, 1));
        mSoundMap.put(3, mSoundPool.load(context, R.raw.fart04, 1));
        mSoundMap.put(4, mSoundPool.load(context, R.raw.fart05, 1));
        mSoundMap.put(5, mSoundPool.load(context, R.raw.fart06, 1));
        mSoundMap.put(6, mSoundPool.load(context, R.raw.fart07, 1));
        mSoundMap.put(7, mSoundPool.load(context, R.raw.fart08, 1));
        mSoundMap.put(8, mSoundPool.load(context, R.raw.fart09, 1));
        mSoundMap.put(9, mSoundPool.load(context, R.raw.fart10, 1));
        mSoundMap.put(10, mSoundPool.load(context, R.raw.fart11, 1));
        mSoundMap.put(11, mSoundPool.load(context, R.raw.fart12, 1));
        mSoundMap.put(12, mSoundPool.load(context, R.raw.fart13, 1));
        mSoundMap.put(13, mSoundPool.load(context, R.raw.fart14, 1));
        mSoundMap.put(14, mSoundPool.load(context, R.raw.fart15, 1));
        mSoundMap.put(15, mSoundPool.load(context, R.raw.fart16, 1));
        mSoundMap.put(16, mSoundPool.load(context, R.raw.fart17, 1));
        mSoundMap.put(17, mSoundPool.load(context, R.raw.fart18, 1));
        mSoundMap.put(18, mSoundPool.load(context, R.raw.fart19, 1));
        mSoundMap.put(19, mSoundPool.load(context, R.raw.fart20, 1));
        mSoundMap.put(20, mSoundPool.load(context, R.raw.fart21, 1));
        mSoundMap.put(21, mSoundPool.load(context, R.raw.fart22, 1));
        mSoundMap.put(22, mSoundPool.load(context, R.raw.fart23, 1));
        mSoundMap.put(23, mSoundPool.load(context, R.raw.fart24, 1));
    }

    public void playSound(int sound, Context context, boolean loop) {
        AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (mSoundPool != null) {
            if(loop){
                mSoundPool.play(mSoundMap.get(sound), streamVolumeMax, streamVolumeMax, 1, -1, 1.0f);
            }else{
                mSoundPool.play(mSoundMap.get(sound), streamVolumeMax, streamVolumeMax, 1, 0, 1.0f);
            }
            if(!mgr.isMusicActive()){
                mgr.setStreamVolume(AudioManager.STREAM_MUSIC,streamVolumeCurrent,0);
            }
        }
    }

    public void resetSounds(){
        if(mSoundPool != null){
            mSoundMap = null;
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    public int getRandomNumber(){
        Random r = new Random();
        int Low = 0;
        int High = 23;
        int rndm = r.nextInt(High-Low) + Low;

        return  rndm;
    }

}
