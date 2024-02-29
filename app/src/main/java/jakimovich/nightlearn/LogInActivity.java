package jakimovich.nightlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    TextView tvBntContinue, tvGoSignUp;
    LinearLayout btnContinue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = findViewById(R.id.etLogInEmail);
        etPassword = findViewById(R.id.etLogInPassword);

        tvBntContinue = findViewById(R.id.tvLogInContinue);
        tvGoSignUp = findViewById(R.id.tvGoSignUp);

        btnContinue = findViewById(R.id.LogInContinue);

        btnContinue.setOnTouchListener((v, event) -> onTouch(v, event));
        btnContinue.setOnClickListener(v -> logIn());

        createLinkedText(tvGoSignUp);

    }

    private void logIn(){

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter all the data", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "User has been signed in successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
            } else {
                Toast.makeText(this, "Error:" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tvBntContinue.setTextColor(Color.parseColor("#8B8B8B"));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                tvBntContinue.setTextColor(Color.parseColor("#585858"));
                break;
        }
        return false;
    }

    private void createLinkedText(TextView textView){
        SpannableString spannableString = new SpannableString("Don’t have an account yet? Sign up!");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        };
        spannableString.setSpan(clickableSpan, 27, 35, 0);
        textView.setText(spannableString);
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }


}