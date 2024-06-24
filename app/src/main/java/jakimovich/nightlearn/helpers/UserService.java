package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import jakimovich.nightlearn.activities.SplashActivity;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.QuizSettings;
import jakimovich.nightlearn.classes.UserRatingInfo;
import jakimovich.nightlearn.classes.UserGuest;
import jakimovich.nightlearn.classes.UserProfile;
import jakimovich.nightlearn.classes.UserVerified;
import jakimovich.nightlearn.interfaces.OnRatingUsersFetched;
import jakimovich.nightlearn.interfaces.OnRatingUsersPlaceFetched;

/**
 * Inner and firebase user management class
 */
public class UserService {

    public static UserProfile myUser;


    public static DatabaseReference getMyUserDatabaseRef(){
        return FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public static Task<Void> setMyUser(UserVerified user) {

        DatabaseReference ref = getMyUserDatabaseRef();

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("nickname", user.getNickname());
        userMap.put("name", user.getName());
        userMap.put("lastname", user.getLastname());
        userMap.put("eMail", user.getEMail());
        userMap.put("password", user.getPassword());
        userMap.put("learnsets",user.getLearnsets());
        userMap.put("points", user.getPoints());
        userMap.put("gamesPlayed", user.getGamesPlayed());
        userMap.put("cardsLearned", user.getCardsLearned());

        return ref.setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                myUser = user;
            }
        });

    }

    public static Task<UserVerified> getUserById(String userId, Context context) {

        DatabaseReference ref = getMyUserDatabaseRef();

        return ref.get().continueWith(task -> {
            if (task.isSuccessful()) {

                String nickname = task.getResult().child("nickname").getValue(String.class);
                String name = task.getResult().child("name").getValue(String.class);
                String lastname = task.getResult().child("lastname").getValue(String.class);
                String eMail = task.getResult().child("eMail").getValue(String.class);
                String password = task.getResult().child("password").getValue(String.class);
                int points = task.getResult().child("points").getValue(Integer.class);

                UserVerified profile = new UserVerified(nickname, name, lastname, eMail, password, points, getLearnsetsFromDatabase(context));

                if (Objects.equals(userId, FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    myUser = profile;
                }

                return profile;

            } else {
                throw task.getException();
            }
        });
    }

    public static boolean isGuest() {
        return (myUser instanceof UserGuest);
    }

    public static void signOut(Context context, Activity activity) {

        FirebaseAuth.getInstance().signOut();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            myUser = null;
            Toast.makeText(context, "User has been signed out successfully", Toast.LENGTH_SHORT).show();
            activity.startActivity(new Intent(activity, SplashActivity.class));
            activity.finish();
        }
    }

    public static void deleteAccount(Context context, Activity activity) {

        DatabaseReference ref = getMyUserDatabaseRef();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete().addOnCompleteListener(task -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                myUser = null;
                ref.removeValue();
                Toast.makeText(context, "User has been deleted successfully, you're starting from scratch!", Toast.LENGTH_LONG).show();
                activity.startActivity(new Intent(activity, SplashActivity.class));
                activity.finish();
            }
        });
    }

    public static void updateUserName(Context context, String name) {

            DatabaseReference ref = getMyUserDatabaseRef();

            ref.child("name").setValue(name);
            myUser.setName(name);
            Toast.makeText(context, "Your name has been updated", Toast.LENGTH_SHORT).show();

    }

    public static void updateUserLastname(Context context, String lastname) {

            DatabaseReference ref = getMyUserDatabaseRef();

            ref.child("lastname").setValue(lastname);
            myUser.setLastname(lastname);

            Toast.makeText(context, "Your lastname has been updated", Toast.LENGTH_SHORT).show();
    }

    public static void updateUserNickname(Context context, String nickname) {

            DatabaseReference ref = getMyUserDatabaseRef();

            ref.child("nickname").setValue(nickname);
            myUser.setNickname(nickname);
            Toast.makeText(context, "Your nickname has been updated", Toast.LENGTH_SHORT).show();

    }

    public static void updateUserGmail(Context context, String Email) {

            DatabaseReference ref = getMyUserDatabaseRef();

            ref.child("eMail").setValue(Email);
            myUser.setEMail(Email);
            Toast.makeText(context, "Your Email address has been updated", Toast.LENGTH_SHORT).show();

    }

    public static void updateUserPassword(Context context, String password) {

            DatabaseReference ref = getMyUserDatabaseRef();

            ref.child("password").setValue(password);
            myUser.setPassword(password);
            Toast.makeText(context, "Your password has been updated, don't forget it!", Toast.LENGTH_SHORT).show();

    }

    public static void updatePoints(int points) {

        myUser.addPoints(points);

        if (!isGuest()) {
            DatabaseReference ref = getMyUserDatabaseRef();
            ref.child("points").setValue(myUser.getPoints());
        }
    }

    public static void updateGamesPlayed() {
        if (!isGuest()) {
            DatabaseReference ref = getMyUserDatabaseRef();
            ref.child("gamesPlayed").setValue(myUser.getGamesPlayed());
        }
    }

    public static void updateCardsLearned() {
        if (!isGuest()) {
            DatabaseReference ref = getMyUserDatabaseRef();
            ref.child("cardsLearned").setValue(myUser.getCardsLearned());
        }

    }

    public static void updateLearnsets(ArrayList<Learnset> learnsets) {

        DatabaseReference ref = getMyUserDatabaseRef();

        ref.child("learnsets").removeValue();

        if (learnsets != null && learnsets.size() != 0) {
            ref.child("learnsets").setValue(learnsets);
        }
    }

    public static void updateLearnset(Learnset learnset, int position) {

        myUser.getLearnsets().set(position, learnset);

        if (!isGuest())
        {
            DatabaseReference ref = getMyUserDatabaseRef();
            ref.child("learnsets/" + position).setValue(learnset);
        }

    }

    public static void removeLearnset(int position) {

        myUser.getLearnsets().remove(position);
        updateLearnsets(myUser.getLearnsets());
    }

    public static ArrayList<Learnset> getLearnsetsFromDatabase(Context context) {

        DatabaseReference ref = getMyUserDatabaseRef();

        ArrayList<Learnset> learnsets = new ArrayList<>();

        if(ref.child("learnsets") != null) {
            ref.child("learnsets").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot learnsetSnapshot : dataSnapshot.getChildren()) {
                            String name = learnsetSnapshot.child("name").getValue(String.class);

                            DataSnapshot quizSettingsSnapshot = learnsetSnapshot.child("quizSettings");
                            QuizSettings quizSettings = new QuizSettings(quizSettingsSnapshot.child("questionsAmount").getValue(Integer.class), quizSettingsSnapshot.child("answerTimeSec").getValue(Integer.class), quizSettingsSnapshot.child("rightAnswersNumToBeLearned").getValue(Integer.class), quizSettingsSnapshot.child("questionType").getValue(Integer.class));

                            ArrayList<Learncard> learncards = new ArrayList<>();
                            for (DataSnapshot learncardSnapshot : learnsetSnapshot.child("learncards").getChildren()) {
                                Learncard learncard = new Learncard(learncardSnapshot.child("definition").getValue(String.class), learncardSnapshot.child("explanation").getValue(String.class), learncardSnapshot.child("timesSeen").getValue(Integer.class), learncardSnapshot.child("timesAnsweredRight").getValue(Integer.class), learncardSnapshot.child("learned").getValue(Boolean.class));
                                learncards.add(learncard);
                            }

                            int gamesPlayed = learnsetSnapshot.child("gamesPlayed").getValue(Integer.class);

                            Learnset learnset = new Learnset(name, quizSettings, gamesPlayed, learncards);
                            learnsets.add(learnset);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return learnsets;
    }

    public static void getRatingUsersList(Context context, OnRatingUsersFetched callback) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ArrayList<UserRatingInfo> ratingUsers = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String userId = userSnapshot.getKey();
                    String nickname = userSnapshot.child("nickname").getValue(String.class);
                    int gamesPlayed = userSnapshot.child("gamesPlayed").getValue(Integer.class);
                    int cardsLearned = userSnapshot.child("cardsLearned").getValue(Integer.class);
                    int pointsEarned = userSnapshot.child("points").getValue(Integer.class);

                    UserRatingInfo userRatingInfo = new UserRatingInfo(userId, nickname, gamesPlayed, cardsLearned, pointsEarned);
                    ratingUsers.add(userRatingInfo);

                }

                ratingUsers.sort((o1, o2) -> o2.getPoints() - o1.getPoints());
                callback.onFetched(ratingUsers);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getRatingPlace(Context context, String currentUserNickname, OnRatingUsersPlaceFetched callback) {
        getRatingUsersList(context, ratingUsers -> {
            for (int i = 0; i < ratingUsers.size(); i++) {
                if (ratingUsers.get(i).getNickname().equals(currentUserNickname)) {
                    callback.onPlaceFetched(i + 1);
                    return;
                }
            }
            // User not found in the list, return -1
            callback.onPlaceFetched(-1);
        });
    }
}
