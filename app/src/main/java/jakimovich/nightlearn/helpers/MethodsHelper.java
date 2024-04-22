package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jakimovich.nightlearn.activities.SplashActivity;
import jakimovich.nightlearn.classes.UserService;

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

}
