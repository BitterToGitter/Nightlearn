package jakimovich.nightlearn.activities;

import static jakimovich.nightlearn.helpers.AlertDialogHelper.showMenuAlertDialog;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.databinding.ActivityMainBinding;
import jakimovich.nightlearn.fragments.main.RatingFragment;
import jakimovich.nightlearn.fragments.main.HomeFragment;
import jakimovich.nightlearn.fragments.main.LearnsetsFragment;
import jakimovich.nightlearn.fragments.main.ProfileFragment;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.GeneralHelper;
import jakimovich.nightlearn.helpers.ImageFilesManager;
import jakimovich.nightlearn.helpers.UserService;

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

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.menuLearnsets:
                    replaceFragment(new LearnsetsFragment());
                    break;

                case R.id.menuRating:
                    replaceFragment(new RatingFragment());
                    break;

                case R.id.menuProfile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });

        new Handler().postDelayed(() -> {
            replaceFragment(new HomeFragment());
            if (!UserService.isGuest()) {
                if (!ImageFilesManager.getProfilePicFile(this).exists()) {
                    ImageFilesManager.getProfilePicRef().getDownloadUrl().addOnSuccessListener(uri -> {
                        AlertDialogHelper.showLoadingAlertDialog(this, "Welcome back, " + UserService.myUser.getName() + "!", "Just a sec... \n Let your profile picture be downloaded from the server.");
                        ImageFilesManager.downloadPictureFromStorage(this, ImageFilesManager.getProfilePicRef(), ImageFilesManager.getProfilePicFile(this), () -> {AlertDialogHelper.dismissAlertDialog(); Toast.makeText(this, "Your profile picture has been successfully downloaded from the server!", Toast.LENGTH_SHORT).show();});});
                }
            }
        }, 100);
    }

    public void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
        fragmentTransaction.commit();

    }

    public void replaceFragmentWithMenuItem(Fragment fragment, @IdRes int menuItemId) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_in_right, R.anim.fragment_fade_out);
            fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
            fragmentTransaction.commit();

            binding.bottomNavigationView.setSelectedItemId(menuItemId);
    }


    public void updateCurrentFragment() {
        Fragment visibleFragment = GeneralHelper.getVisibleFragment(this);
        Fragment updatedFragment = null;

        if (visibleFragment instanceof HomeFragment) {
            updatedFragment = new HomeFragment();
        } else if (visibleFragment instanceof LearnsetsFragment) {
            updatedFragment = new LearnsetsFragment();
        } else if (visibleFragment instanceof RatingFragment) {
            updatedFragment = new RatingFragment();
        } else if (visibleFragment instanceof ProfileFragment) {
            updatedFragment = new ProfileFragment();
        }

        if (updatedFragment != null) {
            replaceFragment(updatedFragment);
        }
    }

    public void showMenu(View v) {
        showMenuAlertDialog(this);
    }

    /**
     *  Updates the learnsets fragment in a case the app displays it currently
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                updateCurrentFragment();
            }
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialogHelper.showOptionsAlertDialog(this, "Are you sure you want to exit the app?", "Yeah \n Let's get out", "Nope \n Back to study", v1 -> finishAffinity(), v2 ->{});
    }
}

