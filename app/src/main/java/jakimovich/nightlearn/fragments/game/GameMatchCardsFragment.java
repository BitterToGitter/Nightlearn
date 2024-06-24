package jakimovich.nightlearn.fragments.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learncard;

public class GameMatchCardsFragment extends GameFragment {


    TextView btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    TextView[] btnAnswers = {btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4};
    TextView btnRightAnswer, btnWrongAnswer;
    String rightAnswerText;
    ArrayList<String> wrongAnswers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_match_cards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setQuestion(view);
        adjustTextSize(btnAnswers);
        setSameBtnsHeight(btnAnswers);
    }


    private void setQuestion(View view){

        int rightAnswerPositionIndex = new Random().nextInt(4);

        for(int i = 0; i < 4; i++){

            btnAnswers[i] = view.findViewById(getResources().getIdentifier("btnMatchCardsAnswer" + (i + 1), "id", getActivity().getPackageName()));

            setBtnWidth(btnAnswers[i]);

            if(i == rightAnswerPositionIndex){

                btnAnswers[i].setText(rightAnswerText);
                btnRightAnswer = btnAnswers[i];
                btnRightAnswer.setOnClickListener(v -> onRightAnswer());

            } else {

                int randomWrongAnswerIndex = new Random().nextInt(wrongAnswers.size());

                btnAnswers[i].setText(wrongAnswers.get(randomWrongAnswerIndex));

                if (wrongAnswers.size() > 1) {
                    wrongAnswers.remove(randomWrongAnswerIndex);
                }

                int finalI = i;
                btnAnswers[i].setOnClickListener(v -> {
                    btnWrongAnswer = btnAnswers[finalI]; onWrongAnswer(); });
            }
        }

    }

    @Override
    protected void onQuestionTypeDefinition() {
        super.onQuestionTypeDefinition();

        rightAnswerText = questionLearncard.getExplanation();

        wrongAnswers = new ArrayList<>();

        for (Learncard wrongLearncard : learnsetToPlay.getLearncards()){
            if (!wrongLearncard.getDefinition().equals(questionLearncard.getDefinition()) || learnsetToPlay.getLearncards().size() == 1){
                wrongAnswers.add(wrongLearncard.getExplanation());
            }
        }

    }

    @Override
    protected void onQuestionTypeExplanation() {
        super.onQuestionTypeExplanation();

        rightAnswerText = questionLearncard.getDefinition();

        wrongAnswers = new ArrayList<>();

        for (Learncard wrongLearncard : learnsetToPlay.getLearncards()){
            if (!wrongLearncard.getDefinition().equals(questionLearncard.getDefinition()) || learnsetToPlay.getLearncards().size() == 1){
                wrongAnswers.add(wrongLearncard.getDefinition());
            }
        }
    }

    @Override
    protected void onSkipped() {
        super.onSkipped();
        allButtonsUnclickable();
        btnRightAnswer.setTextColor(getResources().getColor(R.color.green));
    }

    @Override
    protected void onMissedAnswer() {

        super.onMissedAnswer();
        allButtonsUnclickable();
        btnRightAnswer.setTextColor(getResources().getColor(R.color.green));

    }

    @Override
    protected void onWrongAnswer() {
        super.onWrongAnswer();

        btnWrongAnswer.setTextColor(getResources().getColor(R.color.red));
        btnRightAnswer.setTextColor(getResources().getColor(R.color.green));
        allButtonsUnclickable();
    }

    @Override
    protected void onRightAnswer() {
        super.onRightAnswer();

        btnRightAnswer.setTextColor(getResources().getColor(R.color.green));
        allButtonsUnclickable();

    }

    private void allButtonsUnclickable(){
        for (TextView btnAnswer : btnAnswers){
            btnAnswer.setClickable(false);
            btnAnswer.setFocusable(false);
        }
    }

    private void setBtnWidth(TextView btnAnswer){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        btnAnswer.setWidth(displayMetrics.widthPixels * 9/20);
    }

    private void adjustTextSize(final TextView[] btnAnswers) {
        // Post a runnable to be executed after the layout pass
        btnAnswers[0].post(new Runnable() {
            @Override
            public void run() {
                int maxTextLines = 0;

                // Determine the maximum line count among the TextViews
                for (TextView btnAnswer : btnAnswers) {
                    int lineCount = btnAnswer.getLineCount();
                    if (lineCount > maxTextLines) {
                        maxTextLines = lineCount;
                    }
                }

                // Adjust text size based on the maximum line count
                for (TextView btnAnswer : btnAnswers) {
                    if (btnAnswer.getLineCount() > 2){
                        btnAnswer.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
                    }

                    if (maxTextLines > 3 && maxTextLines <= 6) {
                        btnAnswer.setTextSize(18);
                    }
                    if (maxTextLines > 6) {
                        btnAnswer.setTextSize(16);
                    }
                }
            }
        });
    }
    private void setSameBtnsHeight(TextView[] btnAnswers) {

        btnAnswers[0].post(new Runnable() {
            @Override
            public void run() {
                if (btnAnswers[0].getLineCount() > btnAnswers[1].getLineCount()) {
                    btnAnswers[1].setHeight(btnAnswers[0].getHeight());
                    return;
                }

                if (btnAnswers[1].getLineCount() > btnAnswers[0].getLineCount()) {
                    btnAnswers[0].setHeight(btnAnswers[1].getHeight());
                    return;
                }

                if (btnAnswers[2].getLineCount() > btnAnswers[3].getLineCount()) {
                    btnAnswers[3].setHeight(btnAnswers[2].getHeight());
                    return;
                }
                if (btnAnswers[3].getLineCount() > btnAnswers[2].getLineCount()) {
                    btnAnswers[2].setHeight(btnAnswers[3].getHeight());
                    return;
                }
            }
        });
    }
}
