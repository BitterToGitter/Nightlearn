package jakimovich.nightlearn.activities;

import static jakimovich.nightlearn.helpers.AlertDialogHelper.showOptionsAlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class LogInActivity extends AppCompatActivity {

    ImageView ivOptionsMenuBtn;
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

        ivOptionsMenuBtn = findViewById(R.id.logInOptionsMenuBtn);
        ivOptionsMenuBtn.setImageDrawable(MethodsHelper.convertSvgToDrawable(this,R.raw.ic_auth_options_menu_button));

        ivOptionsMenuBtn.setOnClickListener(v -> showPopupWindow(v));
        btnContinue.setOnClickListener(v -> logIn());

        createLinkedText(tvGoSignUp);

        btnContinueGuest.setOnClickListener(v -> guestEnter());

    }

    private void logIn(){

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter all the data", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "User has been signed in successfully", Toast.LENGTH_SHORT).show();
                //UserService.getUserById(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
                startActivity(new Intent(LogInActivity.this, SplashActivity.class));
                finishAffinity();
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
                Intent intent = getIntent();
                if(intent != null){
                    if(intent.hasExtra("fromSignUp")){
                        finish();
                    }
                    else {startActivity(new Intent(LogInActivity.this, SignUpActivity.class).putExtra("fromLogIn", true));}
                }
            }
        };
        spannableString.setSpan(clickableSpan, 27, 35, 0);
        textView.setText(spannableString);
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }

    private void showPopupWindow(View view) {

        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_menu_auth_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tvAuthor = popupView.findViewById(R.id.tvAuthMenuAuthor);
        TextView tvExit = popupView.findViewById(R.id.tvAuthMenuExit);

        tvAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, AuthorInfoActivity.class));
                popupWindow.dismiss();
            }
        });

        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsAlertDialog(LogInActivity.this, "Are you sure you want to exit?", "Yeah \n Let's get out", "Nope \n Back to study", v1 -> finnishAffinity());
                popupWindow.dismiss();
            }
            private void finnishAffinity() {
                finishAffinity();
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        popupWindow.showAsDropDown(view);
    }

    private void guestEnter(){

        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                startActivity(new Intent(LogInActivity.this, SplashActivity.class));
                finishAffinity();
            } else {
                Toast.makeText(this, "Error:" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}