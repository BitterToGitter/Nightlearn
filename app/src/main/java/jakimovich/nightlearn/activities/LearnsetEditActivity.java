package jakimovich.nightlearn.activities;

import static jakimovich.nightlearn.helpers.UserService.myUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.Quiz;
import jakimovich.nightlearn.helpers.UserService;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.LearncardAdapter;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class LearnsetEditActivity extends AppCompatActivity {

    TextView tvLearncardName;
    ImageView ivLearncardName, btnAddLearncard, ivEditLearnsetName;
    Learnset learnsetToEdit;
    RecyclerView learncardsRV;
    Button btnSave, btnCancel, btnQuizSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnset_edit);

        learnsetToEdit = MethodsHelper.getLearnsetFromIntent(getIntent());

        tvLearncardName = findViewById(R.id.tvEditLearnsetName);
        tvLearncardName.setText("Learnset name: " + learnsetToEdit.getName());

        ivLearncardName = findViewById(R.id.ivEditLearnsetName);
        ivLearncardName.setOnClickListener(v -> AlertDialogHelper.showEditAlertDialog(this,"Update your learnset Name", null, "Update", null, learnsetToEdit.getName(), name -> {learnsetToEdit.setName(name); tvLearncardName.setText("Learnset name: " + learnsetToEdit.getName());}));

        btnAddLearncard = findViewById(R.id.ivLearnsetEditBtnAddLearncard);
        btnAddLearncard.setImageDrawable(MethodsHelper.convertSvgToDrawable(this,R.raw.ic_btn_add_learncard));

        ivEditLearnsetName = findViewById(R.id.ivEditLearnsetName);
        ivEditLearnsetName.setImageDrawable(MethodsHelper.convertSvgToDrawable(this,R.raw.ic_edit));

        btnCancel = findViewById(R.id.btnLearnsetEditCancel);
        btnCancel.setOnClickListener(v -> finish());

        btnSave = findViewById(R.id.btnLearnsetEditSave);
        btnSave.setOnClickListener(v -> save());

        btnQuizSettings = findViewById(R.id.btnLearnsetEditQuizSettings);
        btnQuizSettings.setOnClickListener(v -> quizSettings());

        learncardsRV = findViewById(R.id.learncardsRecycleView);
        learncardsRV.setLayoutManager(new LinearLayoutManager(this));

        LearncardAdapter adapter = new LearncardAdapter(this, learnsetToEdit.getLearncards());

        learncardsRV.setAdapter(adapter);

        btnAddLearncard.setOnClickListener(v -> {learnsetToEdit.addLearncard(new Learncard("New Definition", "New Explanation")); adapter.notifyDataSetChanged(); learncardsRV.getLayoutManager().scrollToPosition(learnsetToEdit.getLearncards().size() - 1); });
    }

    private void save() {

        if(learnsetToEdit.getLearncards().size() < 2){
            Toast.makeText(this, "You need to add at least two learncards", Toast.LENGTH_SHORT).show();
            return;
        }

        setResult(RESULT_OK, new Intent());

        int learnsetPosition = getIntent().getExtras().getInt("positionInArray");

        if (learnsetPosition == myUser.getLearnsets().size()) {
            myUser.getLearnsets().add(learnsetToEdit);
        } else{
            myUser.getLearnsets().set(learnsetPosition, learnsetToEdit);
        }

        UserService.updateLearnset(learnsetToEdit, learnsetPosition);

        Toast.makeText(this, "Learnset saved", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void quizSettings(){

        Intent intent = new Intent(this, QuizSettingsActivity.class);
        intent.putExtra("numberOfQuestions", learnsetToEdit.getQuizSettings().getQuestionsAmount());
        intent.putExtra("timeForAnswering", learnsetToEdit.getQuizSettings().getAnswerTimeSec());
        intent.putExtra("numCountedAsLearned", learnsetToEdit.getQuizSettings().getRightAnswersNumToBeLearned());
        intent.putExtra("questionType", learnsetToEdit.getQuizSettings().getQuestionType());

        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Quiz quiz = new Quiz(data.getExtras().getInt("numberOfQuestions"), data.getExtras().getInt("timeForAnswering"), data.getExtras().getInt("numCountedAsLearned"), data.getExtras().getInt("questionType"));
                learnsetToEdit.setQuizSettings(quiz);
            }
        }

    }
}