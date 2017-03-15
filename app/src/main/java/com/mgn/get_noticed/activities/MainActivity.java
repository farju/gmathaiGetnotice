package com.mgn.get_noticed.activities;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mgn.get_noticed.GetNoticedApplication;
import com.mgn.get_noticed.R;
import com.mgn.get_noticed.util.Constants;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY = 3000;
    private static final int COLOR_CHANGE_DELAY = 300;
    private static final int FADE_ANIMATION_DURATION = 300;


    private final Handler mHideHandler = new Handler();
    private final Handler mColorChangeHandler = new Handler();

    private View mContentView;
    private TextView mDisplayTextView;
    private LinearLayout mQuickAccessMenu;

    static int colorCounter = 0;

    private boolean mVisible;
    private boolean mRunning;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            toggleQuickAccessMenu(false);
        }
    };

    private final Runnable mChangeColorRunnable = new Runnable() {
        @Override
        public void run() {
            mContentView.setBackgroundColor(mColorValuesArray[colorCounter]);
            //mDisplayTextView.setTextColor(Util.getComplementaryColor(mColorValuesArray[colorCounter]));
            ++colorCounter;
            if (colorCounter >= mColorValuesArray.length)
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeValues();             // Initializes the value objects
        initializeViews();              // Initializes the view objects
        initializeAnimations();         // Initializes the animations for fade in/out of the quick access menu
        changeColor();                  // Starts the color changing
        //animateColorChanges(false);     //
    }

    private void initializeValues() {
        mVisible = false;
        mRunning = false;
        mColorValuesArray = getResources().getIntArray(R.array.rainbow_colors);
    }

    private void initializeViews() {
        mQuickAccessMenu = (LinearLayout) findViewById(R.id.fullscreen_content_controls);
        mDisplayTextView = (TextView) findViewById(R.id.display_text);

        mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleQuickAccessMenu(!mVisible);
            }
        });
        mContentView.setOnTouchListener(mDelayHideTouchListener);


        ImageView playImageView = (ImageView) findViewById(R.id.fullscreen_content_controls_play);
        playImageView.setTag(Constants.QUICK_ACCESS_MENU_PLAY);
        playImageView.setOnClickListener(this);

        ImageView textImageView = (ImageView) findViewById(R.id.fullscreen_content_controls_text);
        textImageView.setTag(Constants.QUICK_ACCESS_MENU_TEXT);
        textImageView.setOnClickListener(this);

        ImageView settingsImageView = (ImageView) findViewById(R.id.fullscreen_content_controls_settings);
        settingsImageView.setTag(Constants.QUICK_ACCESS_MENU_SETTINGS);
        settingsImageView.setOnClickListener(this);
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

    private void toggleQuickAccessMenu(boolean isVisible) {
        mVisible = isVisible;
        if (mVisible)
            mQuickAccessMenu.startAnimation(mFadeInAnimation);
        else
            mQuickAccessMenu.startAnimation(mFadeOutAnimation);

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
            mQuickAccessMenu.setVisibility(View.VISIBLE);
        } else if (animation.equals(mFadeOutAnimation)) {
            mQuickAccessMenu.setVisibility(View.INVISIBLE);
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
    protected void onResume() {
        super.onResume();
        // Hides the quick access menu
        hideUI();
        int colorsArray = GetNoticedApplication.getInstance().getSharedPreferences().getInt(Constants.SELECTED_COLOR_ARRAY, -100);
        if (colorsArray != -100)
            mColorValuesArray = getResources().getIntArray(colorsArray);
        mDisplayTextView.setText(GetNoticedApplication.getInstance().getSharedPreferences().getString(Constants.DISPLAY_TEXT, ""));
    }

    @Override
    public void onClick(View view) {
        if (view instanceof ImageView) {
            String tag = (String) view.getTag();
            if (tag != null)
                switch (tag) {
                    case Constants.QUICK_ACCESS_MENU_PLAY:
                        animateColorChanges(!mRunning, (ImageView) view);
                        return;
                    case Constants.QUICK_ACCESS_MENU_TEXT:
                        toggleDisplayText((ImageView) view);
                        return;
                    case Constants.QUICK_ACCESS_MENU_SETTINGS:
                        startActivity(new Intent(this, SettingsActivity.class));
                        return;
                    default:
                }
        }
    }

    private void animateColorChanges(boolean shouldAnimate, ImageView view) {
        if (!shouldAnimate)
            view.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        else {
            view.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
            changeColor();
        }
        mRunning = shouldAnimate;
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY);
        }
    }

    private void toggleDisplayText(ImageView view) {
        boolean isVisible = mDisplayTextView.getVisibility() == View.VISIBLE;
        if (isVisible) {
            view.setImageResource(R.drawable.ic_speaker_notes_white_24px);
            mDisplayTextView.setVisibility(View.INVISIBLE);
        } else {
            view.setImageResource(R.drawable.ic_speaker_notes_off_white_24px);
            mDisplayTextView.setVisibility(View.VISIBLE);

        }
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY);
        }
    }
}
