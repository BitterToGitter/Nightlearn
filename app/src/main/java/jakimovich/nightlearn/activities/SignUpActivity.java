package jakimovich.nightlearn.activities;

import static jakimovich.nightlearn.helpers.AlertDialogHelper.showOptionsAlertDialog;
import static jakimovich.nightlearn.helpers.InputChecker.gmailCheck;
import static jakimovich.nightlearn.helpers.InputChecker.lastnameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.nameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.nicknameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.passwordCheck;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import jakimovich.nightlearn.classes.UserProfile;
import jakimovich.nightlearn.helpers.UserService;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class SignUpActivity extends AppCompatActivity  {

    ImageView ivOptionsMenuBtn;
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

        ivOptionsMenuBtn = findViewById(R.id.signUpOptionsMenuBtn);
        ivOptionsMenuBtn.setImageDrawable(MethodsHelper.convertSvgToDrawable(this,R.raw.ic_auth_options_menu_button));
        ivOptionsMenuBtn.setOnClickListener(v -> showPopupWindow(v));

        btnContinue.setOnClickListener(v -> signUp());

        createLinkedText(tvGoLogIn);

        btnContinueGuest.setOnClickListener(v -> guestEnter());

    }

    private void signUp() {

        String nickname = etNickname.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String lastname = etLastname.getText().toString().trim();
        String eMail = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String repeatPassword = etRepeatPassword.getText().toString().trim();

        if(nickname.isEmpty() || name.isEmpty() || lastname.isEmpty() || eMail.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()){
            Toast.makeText(this, "Please enter all the data", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(repeatPassword)){
            Toast.makeText(this, "Passwords don't match to each other", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(nameCheck(this, name) && lastnameCheck(this, lastname) && nicknameCheck(this, nickname) && gmailCheck(this, eMail) && passwordCheck(this, password))){
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(eMail, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                UserService.setMyUser(new UserProfile(nickname, name, lastname, eMail, password));
                Toast.makeText(this, "User has been created successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, SplashActivity.class));
                finish();
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
                Intent intent = getIntent();
                if(intent != null){
                    if(intent.hasExtra("fromLogIn")){
                        finish();
                    }
                    else {startActivity(new Intent(SignUpActivity.this, LogInActivity.class).putExtra("fromSignUp", true));}
                }
            }
        };
        spannableString.setSpan(clickableSpan, 25, 32, 0);
        textView.setText(spannableString);
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }


    private void showPopupWindow(View view) {

        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_menu_auth_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tvAuthor = popupView.findViewById(R.id.tvAuthMenuAuthor);
        TextView tvExit = popupView.findViewById(R.id.tvAuthMenuExit);

        tvAuthor.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, AuthorInfoActivity.class));
            popupWindow.dismiss();
        });

        tvExit.setOnClickListener(v -> {
                showOptionsAlertDialog(SignUpActivity.this, "Are you sure you want to exit?", "Yeah \n Let's get out", "Nope \n Back to study", v1 -> finishAndRemoveTask());
                popupWindow.dismiss();
            });

        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        popupWindow.showAsDropDown(view);
    }

    private void guestEnter(){

        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "You entered as a guest", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, SplashActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Error:" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialogHelper.showOptionsAlertDialog(this, "Are you sure you want to exit the app?", "Yeah \n Let's get out", "Nope \n Back to study", v -> finishAffinity());
    }

}