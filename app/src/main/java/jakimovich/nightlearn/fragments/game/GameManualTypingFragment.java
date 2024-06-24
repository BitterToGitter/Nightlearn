package jakimovich.nightlearn.fragments.game;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.helpers.GeneralHelper;

public class GameManualTypingFragment extends GameFragment {

    TextView tvAnswerInput;
    EditText etAnswerInput;
    LinearLayout llRightAnswer;
    LinearLayout btnEnter;
    boolean definitionQuestion;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_manual_typing, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAnswerInput = view.findViewById(R.id.tvManualTypingAnswerInput);
        etAnswerInput = view.findViewById(R.id.etManualTypingAnswerInput);
        llRightAnswer = view.findViewById(R.id.llManualTypingRightAnswer);
        btnEnter = view.findViewById(R.id.llManualTypingBtnEnter);

        llRightAnswer.setVisibility(View.INVISIBLE);

        GeneralHelper.onKeyEnter(etAnswerInput, v -> checkAnswer(etAnswerInput.getText().toString().trim()));

        btnEnter.setOnClickListener(v -> checkAnswer(etAnswerInput.getText().toString().trim()));

    }

    @Override
    protected void onQuestionTypeDefinition(){
        super.onQuestionTypeDefinition();

        definitionQuestion = true;
        tvRightAnswer.setText("Right answer: " + questionLearncard.getExplanation());
    }
    @Override
    protected void onQuestionTypeExplanation(){
        super.onQuestionTypeExplanation();

        definitionQuestion = false;
        tvRightAnswer.setText("Right answer: " + questionLearncard.getDefinition());
    }

    private void checkAnswer(String answer) {
        if(answer.isEmpty()){
            Toast.makeText(getActivity(), "Type an answer", Toast.LENGTH_SHORT).show();
            return;
        }
        if (definitionQuestion){

            if (answer.equals(questionLearncard.getExplanation())){
                onRightAnswer();
            } else {
                onWrongAnswer();
            }

        } else {

            if (answer.equals(questionLearncard.getDefinition())){
                onRightAnswer();
            } else {
                onWrongAnswer();
            }

        }
    }

    @Override
    protected void onRightAnswer(){
        super.onRightAnswer();

        etAnswerInput.setVisibility(View.GONE);
        tvAnswerInput.setText("You're right: " + etAnswerInput.getText().toString().trim());
        tvAnswerInput.setTextColor(getResources().getColor(R.color.green));

        btnEnter.setClickable(false);


    }

    @Override
    protected void onWrongAnswer(){
        super.onWrongAnswer();

        etAnswerInput.setVisibility(View.GONE);
        tvAnswerInput.setText("Your answer: " + etAnswerInput.getText().toString().trim());
        tvAnswerInput.setTextColor(getResources().getColor(R.color.red));

        llRightAnswer.setVisibility(View.VISIBLE);

        btnEnter.setClickable(false);

    };

    @Override
    protected void onSkipped() {
        super.onSkipped();

        etAnswerInput.setVisibility(View.GONE);
        tvAnswerInput.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tvAnswerInput.setText("The question have been skipped");

        llRightAnswer.setVisibility(View.VISIBLE);

        btnEnter.setClickable(false);

    }

    @Override
    protected void onMissedAnswer() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        if(!etAnswerInput.getText().toString().trim().isEmpty()){
            checkAnswer(etAnswerInput.getText().toString().trim());
            tvTime.setText("0 sec.");
            return;
        }

        super.onMissedAnswer();

        etAnswerInput.setVisibility(View.GONE);
        tvAnswerInput.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tvAnswerInput.setText("The answer have been missed");

        llRightAnswer.setVisibility(View.VISIBLE);

        btnEnter.setClickable(false);

    }
}
