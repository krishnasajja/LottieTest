package com.ksajja.lottietest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieImageAsset;
import com.amulyakhare.textdrawable.TextDrawable;

import java.io.InputStream;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by ksajja on 11/8/17.
 */

public class Playground {
    LottieAnimationView animationView;


//        final SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                //if (!animationView.isAnimating()) {
//                //animationView.setScale(0.1f);
//                animationView.setProgress(progress / 100f);
//                animationView.resumeAnimation();
//                //}
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//        animationView.addAnimatorUpdateListener(
//                new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        seekBar.setProgress((int) (animation.getAnimatedFraction() * 100));
//                    }
//                });



    public Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    private Drawable textAsDrawable(String text) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRect("A", Color.RED);
        return drawable;
    }


    private Bitmap textAsBitmap(String text) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRect("A", Color.RED);
        return drawableToBitmap(drawable);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    private void setImageAssetDelegate(final Context context) {
        animationView.setImageAssetDelegate(new ImageAssetDelegate() {
            @Override
            public Bitmap fetchBitmap(LottieImageAsset asset) {
                //USe a HashMap to improve the performance
                //Then use recycleBitmaps - otherwise you'll leak the bitmaps.
                //Use onDetachedFromWindow or onStop() or ondestroy

                InputStream istr;
                Bitmap bitmap = null;

                try {
                    //For return bitmaps for dynamic images. Read and return bitmaps from the assets for static images
                    switch (asset.getFileName()) {
                        case "profilePic":
                            return null; //profileBitmap;
                        default:
                            istr = context.getAssets().open("images/" + asset.getFileName());
                            BitmapFactory.Options opts = new BitmapFactory.Options();
                            opts.inScaled = true;
                            opts.inDensity = 160;
                            bitmap = BitmapFactory.decodeStream(istr, null, opts);
                            return bitmap;
                    }
                } catch (Exception e) {
                    Log.e("Bitmap", e.getMessage(), e);
                    // handle exception
                }

                return bitmap;
            }
        });
    }

    /**
     * Use text delegate to change text dynamically
     */
    private void setTextDelegate() {
        //        TextDelegate textDelegate = new TextDelegate(animationView) {
//            @Override
//            public String getText(String input) {
//                // You have ${user.points} SYW points!
//                return input.replace("This", "That");
//            }
//        };
//        animationView.enableMergePathsForKitKatAndAbove(true);
//        animationView.useHardwareAcceleration(true);
//

        //animationView.setTextDelegate(textDelegate);
    }

}

//
//try {
//
//        Glide.with(this)
//        .load("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
//        .asBitmap()
//        .listener(new RequestListener<String, Bitmap>() {
//@Override
//public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//        return false;
//        }
//
//@Override
//public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//        ((LottieDrawable)animationView.getDrawable()).recycleBitmaps();
//        ((LottieDrawable)animationView.getDrawable()).invalidateSelf();
//        //((LottieDrawable)animationView.getDrawable()).getImageAsset
//        animationView.updateBitmap("image_0", bm);
//        animationView.updateBitmap("image_1", bm);
//        ((LottieDrawable)animationView.getDrawable()).invalidateSelf();
//        animationView.playAnimation();
//        return true;
//        }
//        }).into((ImageView) findViewById(R.id.image));
//        } catch (Exception e) {
//        e.printStackTrace();
//        }


