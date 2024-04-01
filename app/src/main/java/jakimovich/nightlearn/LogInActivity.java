package jakimovich.nightlearn;

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


        btnContinue.setOnTouchListener((v, event) -> onTouch(v, event, tvBntContinue));
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

    private boolean onTouch(View v, MotionEvent event, TextView textView) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                textView.setTextColor(Color.parseColor("#8B8B8B"));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                textView.setTextColor(Color.parseColor("#585858"));
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionAuthor:
                startActivity(new Intent(LogInActivity.this, AuthorInfo.class));
                return true;
            case R.id.optionExit:
                showAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDialog(){

        LinearLayout alertDialogExit = findViewById(R.id.linearLayoutAlertDialogExit);
        View view = LayoutInflater.from(LogInActivity.this).inflate(R.layout.alert_dialog_exit, alertDialogExit);
        LinearLayout alertExitAccept = view.findViewById(R.id.linearLayoutAlertExitAccept);
        LinearLayout alertExitDecline = view.findViewById(R.id.linearLayoutAlertExitDecline);
        TextView tvExitAccept = view.findViewById(R.id.tvAlertExitAccept);
        TextView tvExitDecline = view.findViewById(R.id.tvAlertExitDecline);

        AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        alertExitAccept.setOnTouchListener((v, event) -> onTouch(v, event, tvExitAccept));
        alertExitDecline.setOnTouchListener((v, event) -> onTouch(v, event, tvExitDecline));
        alertExitAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        alertExitDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();

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