package jakimovich.nightlearn.helpers;

import static jakimovich.nightlearn.helpers.InputChecker.gmailCheck;
import static jakimovich.nightlearn.helpers.InputChecker.lastnameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.nameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.nicknameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.passwordCheck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakimovich.nightlearn.activities.LearnsetEditActivity;
import jakimovich.nightlearn.activities.SplashActivity;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.Quiz;
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

    public static void deleteAccount(Context context, Activity activity){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        user.delete().addOnCompleteListener(task -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                UserService.myUser = null;
                FirebaseDatabase.getInstance().getReference("users/" + userId).removeValue();
                Toast.makeText(context, "User has been deleted successfully, you're starting from scratch!", Toast.LENGTH_LONG).show();
                activity.startActivity(new Intent(activity, SplashActivity.class));
                activity.finish();
            }});
    }

    public static void updateUserName(Context context, String name){
        if(nameCheck(context, name)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("name");
            ref.setValue(name);

            UserService.myUser.setName(name);

            Toast.makeText(context, "Your name has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserLastname(Context context, String lastname){

        if(lastnameCheck(context, lastname)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("lastname");
            ref.setValue(lastname);

            UserService.myUser.setLastname(lastname);

            Toast.makeText(context, "Your lastname has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserNickname(Context context, String nickname){

        if(nicknameCheck(context, nickname)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("nickname");
            ref.setValue(nickname);

            UserService.myUser.setNickname(nickname);

            Toast.makeText(context, "Your nickname has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserGmail(Context context, String gmail) {

        if(gmailCheck(context, gmail)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("eMail");
            ref.setValue(gmail);

            UserService.myUser.setEMail(gmail);

            Toast.makeText(context, "Your gmail address has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserPassword(Context context, String password) {

        if(passwordCheck(context, password)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("password");
            ref.setValue(password);

            UserService.myUser.setPassword(password);

            Toast.makeText(context, "Your password has been updated, don't forget it!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    public static PictureDrawable convertSvgToDrawable (Context context, int inputStreamInt) throws SVGParseException {

        try {
            return new PictureDrawable(SVG.getFromInputStream(context.getResources().openRawResource(inputStreamInt)).renderToPicture());
    } catch (SVGParseException e) {
        e.printStackTrace();
    }
        return null;
    }

    public static Intent putLearnsetIntoIntent(Activity activity, Learnset learnset){

        List<Learncard> learncards = learnset.getLearncards();

        Intent intent = new Intent(activity, LearnsetEditActivity.class);

        intent.putExtra("cardsInTotal", learncards.size());
        intent.putExtra("learnsetName", learnset.getName());
        intent.putExtra("quizQuestionsAmount", learnset.getQuizSettings().getQuestionsAmount());
        intent.putExtra("quizAnswerTimeSec", learnset.getQuizSettings().getAnswerTimeSec());
        intent.putExtra("quizRightAnswersNumToBeLearned", learnset.getQuizSettings().getRightAnswersNumToBeLearned());
        intent.putExtra("quizQuestionType", learnset.getQuizSettings().getQuestionType());

        for(int i = 0; i < learncards.size(); i++ ){
            intent.putExtra("definition " + i, learncards.get(i).getDefinition());
            intent.putExtra("explanation " + i, learncards.get(i).getExplanation());
            intent.putExtra("timesSeen " + i, learncards.get(i).getTimesSeen());
            intent.putExtra("timesAnsweredRight " + i, learncards.get(i).getTimesAnsweredRight());
            intent.putExtra("learned " + i, learncards.get(i).getLearned());
        }

        return intent;

    }

    public static Learnset getLearnsetFromIntent(Intent intent){

        ArrayList<Learncard> learncards = new ArrayList<>();
        for (int i = 0; i < intent.getExtras().getInt("cardsInTotal"); i++){
            learncards.add(new Learncard(
                    intent.getExtras().getString("definition " + i),
                    intent.getExtras().getString("explanation " + i),
                    intent.getExtras().getInt("timesSeen " + i),
                    intent.getExtras().getInt("timesAnsweredRight " + i),
                    intent.getExtras().getBoolean("learned " + i)));
        }

        return new Learnset(
                intent.getExtras().getString("learnsetName"),
                new Quiz(intent.getExtras().getInt("quizQuestionsAmount"),
                        intent.getExtras().getInt("quizAnswerTimeSec"),
                        intent.getExtras().getInt("quizRightAnswersNumToBeLearned"),
                        intent.getExtras().getInt("quizQuestionType")),
                learncards
        );

    }

}
