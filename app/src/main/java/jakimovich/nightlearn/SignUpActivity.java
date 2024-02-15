package jakimovich.nightlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    EditText etName;
    EditText etLastname;
    EditText etNickname;
    EditText etEmail;
    EditText etPassword;
    EditText etRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName.findViewById(R.id.etSignUpName);


    }
}