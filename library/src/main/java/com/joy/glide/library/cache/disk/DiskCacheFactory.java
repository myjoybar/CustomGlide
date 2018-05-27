package com.joy.glide.library.cache.disk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.joy.glide.library.cache.DrawableSave;
import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.utils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by joybar on 27/05/2018.
 */

public class DiskCacheFactory {

    public static final String FILE_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/cache/pics";


    public static DiskCacheFactory getInstance() {
        return DiskCacheFactoryHolder.INSTANCE;
    }

    private static class DiskCacheFactoryHolder {
        private static DiskCacheFactory INSTANCE = new DiskCacheFactory();
    }


    public DrawableSave getDrawableSaveFromDisk(DrawableKey key) {
        String url = key.getId();
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(FILE_PATH, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                DrawableSave drawableSave = new DrawableSave(bitmap,null);
                return drawableSave;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDrawableSave2Local(DrawableKey key, DrawableSave drawableSave) {
        try {
            String fileName = MD5Encoder.encode(key.getId());
            File file = new File(FILE_PATH, fileName);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            Bitmap bitmap = drawableSave.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
