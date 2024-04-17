package jakimovich.nightlearn.activities;

import static jakimovich.nightlearn.helpers.AlertDialogHelper.showOptionsAlertDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.UserService;

public class LogInActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText etEmail, etPassword;
    TextView tvBntContinue, tvGoSignUp;
    LinearLayout btnContinue;

    Button btnContinueGuest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = findViewById(R.id.etLogInEmail);
        etPassword = findViewById(R.id.etLogInPassword);

        tvBntContinue = findViewById(R.id.tvLogInContinue);
        tvGoSignUp = findViewById(R.id.tvGoSignUp);

        btnContinue = findViewById(R.id.LogInLayoutContinue);

        btnContinueGuest = findViewById(R.id.btnLogInGuest);

        toolbar = findViewById(R.id.logInToolbar);

        btnContinue.setOnClickListener(v -> logIn());

        createLinkedText(tvGoSignUp);

        btnContinueGuest.setOnClickListener(v -> guestEnter());

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
                UserService.getUserById(FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(new Intent(LogInActivity.this, SplashActivity.class));
            } else {
                Toast.makeText(this, "Error:" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createLinkedText(TextView textView){
        SpannableString spannableString = new SpannableString("Don’t have an account yet? Sign up!");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class)); //TODO: Actually get BACK to signUp
            }
        };
        spannableString.setSpan(clickableSpan, 27, 35, 0);
        textView.setText(spannableString);
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionAuthor:
                startActivity(new Intent(LogInActivity.this, AuthorInfoActivity.class));
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
                startActivity(new Intent(LogInActivity.this, SplashActivity.class));
            } else {
                Toast.makeText(this, "Error:" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}