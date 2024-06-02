package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.fragments.game.GameFragment;
import jakimovich.nightlearn.fragments.game.GameManualTypingFragment;
import jakimovich.nightlearn.fragments.game.GameMatchCardsFragment;
import jakimovich.nightlearn.fragments.game.GamePreviewFragment;
import jakimovich.nightlearn.fragments.game.GameResultsFragment;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class PlayActivity extends AppCompatActivity {

    //TODO: To think of Quiz' inheritance ( Regular Quiz / Exam Quiz)

    Learnset learnsetToPlay;
    Fragment fragment;
    int currentRound;
    int roundsInTotal;
    int rightAnswersNum;
    int cardsLearnedNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        learnsetToPlay = MethodsHelper.getLearnsetFromIntent(getIntent());
        roundsInTotal = learnsetToPlay.getQuizSettings().getQuestionsAmount();

        currentRound = -1;

        rightAnswersNum = 0;
        cardsLearnedNum = 0;

        goNextRound();

    }


    public void goNextRound() {

        if(currentRound < 0){

            fragment = new GamePreviewFragment();

        } else if(currentRound < roundsInTotal) {

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
        } else {
            fragment = new GameResultsFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.playFragmentContainer, fragment).commit();
        currentRound++;

    }

    public int getCurrentRound() {
        return currentRound;
    }

    public Learnset getLearnsetToPlay() {
        return learnsetToPlay;
    }

    public int getRightAnswersNum() {
        return rightAnswersNum;
    }

    public int getCardsLearnedNum() {
        return cardsLearnedNum;
    }

    public void answeredRight(){
        rightAnswersNum++;
    }

    public void cardLearned() {
        cardsLearnedNum++;
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

}

