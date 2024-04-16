package jakimovich.nightlearn;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class MethodsHelper {

    public static void signOut(Context context) {
        FirebaseAuth.getInstance().signOut();
        UserService.myUser = null;
        Intent intent = new Intent(context, SplashActivity.class);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Toast.makeText(context, "User has been signed out successfully", Toast.LENGTH_SHORT).show();
            context.startActivity(intent);}
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

        UserService.myUser.setName(lastname);

        Toast.makeText(context, "Your lastname has been updated", Toast.LENGTH_SHORT).show();

    }

}
