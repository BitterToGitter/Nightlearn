package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.helpers.UserService;
import jakimovich.nightlearn.helpers.AlertDialogHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
        if(isConnectedToInternet()){
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null){
               // if(UserService.isGuest()){
               //     startActivity(new Intent(SplashActivity.this, MainActivity.class));
               //     finish();
              //  } else { //Todo: to solve
                    UserService.getUserById(currentUser.getUid(), this).addOnCompleteListener(task -> {
                        if (UserService.myUser == null) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    });
              //  }
            } else {
                startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                finish();
            }
        }else {
            AlertDialogHelper.showWarningAlertDialog(this, "Oops..., your device seems to be disconnected from internet. For proper app work stable wifi network is needed.", "Ok", v -> finishAndRemoveTask());
        }
        }, 500);

    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}