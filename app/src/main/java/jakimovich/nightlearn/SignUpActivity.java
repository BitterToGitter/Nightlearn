package jakimovich.nightlearn;

import static jakimovich.nightlearn.AlertDialogHelper.showOptionsAlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity  {

    Toolbar toolbar;
    EditText etName, etLastname, etNickname, etEmail, etPassword, etRepeatPassword;
    TextView tvGoLogIn, tvBntContinue;
    LinearLayout btnContinue;
    Button btnContinueGuest;

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

        btnContinue = findViewById(R.id.signUpLayoutContinue);

        btnContinueGuest = findViewById(R.id.btnSignUpGuest);

        toolbar = findViewById(R.id.signUpToolbar);
        setSupportActionBar(toolbar);

        //btnContinue.setOnFocusChangeListener((v, hasFocus) -> onTouch(v, hasFocus, tvBntContinue));
        btnContinue.setOnClickListener(v -> signUp());

        createLinkedText(tvGoLogIn);

        btnContinueGuest.setOnClickListener(v -> guestEnter());

    }

    private void signUp() {

        String nickname = etNickname.getText().toString();
        String name = etName.getText().toString();
        String lastname = etLastname.getText().toString();
        String eMail = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if(nickname.isEmpty() || name.isEmpty() || lastname.isEmpty() || eMail.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()){
            Toast.makeText(this, "Please enter all the data", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(repeatPassword)){
            Toast.makeText(this, "Passwords don't match to each other", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(eMail, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                UserService.setMyUser(new UserProfile(nickname, name, lastname, eMail, password));
                Toast.makeText(this, "User has been created successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, SplashActivity.class));
            } else {
                Toast.makeText(this, "Error: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createLinkedText(TextView textView){
        SpannableString spannableString = new SpannableString("Already have an account? Log in!");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        };
        spannableString.setSpan(clickableSpan, 25, 32, 0);
        textView.setText(spannableString);
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionAuthor:
                startActivity(new Intent(SignUpActivity.this, AuthorInfo.class));
                return true;
            case R.id.optionExit:
                String message = "Are you sure you want to exit?";
                String accept = "Yeah \n Let's get out";
                String decline = "Nope \n Back to study";
                showOptionsAlertDialog(this,  message, accept, decline, this::finishAffinity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void guestEnter(){

        String email = "guest@guest.com";
        String password = "guestt";

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "You entered as a guest", Toast.LENGTH_SHORT).show();
                UserService.getUserById(FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(new Intent(SignUpActivity.this, SplashActivity.class));
            } else {
                Toast.makeText(this, "Error:" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}