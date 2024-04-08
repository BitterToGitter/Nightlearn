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

public class MethodsHelper {
    public static boolean onTouch(View v, MotionEvent event, TextView textView) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                textView.setTextColor(Color.parseColor("#8B8B8B"));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                textView.setTextColor(Color.parseColor("#585858"));
                break;
        }
        return false;
    }

    public static void signOut(Context context) {
        FirebaseAuth.getInstance().signOut();
        UserService.myUser = null;
        Intent intent = new Intent(context, SplashActivity.class);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Toast.makeText(context, "User has been signed out successfully", Toast.LENGTH_SHORT).show();
            context.startActivity(intent);}
    }

}
