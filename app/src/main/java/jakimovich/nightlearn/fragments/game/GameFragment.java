package jakimovich.nightlearn.fragments.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.PlayActivity;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.helpers.AlertDialogHelper;

public abstract class GameFragment extends Fragment {

    TextView tvRound, tvSubTitle;
    TextView tvTime;
    TextView tvQuestion;

    TextView tvSkip;
    LinearLayout llSkip, llPauseGame;

    Learnset learnsetToPlay;
    Learncard questionLearncard;

    CountDownTimer countDownTimer;
    long timeLeftInMillis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timeLeftInMillis = 0;

        learnsetToPlay = ((PlayActivity) getActivity()).getLearnsetToPlay();

        if (this instanceof GameMatchCardsFragment){
            tvRound = view.findViewById(R.id.tvMatchCardsRound);
            tvSubTitle = view.findViewById(R.id.tvMatchCardsSubTitle);
            tvTime = view.findViewById(R.id.tvMatchCardsTime);
            tvQuestion = view.findViewById(R.id.tvMatchCardsQuestionExplanation);
            llSkip = view.findViewById(R.id.llMatchCardsBtnSkip);
            llPauseGame = view.findViewById(R.id.llMatchCardsBtnPause);
            tvSkip = view.findViewById(R.id.tvMatchCardsBtnSkip);
        }
        if (this instanceof GameManualTypingFragment){
            tvRound = view.findViewById(R.id.tvManualTypingRound);
            //Todo: to fill in the rest of the views for GameManualTypingFragment
        }

        llSkip.setOnClickListener(v -> onSkipped());
        llPauseGame.setOnClickListener(v -> onGamePaused());

        tvRound.setText("Round " + ((PlayActivity) getActivity()).getCurrentRound() + "/" + learnsetToPlay.getQuizSettings().getQuestionsAmount());

        startCountDownTimer(tvTime);

    }

    public void startCountDownTimer(TextView timeUpdate) {

        long timeForAnswering;

        if (timeLeftInMillis != 0) {
            timeForAnswering = timeLeftInMillis;
        } else {
            timeForAnswering = learnsetToPlay.getQuizSettings().getAnswerTimeSec() * 1000;
        }
        countDownTimer = new CountDownTimer(timeForAnswering, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                timeUpdate.setText((millisUntilFinished / 1000) + 1 + " sec.");
            }
            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                onMissedAnswer();
            }

        }.start();
    }

    public void resetCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            timeLeftInMillis = 0;
        }
    }

    protected void onSkipped(){

        resetCountDownTimer();
        tvSubTitle.setText("Well, it's your choice...");
        tvSkip.setText("Next round");
        llSkip.setOnClickListener(v -> ((PlayActivity) getActivity()).goNextRound());

        questionLearncard.onSeen();
        ((PlayActivity) getActivity()).getLearnsetToPlay().updateLearncard(questionLearncard);

    }

    protected void onMissedAnswer() {

        resetCountDownTimer();
        tvSubTitle.setText("Time's up!");
        tvTime.setText("0 sec.");
        tvSkip.setText("Next round");
        llSkip.setOnClickListener(v -> ((PlayActivity) getActivity()).goNextRound());
        questionLearncard.onSeen();
        ((PlayActivity) getActivity()).getLearnsetToPlay().updateLearncard(questionLearncard);

    }


    protected void onWrongAnswer(){

        resetCountDownTimer();
        tvSubTitle.setText(answerCommentsOptions(false));
        tvSkip.setText("Next round");
        llSkip.setOnClickListener(v -> ((PlayActivity) getActivity()).goNextRound());
        questionLearncard.onSeen();
        ((PlayActivity) getActivity()).getLearnsetToPlay().updateLearncard(questionLearncard);


    }

    protected void onRightAnswer(){

        resetCountDownTimer();
        tvSubTitle.setText(answerCommentsOptions(true));
        tvSkip.setText("Next round");
        llSkip.setOnClickListener(v -> ((PlayActivity) getActivity()).goNextRound());

        ((PlayActivity) getActivity()).answeredRight();
        if (questionLearncard.checkLearned(learnsetToPlay.getQuizSettings().getRightAnswersNumToBeLearned())){
            ((PlayActivity) getActivity()).cardHasBeenLearned();
        }

        questionLearncard.onAnsweredRight();
        questionLearncard.onSeen();
        questionLearncard.checkLearned(learnsetToPlay.getQuizSettings().getRightAnswersNumToBeLearned());
        ((PlayActivity) getActivity()).getLearnsetToPlay().updateLearncard(questionLearncard);

    }


    private String answerCommentsOptions (boolean rightAnswer){

        ArrayList<String> answerComments = new ArrayList<>();

        if (rightAnswer){
            answerComments.add("Great job!");
            answerComments.add("You're on fire!");
            answerComments.add("You're doing great!");
            answerComments.add("You're on a roll!");
            answerComments.add("You're on a winning streak!");
            answerComments.add("Well done!");
            answerComments.add("You're on the right track!");
            answerComments.add("All correct!");
            answerComments.add("Yeah, you got it!");
            answerComments.add("Answered correctly!");
        }
        else{
            answerComments.add("Oops! Didn't go well this time.");
            answerComments.add("Not this time...");
            answerComments.add("Try again, you can do it!");
            answerComments.add("Not quite...");
            answerComments.add("Not really correct...");
            answerComments.add("Don't give up!");
            answerComments.add("Keep trying though!");
            answerComments.add("Next time will be yours!");
            answerComments.add("Little mistake");
            answerComments.add("It happens...");
        }

        return answerComments.get(new Random().nextInt(answerComments.size()));

    }

    public void onGamePaused(){
        if (timeLeftInMillis != 0){
            countDownTimer.cancel();
        }
        AlertDialogHelper.showOptionsAlertDialog(getActivity(), "The game is paused \nChoose your next action", "Stop and exit the game", "Continue playing", v -> getActivity().finish(), v -> { if(timeLeftInMillis != 0){startCountDownTimer((tvTime));}});
    }
}