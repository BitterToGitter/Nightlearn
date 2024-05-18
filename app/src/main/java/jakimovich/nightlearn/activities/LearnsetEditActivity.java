package jakimovich.nightlearn.activities;

import static jakimovich.nightlearn.classes.UserService.myUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.caverock.androidsvg.SVGParseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.Quiz;
import jakimovich.nightlearn.classes.UserService;
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
        ivLearncardName.setOnClickListener(v -> AlertDialogHelper.showEditAlertDialog(this,"Update your learnset Name", null, "Update", null, name -> {learnsetToEdit.setName(name); tvLearncardName.setText("Learnset name: " + learnsetToEdit.getName());}));

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

        btnAddLearncard.setOnClickListener(v -> {learnsetToEdit.addLearncard(new Learncard("New Definition", "New Explanation")); adapter.notifyDataSetChanged(); });
    }

    private void save() {

        if(learnsetToEdit.getLearncards().size() == 0){
            Toast.makeText(this, "You need to add at least one learncard", Toast.LENGTH_SHORT).show();
            return;
        }

        setResult(RESULT_OK, new Intent());

        int learnsetPosition = getIntent().getExtras().getInt("positionInArray");

        if (learnsetPosition == myUser.getLearnsets().size()) {
            myUser.getLearnsets().add(learnsetToEdit);
        } else{
            myUser.getLearnsets().set(learnsetPosition, learnsetToEdit);
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.child("learnsets/" + learnsetPosition).setValue(learnsetToEdit);

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