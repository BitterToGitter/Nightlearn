package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.CustomSeekBar;
import jakimovich.nightlearn.helpers.CustomSeekBarSec;
import jakimovich.nightlearn.helpers.LearncardAdapter;

public class QuizSettingsActivity extends AppCompatActivity {

    CustomSeekBar sbNumberOfQuestions, sbNumCountedAsLearned;
    CustomSeekBarSec sbTimeForAnswering;
    Switch swCardsMatch, swManualTyping;
    ImageView ivInfoCardsMatch, ivInfoManualTyping;
    Button btnSave, btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_settings);

        sbNumberOfQuestions = findViewById(R.id.sbNumberOfQuestions);
        sbNumberOfQuestions.setProgress(getIntent().getExtras().getInt("numberOfQuestions"));

        sbNumCountedAsLearned = findViewById(R.id.sbNumCountedAsLearned);
        sbNumCountedAsLearned.setProgress(getIntent().getExtras().getInt("numCountedAsLearned"));

        sbTimeForAnswering = findViewById(R.id.sbTimeForAnswering);
        sbTimeForAnswering.setProgress(getIntent().getExtras().getInt("timeForAnswering"));

        swCardsMatch = findViewById(R.id.swCardsMatch);
        swManualTyping = findViewById(R.id.swManualTyping);

        int questionType = getIntent().getExtras().getInt("questionType");
        if(questionType == 1){
            swCardsMatch.setChecked(true);
            swManualTyping.setChecked(false);
        } else if (questionType == 2){
            swCardsMatch.setChecked(false);
            swManualTyping.setChecked(true);
        } else if (questionType == 3){
            swCardsMatch.setChecked(true);
            swManualTyping.setChecked(true);
        }

        ivInfoCardsMatch = findViewById(R.id.ivInfoCardsMatch);
        ivInfoCardsMatch.setOnClickListener(v -> showPopupWindow(v, "This option allows to to match the right answer among 4 random cards from your learnset."));

        ivInfoManualTyping = findViewById(R.id.ivInfoManualTyping);
        ivInfoManualTyping.setOnClickListener(v -> showPopupWindow(v, "This option allows to type the answer manually."));
        //Todo: maybe popUpWindow

        btnSave = findViewById(R.id.btnQuizSettingsSave);
        btnCancel = findViewById(R.id.btnQuizSettingsCancel);


        btnSave.setOnClickListener(v -> saveSettings());
        btnCancel.setOnClickListener(v -> finish());

    }

    private void saveSettings() {

        boolean cardsMatch = swCardsMatch.isChecked();

        boolean manualTyping = swManualTyping.isChecked();

        int questionType;

        if (cardsMatch && !manualTyping) {
            questionType = 1;
        } else if (manualTyping && !cardsMatch){
            questionType = 2;
        } else if (cardsMatch && manualTyping) {
            questionType = 3;
        } else {
            Toast.makeText(this, "At least one question type has to be chosen", Toast.LENGTH_SHORT).show();;
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("numberOfQuestions", sbNumberOfQuestions.getProgress());
        intent.putExtra("numCountedAsLearned", sbNumCountedAsLearned.getProgress());
        intent.putExtra("timeForAnswering", sbTimeForAnswering.getProgress());
        intent.putExtra("questionType", questionType);

        setResult(RESULT_OK, intent);
        finish();

    }

    private void showPopupWindow(View view, String message) {

        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_menu_learncard_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tvMessage = popupView.findViewById(R.id.tvLearncardMenuDelete);
        tvMessage.setGravity(Gravity.CENTER);
        tvMessage.setText(message);

        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        popupWindow.showAsDropDown(view);
    }


}