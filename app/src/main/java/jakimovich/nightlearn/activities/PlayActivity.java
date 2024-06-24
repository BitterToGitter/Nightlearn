package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.Random;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.helpers.UserService;
import jakimovich.nightlearn.fragments.game.GameFragment;
import jakimovich.nightlearn.fragments.game.GameManualTypingFragment;
import jakimovich.nightlearn.fragments.game.GameMatchCardsFragment;
import jakimovich.nightlearn.fragments.game.GamePreviewFragment;
import jakimovich.nightlearn.fragments.game.GameResultsFragment;
import jakimovich.nightlearn.helpers.GeneralHelper;

public class PlayActivity extends AppCompatActivity {

    Learnset learnsetToPlay;
    Fragment fragment;
    int currentRound;
    int roundsInTotal;
    int rightAnswersNum;
    int cardsLearnedNum;
    int pointsEarned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        learnsetToPlay = GeneralHelper.getLearnsetFromIntent(getIntent());
        roundsInTotal = learnsetToPlay.getQuizSettings().getQuestionsAmount();

        currentRound = -1;

        rightAnswersNum = 0;
        cardsLearnedNum = 0;
        pointsEarned = 0;

        goNextRound();

    }


    public void goNextRound() {

        if(currentRound < 0){

            fragment = new GamePreviewFragment();

        } else if(currentRound < roundsInTotal) {

            int questionFragmentType = learnsetToPlay.getQuizSettings().getQuestionType() / 10;

            switch (questionFragmentType){
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
                    break;
                default:
                    fragment = new GameMatchCardsFragment();
                    break;

            }
        } else {
            fragment = new GameResultsFragment();
            learnsetToPlay.gamePlayed();

                UserService.updateLearnset(learnsetToPlay, getIntent().getExtras().getInt("positionInArray"));
                UserService.updatePoints(pointsEarned);
                UserService.updateGamesPlayed();
                UserService.updateCardsLearned();

        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.fragment_slide_in_right, R.anim.fragment_fade_out);

        transaction.replace(R.id.playFragmentContainer, fragment);
        transaction.commit();

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

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void addPoints(int points) {
        pointsEarned += points;
    }

    public void removePoints(int points) {
        pointsEarned -= points;
    }

    public void answeredRight(){
        rightAnswersNum++;
    }

    public void cardHasBeenLearned() {
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
            ((GameFragment) fragment).onGamePaused();
        } else {
            super.onBackPressed();
        }
    }

}

