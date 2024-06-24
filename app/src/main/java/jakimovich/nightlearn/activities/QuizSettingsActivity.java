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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.helpers.CustomSeekBar;
import jakimovich.nightlearn.helpers.CustomSeekBarSec;

public class QuizSettingsActivity extends AppCompatActivity {

    CustomSeekBar sbNumberOfQuestions, sbNumCountedAsLearned;
    CustomSeekBarSec sbTimeForAnswering;
    Switch swCardsMatch, swManualTyping;
    ImageView ivInfoCardsMatch, ivInfoManualTyping;
    LinearLayout llSbNumCountedAsLearned;
    Button btnSave, btnCancel;
    TextView btnDefOnly, btnDefAndExpl, btnExplOnly;
    int questionFragmentType;
    int questionType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_settings);

        sbNumberOfQuestions = findViewById(R.id.sbNumberOfQuestions);
        sbNumberOfQuestions.setProgress(getIntent().getExtras().getInt("numberOfQuestions"));

        sbNumCountedAsLearned = findViewById(R.id.sbNumCountedAsLearned);
        sbNumCountedAsLearned.setProgress(getIntent().getExtras().getInt("numCountedAsLearned"));

        llSbNumCountedAsLearned = findViewById(R.id.llSbNumCountedAsLearned);
        if (getIntent().getExtras().getBoolean("learnsetWasPlayedOnce")){
            sbNumCountedAsLearned.setEnabled(false);
            llSbNumCountedAsLearned.setOnClickListener(v -> Toast.makeText(this, "This setting can be set for new learnsets only", Toast.LENGTH_SHORT).show());
        }

        sbTimeForAnswering = findViewById(R.id.sbTimeForAnswering);
        sbTimeForAnswering.setProgress(getIntent().getExtras().getInt("timeForAnswering"));

        swCardsMatch = findViewById(R.id.swCardsMatch);
        swManualTyping = findViewById(R.id.swManualTyping);

        questionFragmentType = getIntent().getExtras().getInt("questionType") / 10;
        questionType = getIntent().getExtras().getInt("questionType") % 10;

        switch (questionFragmentType){
            case 1:
                swCardsMatch.setChecked(true);
                swManualTyping.setChecked(false);
                break;
            case 2:
                swCardsMatch.setChecked(false);
                swManualTyping.setChecked(true);
                break;
            case 3:
                swCardsMatch.setChecked(true);
                swManualTyping.setChecked(true);
                break;
                default:
                    swCardsMatch.setChecked(false);
                    swManualTyping.setChecked(false);
                    break;
        }

        btnDefOnly = findViewById(R.id.tvDefinitionOnly);
        btnDefOnly.setOnClickListener(v -> onDefOnly());

        btnExplOnly = findViewById(R.id.tvExplanationOnly);
        btnExplOnly.setOnClickListener(v -> onExplOnly());

        btnDefAndExpl = findViewById(R.id.tvRandomChance);
        btnDefAndExpl.setOnClickListener(v -> onDefAndExpl());

        switch (questionType){
            case 1:
                onDefOnly();
                break;
            case 2:
                onExplOnly();
                break;
            case 3:
                onDefAndExpl();
                break;
            default:
                onExplOnly();
                break;
        }

        ivInfoCardsMatch = findViewById(R.id.ivInfoCardsMatch);
        ivInfoCardsMatch.setOnClickListener(v -> showPopupWindow(v, "This option allows to to match the right answer among 4 random cards from your learnset."));

        ivInfoManualTyping = findViewById(R.id.ivInfoManualTyping);
        ivInfoManualTyping.setOnClickListener(v -> showPopupWindow(v, "This option allows to type the answer manually."));

        btnSave = findViewById(R.id.btnQuizSettingsSave);
        btnCancel = findViewById(R.id.btnQuizSettingsCancel);


        btnSave.setOnClickListener(v -> saveSettings());
        btnCancel.setOnClickListener(v -> finish());

    }

    private void onDefOnly() {

        btnDefOnly.setBackgroundResource(R.drawable.btn_background_selected);
        btnDefOnly.setTextColor(getResources().getColor(R.color.brightGray));

        btnExplOnly.setBackgroundResource(R.drawable.btn_background);
        btnExplOnly.setTextColor(getResources().getColor(R.color.dark_gray_focused));

        btnDefAndExpl.setBackgroundResource(R.drawable.btn_background);
        btnDefAndExpl.setTextColor(getResources().getColor(R.color.dark_gray_focused));

        questionType = 1;

    }

    private void onExplOnly() {

        btnExplOnly.setBackgroundResource(R.drawable.btn_background_selected);
        btnExplOnly.setTextColor(getResources().getColor(R.color.brightGray));

        btnDefOnly.setBackgroundResource(R.drawable.btn_background);
        btnDefOnly.setTextColor(getResources().getColor(R.color.dark_gray_focused));

        btnDefAndExpl.setBackgroundResource(R.drawable.btn_background);
        btnDefAndExpl.setTextColor(getResources().getColor(R.color.dark_gray_focused));

        questionType = 2;

    }

    private void onDefAndExpl() {

        btnDefAndExpl.setBackgroundResource(R.drawable.btn_background_selected);
        btnDefAndExpl.setTextColor(getResources().getColor(R.color.brightGray));

        btnDefOnly.setBackgroundResource(R.drawable.btn_background);
        btnDefOnly.setTextColor(getResources().getColor(R.color.dark_gray_focused));

        btnExplOnly.setBackgroundResource(R.drawable.btn_background);
        btnExplOnly.setTextColor(getResources().getColor(R.color.dark_gray_focused));

        questionType = 3;

    }

    private void saveSettings() {

        boolean cardsMatch = swCardsMatch.isChecked();

        boolean manualTyping = swManualTyping.isChecked();

        if (cardsMatch && !manualTyping) {
            questionFragmentType = 1;
        } else if (manualTyping && !cardsMatch){
            questionFragmentType = 2;
        } else if (cardsMatch && manualTyping) {
            questionFragmentType = 3;
        } else {
            Toast.makeText(this, "At least one question type has to be chosen", Toast.LENGTH_SHORT).show();;
            return;
        }

        //Todo: to copy again
        Intent intent = new Intent();
        intent.putExtra("numberOfQuestions", sbNumberOfQuestions.getProgress());
        intent.putExtra("numCountedAsLearned", sbNumCountedAsLearned.getProgress());
        intent.putExtra("timeForAnswering", sbTimeForAnswering.getProgress());
        intent.putExtra("questionType", questionFragmentType * 10 + questionType);

        setResult(RESULT_OK, intent);
        finish();

    }

    private void showPopupWindow(View view, String message) {

        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_menu_learncard_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tvMessage = popupView.findViewById(R.id.tvLearncardPopupLayoutText);
        tvMessage.setGravity(Gravity.CENTER);
        tvMessage.setText(message);

        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        popupWindow.showAsDropDown(view);
    }


}