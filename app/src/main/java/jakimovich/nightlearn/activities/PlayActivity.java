package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.fragments.GameManualTypingFragment;
import jakimovich.nightlearn.fragments.GameMatchCardsFragment;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class PlayActivity extends AppCompatActivity {

    //TODO: To think of Quiz' inheritance ( Regular Quiz / Exam Quiz)

    Learnset learnsetToPlay;

    int roundsInTotal;
    int currentRound = 0;

    int timeForAnswering;

    Button btnNext;

    Fragment fragment;

    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        learnsetToPlay = MethodsHelper.getLearnsetFromIntent(getIntent());
        roundsInTotal = learnsetToPlay.getQuizSettings().getQuestionsAmount();
        timeForAnswering = learnsetToPlay.getQuizSettings().getAnswerTimeSec() * 1000;


        btnNext = findViewById(R.id.playBtnNext);

        btnNext.setOnClickListener(v ->  goNextAction());

    }


    private void showNextFragment() {

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
                    if (getRandomChance()) {
                        fragment = new GameMatchCardsFragment();
                    } else {
                        fragment = new GameManualTypingFragment();
                    }
            }

            currentRound++;

            Bundle bundle = new Bundle();
            bundle.putInt("round", currentRound);
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.playFragmentContainer, fragment);
            fragmentTransaction.commit();

        }
    }

    private boolean getRandomChance(){

        Random random = new Random();
        float probability = 0.7f;

        return random.nextFloat() < probability;

    }

    public void startCountDownTimer(TextView timeUpdate) {
        countDownTimer = new CountDownTimer(timeForAnswering, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeUpdate.setText((millisUntilFinished / 1000) + " sec");
            }

            @Override
            public void onFinish() {
                showNextFragment();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Clean up to avoid memory leaks
        }
    }

    private void goNextAction(){
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        showNextFragment();
    }


}

