package com.joy.glide.library.cache;

import android.graphics.Bitmap;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by joybar on 26/05/2018.
 */

public final class DrawableSave {
   private Bitmap bitmap;
   private GifDrawable gifDrawable;

    public DrawableSave(Bitmap bitmap, GifDrawable gifDrawable) {
        this.bitmap = bitmap;
        this.gifDrawable = gifDrawable;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public GifDrawable getGifDrawable() {
        return gifDrawable;
    }
}
