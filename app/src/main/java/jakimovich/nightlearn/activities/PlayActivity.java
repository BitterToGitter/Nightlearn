package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.fragments.game.GameFragment;
import jakimovich.nightlearn.fragments.game.GameManualTypingFragment;
import jakimovich.nightlearn.fragments.game.GameMatchCardsFragment;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class PlayActivity extends AppCompatActivity {

    //TODO: To think of Quiz' inheritance ( Regular Quiz / Exam Quiz)

    TextView tvPreCountDown;
    Learnset learnsetToPlay;
    Fragment fragment;
    int currentRound;
    int roundsInTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        learnsetToPlay = MethodsHelper.getLearnsetFromIntent(getIntent());
        roundsInTotal = learnsetToPlay.getQuizSettings().getQuestionsAmount();

        currentRound = 0;

        tvPreCountDown = findViewById(R.id.tvPreCountDown);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvPreCountDown.setText((int) ((millisUntilFinished / 1000) + 1));
            }
            @Override
            public void onFinish() {
                tvPreCountDown.setVisibility(TextView.GONE);
                goNextRound();
            }
        }.start();

    }


    public void goNextRound() {

        if(currentRound < roundsInTotal) {

            int questionType = learnsetToPlay.getQuizSettings().getQuestionType();

            switch (questionType){
                case 1:
                    fragment = new GameMatchCardsFragment();
                    break;
                case 2:
                    fragment = new GameManualTypingFragment();
                    break;
                case 3:
                    if (new Random().nextFloat() < 0.6f) {
                        fragment = new GameMatchCardsFragment();
                    } else {
                        fragment = new GameManualTypingFragment();
                    }
            }

            currentRound++;

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.playFragmentContainer, fragment);
            fragmentTransaction.commit();

        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public Learnset getLearnsetToPlay() {
        return learnsetToPlay;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fragment instanceof GameFragment) {
            ((GameFragment) fragment).resetCountDownTimer();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof GameFragment) {
            ((GameFragment) fragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    //Todo: To make transaction animations
    //Todo: To make a game result screen

}

