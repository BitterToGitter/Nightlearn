package jakimovich.nightlearn.fragments.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learncard;

public class GameMatchCardsFragment extends GameFragment {


    TextView btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    TextView[] btnAnswers = {btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4};

    TextView rightAnswer, wrongAnswer;

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


        questionLearncard = learnsetToPlay.getRandomCard(true);

        setQuestion(view);

    }

    private void setQuestion(View view){

        tvQuestion.setText(questionLearncard.getExplanation());

        int rightAnswerPosition = new Random().nextInt(4);

        ArrayList<String> wrongAnswers = new ArrayList<>();
        for (Learncard wrongLearncard : learnsetToPlay.getLearncards()){
            if (!wrongLearncard.getDefinition().equals(questionLearncard.getDefinition()) || learnsetToPlay.getLearncards().size() == 1){
                wrongAnswers.add(wrongLearncard.getDefinition());
            }
        }


        for(int i = 0; i < 4; i++){

            btnAnswers[i] = view.findViewById(getResources().getIdentifier("btnMatchCardsAnswer" + (i + 1), "id", getActivity().getPackageName()));

            if(i == rightAnswerPosition){

                btnAnswers[i].setText(questionLearncard.getDefinition());
                rightAnswer = btnAnswers[i];
                rightAnswer.setOnClickListener(v -> onRightAnswer());

            } else {


                int randomWrongAnswerIndex = new Random().nextInt(wrongAnswers.size());
                btnAnswers[i].setText(wrongAnswers.get(randomWrongAnswerIndex));

                if (wrongAnswers.size() > 1) {
                    wrongAnswers.remove(randomWrongAnswerIndex);
                }

                int finalI = i;
                btnAnswers[i].setOnClickListener(v -> {wrongAnswer = btnAnswers[finalI]; onWrongAnswer(); });
            }
        }

    }

    @Override
    protected void onMissedAnswer() {
        super.onMissedAnswer();
        allButtonsUnclickable();
        rightAnswer.setTextColor(getResources().getColor(R.color.green));

    }

    @Override
    protected void onWrongAnswer() {
        super.onWrongAnswer();

        wrongAnswer.setTextColor(getResources().getColor(R.color.red));
        rightAnswer.setTextColor(getResources().getColor(R.color.green));
        allButtonsUnclickable();
    }

    @Override
    protected void onRightAnswer() {
        super.onRightAnswer();

        rightAnswer.setTextColor(getResources().getColor(R.color.green));
        allButtonsUnclickable();

    }


    private void allButtonsUnclickable(){
        for (TextView btnAnswer : btnAnswers){
            btnAnswer.setClickable(false);
            btnAnswer.setFocusable(false);
        }
    }
}
