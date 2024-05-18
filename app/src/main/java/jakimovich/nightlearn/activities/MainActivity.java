package jakimovich.nightlearn.activities;

import static jakimovich.nightlearn.helpers.AlertDialogHelper.showMenuAlertDialog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.UserService;
import jakimovich.nightlearn.databinding.ActivityMainBinding;
import jakimovich.nightlearn.fragments.AlarmsFragment;
import jakimovich.nightlearn.fragments.HomeFragment;
import jakimovich.nightlearn.fragments.LearnsetsFragment;
import jakimovich.nightlearn.fragments.ProfileFragment;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.MethodsHelper;

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

        replaceFragment(new HomeFragment(), "home");

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    replaceFragment(new HomeFragment(), "home");
                    break;

                case R.id.menuLearnsets:
                    replaceFragment(new LearnsetsFragment(), "learnsets");
                    break;

                case R.id.menuAlarms:
                    replaceFragment(new AlarmsFragment(), "alarms");
                    break;

                case R.id.menuProfile:
                    replaceFragment(new ProfileFragment(), "profile");
                    break;
            }
            return true;
        });

    }

    public void replaceFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment, fragmentTag);
        fragmentTransaction.commit();
    }

    public void showAlertDialogForMain(View v) {
        showMenuAlertDialog(this);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        AlertDialogHelper.showOptionsAlertDialog(this, "Are you sure you want to exit the app?", "Yeah \n Let's get out", "Nope \n Back to study", v -> finishAffinity());

    }

}