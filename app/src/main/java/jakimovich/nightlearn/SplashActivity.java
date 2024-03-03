package jakimovich.nightlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null){
                UserService.getUserById(currentUser.getUid()).addOnCompleteListener(task -> {
                    if(UserService.myUser == null){
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                });
            } else {
                startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
            }
            finish();
        }, 1000);

    }
}