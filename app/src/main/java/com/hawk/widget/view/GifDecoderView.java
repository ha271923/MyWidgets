package com.hawk.widget.view;

/**
 * Created by ha271 on 2016/5/20.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.hawk.widget.R;

import java.io.InputStream;

public class GifDecoderView extends ImageView {

    private boolean mIsPlayingGif = false;

    private GifDecoder mGifDecoder;

    private Bitmap mTmpBitmap;

    public RemoteViews mRv;
    public int mVid;

    final Handler mHandler = new Handler();



    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            if (mTmpBitmap != null && !mTmpBitmap.isRecycled()) {
                //GifDecoderView.this.setImageBitmap(mTmpBitmap); // Approach 2: Extracting the Bitmaps
                Log.i("GIFa","2. TID="+Thread.currentThread()+"  mRv="+mRv+"  mVid="+mVid+"  mTmpBitmap="+mTmpBitmap);
                // mRv.setImageViewBitmap(mVid,mTmpBitmap); // Approach 2H: Extracting the Bitmaps, Hawk added R.id.gif_imageView resource

                // mRv.setInt(R.id.gif_imageView, "setBackgroundResource", R.drawable.apple); // failed: no any img update
                // mRv.setInt(R.id.gif_imageView, "setImageResource", R.drawable.apple); // failed: no any img update
                // mRv.setInt(mVid, "setImageResource", R.drawable.apple); // failed: no any img update
                // mRv.setImageViewBitmap(mVid, drawableToBitmap(R.drawable.apple)); // failed: no any img update
            }
        }
    };

    public GifDecoderView(Context context, InputStream stream) {
        super(context);
        playGif(stream);
    }

    public GifDecoderView(Context context, InputStream stream, RemoteViews rv, int vid) {
        super(context);
        playGif(stream);
        mRv = rv;
        mVid = vid;
    }


    private void playGif(InputStream stream) {
        mGifDecoder = new GifDecoder();
        mGifDecoder.read(stream);

        mIsPlayingGif = true;

        new Thread(new Runnable() {
            public void run() {
                final int n = mGifDecoder.getFrameCount();
                final int ntimes = mGifDecoder.getLoopCount();
                int repetitionCounter = 0;
                do {
                    for (int i = 0; i < n; i++) {
                        mTmpBitmap = mGifDecoder.getFrame(i);
                        int t = mGifDecoder.getDelay(i);
                        mHandler.post(mUpdateResults);
                        try {
                            Thread.sleep(t);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(ntimes != 0) {
                        repetitionCounter ++;
                    }
                } while (mIsPlayingGif && (repetitionCounter <= ntimes));
            }
        }).start();
    }

    public void stopRendering() {
        mIsPlayingGif = true;
    }
}
