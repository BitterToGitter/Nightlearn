package jakimovich.nightlearn.helpers;

import static jakimovich.nightlearn.helpers.InputChecker.gmailCheck;
import static jakimovich.nightlearn.helpers.InputChecker.lastnameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.nameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.nicknameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.passwordCheck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import jakimovich.nightlearn.activities.SplashActivity;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.Quiz;
import jakimovich.nightlearn.classes.UserProfile;

/**
 * Inner and firebase user management class
 */
public class UserService {
    public static UserProfile myUser;
    public static final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public static final DatabaseReference USER_DB_REF = FirebaseDatabase.getInstance().getReference("users/" + USER_ID);

    public static Task<Void> setMyUser(UserProfile user) {

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("nickname", user.getNickname());
        userMap.put("name", user.getName());
        userMap.put("lastname", user.getLastname());
        userMap.put("eMail", user.getEMail());
        userMap.put("password", user.getPassword());

        return USER_DB_REF.setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                myUser = user;
            }
        });

    }

    public static Task<UserProfile> getUserById(String userId, Context context) {

        return USER_DB_REF.get().continueWith(task -> {
            if (task.isSuccessful()) {

                String nickname = task.getResult().child("nickname").getValue(String.class);
                String name = task.getResult().child("name").getValue(String.class);
                String lastname = task.getResult().child("lastname").getValue(String.class);
                String eMail = task.getResult().child("eMail").getValue(String.class);
                String password = task.getResult().child("password").getValue(String.class);

                UserProfile profile = new UserProfile(nickname, name, lastname, eMail, password);

                if (Objects.equals(userId, FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    myUser = profile;
                    myUser.setLearnsets(getLearnsetsFromDatabase(context));
                }

                Uri profilePicUri = Uri.parse(task.getResult().child("profilePic").getValue(String.class));
                if(profilePicUri != null)
                {myUser.setProfilePic(profilePicUri);}
//                File uriFile = new File(Environment.getExternalStorageDirectory() + profilePicUri.getPath());
//                if (uriFile.exists()) {
//                    myUser.setProfilePic(profilePicUri);
//                    Toast.makeText(context, "Stuck on database", Toast.LENGTH_SHORT).show();
//                } else {
//                    StorageReference profilePicRef = FirebaseStorage.getInstance().getReference("users/" + userId + "/profilePic");
//
//                    profilePicRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes ->  {
//                        { File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/jakimovich.nightlearn/files/DCIM", "profilePic.jpg");
//
//                            // Create parent directories if they don't exist
//                            if (!file.getParentFile().exists()) {
//                                file.getParentFile().mkdirs();
//                            }
//
//                            try (FileOutputStream fos = new FileOutputStream(file)) {
//                                fos.write(bytes);
//                                myUser.setProfilePic(Uri.fromFile(file));
//                                uploadUriPicToDatabase(Uri.fromFile(file));
//                                Toast.makeText(context, "Image Saved Successfully", Toast.LENGTH_SHORT).show();
//                            } catch (IOException e) {
//                                Log.e("SaveImage", "Saving image failed", e);
//                            }
//                        }
//                    }).addOnFailureListener(e ->
//                            { Log.e("FirebaseStorage", "Download failed", e); }
//                    );
                //}
                // TODO: To fix storage downloading

                //TODO: To build user inheritance

                return profile;

            } else {
                throw task.getException();
            }
        });
    }

   public static void uploadProfilePicToStorage(Context context, Uri fileUri) {

    StorageReference profilePicRef = FirebaseStorage.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profilePic");

    profilePicRef.putFile(fileUri)
        .addOnSuccessListener(taskSnapshot -> {
            uploadUriPicToDatabase(fileUri);
            Toast.makeText(context, "Profile picture uploaded successfully", Toast.LENGTH_SHORT).show();
        })
        .addOnFailureListener(exception -> {
            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
}

    public static void uploadUriPicToDatabase(Uri fileUri) {
        USER_DB_REF.child("profilePic").setValue(fileUri.toString());
    }

    public static boolean isGuest() {
        return FirebaseAuth.getInstance().getCurrentUser().isAnonymous();
    }

    public static void signOut(Context context, Activity activity) {
        FirebaseAuth.getInstance().signOut();
        myUser = null;
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(context, "User has been signed out successfully", Toast.LENGTH_SHORT).show();
            activity.startActivity(new Intent(activity, SplashActivity.class));
            activity.finish();
        }
    }

    public static void deleteAccount(Context context, Activity activity) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        user.delete().addOnCompleteListener(task -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                myUser = null;
                USER_DB_REF.removeValue();
                Toast.makeText(context, "User has been deleted successfully, you're starting from scratch!", Toast.LENGTH_LONG).show();
                activity.startActivity(new Intent(activity, SplashActivity.class));
                activity.finish();
            }
        });
    }

    public static void updateUserName(Context context, String name) {
        if (nameCheck(context, name)) {

            USER_DB_REF.child("name").setValue(name);
            myUser.setName(name);
            Toast.makeText(context, "Your name has been updated", Toast.LENGTH_SHORT).show();

        }
    }

    public static void updateUserLastname(Context context, String lastname) {

        if (lastnameCheck(context, lastname)) {

            USER_DB_REF.child("lastname").setValue(lastname);
            myUser.setLastname(lastname);

            Toast.makeText(context, "Your lastname has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserNickname(Context context, String nickname) {

        if (nicknameCheck(context, nickname)) {

            USER_DB_REF.child("nickname").setValue(nickname);
            myUser.setNickname(nickname);
            Toast.makeText(context, "Your nickname has been updated", Toast.LENGTH_SHORT).show();

        }
    }

    public static void updateUserGmail(Context context, String Email) {

        if (gmailCheck(context, Email)) {

            USER_DB_REF.child("eMail").setValue(Email);
            myUser.setEMail(Email);
            Toast.makeText(context, "Your Email address has been updated", Toast.LENGTH_SHORT).show();

        }
    }

    public static void updateUserPassword(Context context, String password) {

        if (passwordCheck(context, password)) {

            USER_DB_REF.child("password").setValue(password);
            myUser.setPassword(password);
            Toast.makeText(context, "Your password has been updated, don't forget it!", Toast.LENGTH_SHORT).show();

        }
    }

    public static void updateLearnsets(ArrayList<Learnset> learnsets) {

        USER_DB_REF.child("learnsets").removeValue();

        if (learnsets != null && learnsets.size() != 0) {
            USER_DB_REF.child("learnsets").setValue(learnsets);
        }
    }

    public static void updateLearnset(Learnset learnset, int position) {

        myUser.getLearnsets().set(position, learnset);
        USER_DB_REF.child("learnsets/" + position).setValue(learnset);
    }

    public static void removeLearnset(int position) {

        myUser.getLearnsets().remove(position);
        updateLearnsets(myUser.getLearnsets());
    }

    public static ArrayList<Learnset> getLearnsetsFromDatabase(Context context) {

        ArrayList<Learnset> learnsets = new ArrayList<>();

        if(USER_DB_REF.child("learnsets") != null) {
            USER_DB_REF.child("learnsets").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot learnsetSnapshot : dataSnapshot.getChildren()) {
                            String name = learnsetSnapshot.child("name").getValue(String.class);

                            DataSnapshot quizSettingsSnapshot = learnsetSnapshot.child("quizSettings");
                            Quiz quizSettings = new Quiz(quizSettingsSnapshot.child("questionsAmount").getValue(Integer.class), quizSettingsSnapshot.child("answerTimeSec").getValue(Integer.class), quizSettingsSnapshot.child("rightAnswersNumToBeLearned").getValue(Integer.class), quizSettingsSnapshot.child("questionType").getValue(Integer.class));

                            ArrayList<Learncard> learncards = new ArrayList<>();
                            for (DataSnapshot learncardSnapshot : learnsetSnapshot.child("learncards").getChildren()) {
                                Learncard learncard = new Learncard(learncardSnapshot.child("definition").getValue(String.class), learncardSnapshot.child("explanation").getValue(String.class), learncardSnapshot.child("timesSeen").getValue(Integer.class), learncardSnapshot.child("timesAnsweredRight").getValue(Integer.class), learncardSnapshot.child("learned").getValue(Boolean.class));
                                learncards.add(learncard);
                            }

                            Learnset learnset = new Learnset(name, quizSettings, learncards);
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
}
