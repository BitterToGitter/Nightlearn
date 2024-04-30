package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import jakimovich.nightlearn.activities.SplashActivity;
import jakimovich.nightlearn.classes.UserService;
import jakimovich.nightlearn.interfaces.AlertAcceptClickListener;

public class MethodsHelper {

    public static void signOut(Context context, Activity activity) {
        FirebaseAuth.getInstance().signOut();
        UserService.myUser = null;
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Toast.makeText(context, "User has been signed out successfully", Toast.LENGTH_SHORT).show();
            activity.startActivity(new Intent(activity, SplashActivity.class));
            activity.finish();
        }
        //TODO: Save the data before Signing out

    }

    public static void updateUserName(Context context, String name){

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/" + userId).child("name");
        ref.setValue(name);

        UserService.myUser.setName(name);

        Toast.makeText(context, "Your name has been updated", Toast.LENGTH_SHORT).show();

    }

    public static void updateUserLastname(Context context, String lastname){

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/" + userId).child("lastname");
        ref.setValue(lastname);

        UserService.myUser.setLastname(lastname);

        Toast.makeText(context, "Your lastname has been updated", Toast.LENGTH_SHORT).show();

    }


//    public static void systemExit(Activity activity){
//        activity.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(
//                new View.OnSystemUiVisibilityChangeListener() {
//                    @Override
//                    public void onSystemUiVisibilityChange(int visibility) {
//                        // Check if the navigation bar arrow button was clicked
//                        if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
//                            // Navigation bar is visible
//                            // Show AlertDialog when the arrow button is clicked
//                            AlertDialogHelper.showOptionsAlertDialog(activity, "Are you sure you want to exit?", "Yeah \n Let's get out", "Nope \n Back to study", this::finish );
//                        }
//                    }
//
//                    private void finish() {
//                        activity.finishAffinity();
//                    }
//                });

//    }
    //Todo: To think about systemExit here



}
