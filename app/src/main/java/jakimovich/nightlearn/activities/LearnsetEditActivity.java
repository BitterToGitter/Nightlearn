package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVGParseException;

import java.util.ArrayList;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.Quiz;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.LearncardAdapter;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class LearnsetEditActivity extends AppCompatActivity {

    TextView tvLearncardName;
    ImageView ivLearncardName, btnAddLearncard;
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
        try {
            btnAddLearncard.setImageDrawable(MethodsHelper.convertSvgToDrawable(this,R.raw.ic_btn_add_learncard));
        } catch (SVGParseException e) {
            throw new RuntimeException(e);
        }

        btnCancel = findViewById(R.id.btnLearnsetEditCancel);
        btnCancel.setOnClickListener(v -> finish() );

        btnSave = findViewById(R.id.btnLearnsetEditSave);
        btnSave.setOnClickListener(v -> save());

        btnQuizSettings = findViewById(R.id.btnLearnsetEditQuizSettings);
        btnQuizSettings.setOnClickListener(v -> startActivity(new Intent(this, QuizSettingsActivity.class)));

        learncardsRV = findViewById(R.id.learncardsRecycleView);
        learncardsRV.setLayoutManager(new LinearLayoutManager(this));

        btnAddLearncard.setOnClickListener(v -> {learnsetToEdit.addLearncard(new Learncard("new Definition", "new Explanation")); learncardsRV.setAdapter(new LearncardAdapter(this, learnsetToEdit.getLearncards()));});

        learncardsRV.setAdapter(new LearncardAdapter(this, learnsetToEdit.getLearncards()));

    }

    private void save() {
    }
}