package edu.dlsu.mobapde.picnicpanicbeta;

import android.graphics.Bitmap;

/**
 * Created by acer on 15/11/2017.
 */

public class BackgroundScale implements Transformation {
    private int mSize;
    private boolean isHeightScale;

    public BackgroundScale(int size, boolean isHeightScale) {
        mSize = size;
        this.isHeightScale = isHeightScale;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        float scale;
        int newSize;
        Bitmap scaleBitmap;
        if (isHeightScale) {
            scale = (float) mSize / source.getWidth();
            newSize = Math.round(source.getHeight() * scale);
            scaleBitmap = Bitmap.createScaledBitmap(source, mSize, newSize, true);
        } else {
            scale = (float) mSize / source.getWidth();
            newSize = Math.round(source.getHeight() * scale);
            scaleBitmap = Bitmap.createScaledBitmap(source, mSize, newSize, true);
        }
        if (scaleBitmap != source) {
            source.recycle();
        }

        return scaleBitmap;
    }

    @Override
    public String key() {
        return "scaleRespectRatio"+mSize+isHeightScale;
    }
}
