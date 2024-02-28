package jakimovich.nightlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText etName, etLastname, etNickname, etEmail, etPassword, etRepeatPassword;
    TextView tvBntContinue, tvGoLogIn;
    LinearLayout btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.etSignUpName);
        etLastname = findViewById(R.id.etSignUpLastname);
        etNickname = findViewById(R.id.etSignUpNickname);
        etEmail = findViewById(R.id.etSignUpEmail);
        etPassword = findViewById(R.id.etSignUpPassword);
        etRepeatPassword = findViewById(R.id.etSignUpRepeatPassword);

        tvBntContinue = findViewById(R.id.tvSignUpContinue);
        tvGoLogIn = findViewById(R.id.tvGoLogIn);

        btnContinue = findViewById(R.id.signUpContinue);

//        btnContinue.setOnFocusChangeListener((view, b) -> onFocusChange(b));

//        btnContinue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    tvBntContinue.setTextColor(Color.parseColor("#2C2C2C"));
//                } else {
//                    tvBntContinue.setTextColor(Color.parseColor("#585858"));
//                }
//            }
//        });

        SpannableString spannableString = new SpannableString("Already have an account? Log in!");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        };
        spannableString.setSpan(clickableSpan, 25, 32, 0);
        tvGoLogIn.setText(spannableString);
        tvGoLogIn.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());


        btnContinue.setOnClickListener(v -> signUp());

    }

    private void signUp(){

        String name = etName.getText().toString();
        String lastname = etLastname.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if(name.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()){
            Toast.makeText(this, "Please enter all the data", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password != repeatPassword){
            Toast.makeText(this, "Passwords don't match to each other", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "User has been created successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error:" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void onFocusChange(boolean hasFocus){
//        if (hasFocus)
//        {tvBntContinue.setTextColor(Color.parseColor("#2C2C2C"));}
//        else
//        {tvBntContinue.setTextColor(Color.parseColor("#585858"));}
//    }

}