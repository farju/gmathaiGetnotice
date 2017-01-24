package com.mgn.get_noticed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY = 3000;
    private static final int COLOR_CHANGE_DELAY = 300;
    private static final int FADE_ANIMATION_DURATION = 300;


    private final Handler mHideHandler = new Handler();
    private final Handler mColorChangeHandler = new Handler();

    private View mContentView;
    private TextView mDisplayTextView;
    private HorizontalScrollView mControlsView;

    static int colorCounter = 0;

    private boolean mVisible;
    private boolean mRunning;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final Runnable mChangeColorRunnable = new Runnable() {
        @Override
        public void run() {
            mContentView.setBackgroundColor(mColorValuesArray[colorCounter]);
            //mDisplayTextView.setTextColor(Util.getComplementaryColor(mColorValuesArray[colorCounter]));
            ++colorCounter;
            if (colorCounter == mColorValuesArray.length)
                colorCounter = 0;
            if (mRunning)
                changeColor();
        }
    };

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY);
            }
            return false;
        }
    };

    private Animation mFadeInAnimation;
    private Animation mFadeOutAnimation;
    private int[] mColorValuesArray;
    ImageView playControlsImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVisible = true;
        mRunning = true;

        mControlsView = (HorizontalScrollView) findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        mDisplayTextView = (TextView) findViewById(R.id.display_text);
        playControlsImageView = (ImageView) findViewById(R.id.fullscreen_content_controls_play);
        playControlsImageView.setTag("PlayControls");
        playControlsImageView.setOnClickListener(this);

        ImageView settingsImageView = (ImageView) findViewById(R.id.fullscreen_content_controls_settings);
        settingsImageView.setTag("Settings");
        settingsImageView.setOnClickListener(this);

        hideUI();
        initializeAnimations();

        mColorValuesArray = getResources().getIntArray(R.array.rainbow_colors);
        changeColor();

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        mContentView.setOnTouchListener(mDelayHideTouchListener);

        animateColorChanges(false);
    }

    private void hideUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        int uiVisibilityFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uiVisibilityFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        mContentView.setSystemUiVisibility(uiVisibilityFlags);
    }

    private void initializeAnimations() {
        mFadeInAnimation = new AlphaAnimation(0, 1);
        mFadeInAnimation.setInterpolator(new DecelerateInterpolator());
        mFadeInAnimation.setDuration(FADE_ANIMATION_DURATION);
        mFadeInAnimation.setAnimationListener(this);

        mFadeOutAnimation = new AlphaAnimation(1, 0);
        mFadeOutAnimation.setInterpolator(new AccelerateInterpolator());
        mFadeOutAnimation.setDuration(FADE_ANIMATION_DURATION);
        mFadeOutAnimation.setAnimationListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        mControlsView.startAnimation(mFadeOutAnimation);
        mVisible = false;
    }

    private void show() {
        mVisible = true;
        mControlsView.startAnimation(mFadeInAnimation);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation.equals(mFadeInAnimation)) {
            mControlsView.setVisibility(View.VISIBLE);
        } else if (animation.equals(mFadeOutAnimation)) {
            mControlsView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    private void changeColor() {
        mColorChangeHandler.removeCallbacks(mChangeColorRunnable);
        mColorChangeHandler.postDelayed(mChangeColorRunnable, COLOR_CHANGE_DELAY);
    }


    @Override
    public void onClick(View view) {
        if (view instanceof ImageView) {
            if ("PlayControls".equals(view.getTag())) {
                animateColorChanges(!mRunning);
            } else if ("Settings".equals(view.getTag())) {
                startActivity(new Intent(this, SettingsActivity.class));
            }
        }
    }

    private void animateColorChanges(boolean shouldAnimate) {
        if (!shouldAnimate)
            playControlsImageView.setImageResource(R.drawable.ic_invert_colors_white_24px);
        else {
            playControlsImageView.setImageResource(R.drawable.ic_invert_colors_off_white_24px);
            changeColor();
        }
        mRunning = shouldAnimate;
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY);
        }
    }
}
