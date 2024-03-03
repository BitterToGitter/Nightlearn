package jakimovich.nightlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView tvTitleMain;
    Button btnNext, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitleMain = findViewById(R.id.tvTitleMain);
        btnNext = findViewById(R.id.btnMainNext);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        tvTitleMain.setText("Happy to see you, " + UserService.myUser.getNickname());
        btnSignOut.setOnClickListener(v -> signOut());

    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        UserService.myUser = null;
        startActivity(new Intent(MainActivity.this, SplashActivity.class));
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
        Toast.makeText(this, "User has been signed out successfully", Toast.LENGTH_SHORT).show();}
    }

}