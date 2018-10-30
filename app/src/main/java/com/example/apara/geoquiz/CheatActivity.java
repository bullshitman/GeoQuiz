package com.example.apara.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    public static final String EXTRA_ANSWER_IS_TRUE = "geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "geoquiz.answer_shown";
    public static final String CHEAT_TIMES = "cheat_times";
    private static int mCheatCount;
    private boolean mCheatEnabled = false;
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private TextView mApiLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mApiLevel = findViewById(R.id.api_level);

        if (savedInstanceState != null){
            setAnswerShownResult(mCheatEnabled = savedInstanceState.getBoolean("cheat_enabled"));
            mAnswerTextView.setText(R.string.watching_you);
        }
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheatEnabled = true;
                if (mCheatCount < 2) {
                    if (mAnswerIsTrue) {
                        mAnswerTextView.setText(R.string.true_button);
                    } else {
                        mAnswerTextView.setText(R.string.false_button);
                    }
                    mCheatCount++;
                    setAnswerShownResult(true);
                }else {
                    mCheatEnabled = false;
                    mAnswerTextView.setText(R.string.cheating_is_over);
                    setAnswerShownResult(false);
                }
                String string = "Api level: " + Build.VERSION.SDK_INT;
                mApiLevel.setText(string);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.VISIBLE);
                        }
                    });
                    anim.start();
                }else{
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                    }
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        data.putExtra(CHEAT_TIMES, mCheatCount);
        setResult(RESULT_OK, data);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        intent.putExtra(CHEAT_TIMES, mCheatCount);
        return intent;
    }
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("cheat_enabled", mCheatEnabled);
        savedInstanceState.putInt("cheat_times", mCheatCount);
    }
}
