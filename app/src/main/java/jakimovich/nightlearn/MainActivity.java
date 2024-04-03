package jakimovich.nightlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jakimovich.nightlearn.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BottomAppBar bac;
    ActivityMainBinding binding;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bac = findViewById(R.id.bottomAppBar);
        floatingActionButton = findViewById(R.id.fab);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menuHome:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.menuLearnsets:
                    replaceFragment(new LearnsetsFragment());
                    break;

                case R.id.menuAlarms:
                    replaceFragment(new AlarmsFragment());
                    break;

                case R.id.menuProfile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
        floatingActionButton.bringToFront();

        floatingActionButton.setOnClickListener(v -> {
            signOut();
        });

    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        UserService.myUser = null;
        startActivity(new Intent(MainActivity.this, SplashActivity.class));
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
        Toast.makeText(this, "User has been signed out successfully", Toast.LENGTH_SHORT).show();}
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
        fragmentTransaction.commit();
    }

    private boolean onTouch(View v, MotionEvent event, TextView textView) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                textView.setTextColor(Color.parseColor("#8B8B8B"));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                textView.setTextColor(Color.parseColor("#D9D9D9"));
                break;
        }
        return false;
    }
    private void showAlertDialog() {

        LinearLayout alertDialogMenu = findViewById(R.id.llAlertDialogMenu);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_dialog_menu, alertDialogMenu);

        TextView tvCreateLearnsetsFolder = view.findViewById(R.id.tvCreateLearnsetsFolder);
        TextView tvCreateLearnset = view.findViewById(R.id.tvCreateLearnset);
        TextView tvCreateAlarm = view.findViewById(R.id.tvCreateAlarm);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        tvCreateLearnsetsFolder.setOnTouchListener((v, event) -> onTouch(v, event, tvCreateLearnsetsFolder));
        tvCreateLearnset.setOnTouchListener((v, event) -> onTouch(v, event, tvCreateLearnset));
        tvCreateAlarm.setOnTouchListener((v, event) -> onTouch(v, event, tvCreateAlarm));

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        alertDialog.show();

    }
}