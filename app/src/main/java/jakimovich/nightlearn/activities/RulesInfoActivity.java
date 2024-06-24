package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import jakimovich.nightlearn.R;

public class RulesInfoActivity extends AppCompatActivity {

    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_info);

        btnBack = findViewById(R.id.btnRulesGetBack);
        btnBack.setOnClickListener(v -> finish());

    }

}