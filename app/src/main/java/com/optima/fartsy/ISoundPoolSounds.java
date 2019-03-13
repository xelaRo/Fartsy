package com.optima.fartsy;

import android.content.Context;

/**
 * Created by Alex on 2/10/2019.
 */

public interface ISoundPoolSounds {

    void setFartSounds(Context context);
    void playSound(int sound, Context context, boolean loop);
    void resetSounds();
    int getRandomNumber();
}
