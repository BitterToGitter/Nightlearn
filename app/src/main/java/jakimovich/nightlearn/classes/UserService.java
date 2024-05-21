package jakimovich.nightlearn.classes;

import static jakimovich.nightlearn.helpers.InputChecker.gmailCheck;
import static jakimovich.nightlearn.helpers.InputChecker.lastnameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.nameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.nicknameCheck;
import static jakimovich.nightlearn.helpers.InputChecker.passwordCheck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import jakimovich.nightlearn.activities.MainActivity;
import jakimovich.nightlearn.activities.SplashActivity;

public class UserService {
    public static UserProfile myUser;

    public static Task<Void> setMyUser(UserProfile user) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference("users/" + userId);

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("nickname", user.getNickname());
        userMap.put("name", user.getName());
        userMap.put("lastname", user.getLastname());
        userMap.put("eMail", user.getEMail());
        userMap.put("password", user.getPassword());

        return ref.setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                myUser = user;
            }
        });

    }

    public static Task<UserProfile> getUserById(String userId, Context context) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/" + userId);

        return ref.get().continueWith(task -> {
            if (task.isSuccessful()) {

                String nickname = task.getResult().child("nickname").getValue(String.class);
                String name = task.getResult().child("name").getValue(String.class);
                String lastname = task.getResult().child("lastname").getValue(String.class);
                String eMail = task.getResult().child("eMail").getValue(String.class);
                String password = task.getResult().child("password").getValue(String.class);

                UserProfile profile = new UserProfile(nickname, name, lastname, eMail, password);

                if (Objects.equals(userId, FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    myUser = profile;
                    getLearnsetsFromDatabase(context);
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
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilePic");
        databaseRef.setValue(fileUri.toString());
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
                FirebaseDatabase.getInstance().getReference("users/" + userId).removeValue();
                Toast.makeText(context, "User has been deleted successfully, you're starting from scratch!", Toast.LENGTH_LONG).show();
                activity.startActivity(new Intent(activity, SplashActivity.class));
                activity.finish();
            }
        });
    }

    public static void updateUserName(Context context, String name) {
        if (nameCheck(context, name)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("name");
            ref.setValue(name);

            myUser.setName(name);

            Toast.makeText(context, "Your name has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserLastname(Context context, String lastname) {

        if (lastnameCheck(context, lastname)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("lastname");
            ref.setValue(lastname);

            myUser.setLastname(lastname);

            Toast.makeText(context, "Your lastname has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserNickname(Context context, String nickname) {

        if (nicknameCheck(context, nickname)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("nickname");
            ref.setValue(nickname);

            myUser.setNickname(nickname);

            Toast.makeText(context, "Your nickname has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserGmail(Context context, String gmail) {

        if (gmailCheck(context, gmail)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("eMail");
            ref.setValue(gmail);

            myUser.setEMail(gmail);

            Toast.makeText(context, "Your gmail address has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateUserPassword(Context context, String password) {

        if (passwordCheck(context, password)) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users/" + userId).child("password");
            ref.setValue(password);

            myUser.setPassword(password);

            Toast.makeText(context, "Your password has been updated, don't forget it!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateLearnsets(Context context, ArrayList<Learnset> learnsets) {

        if (learnsets != null || learnsets.size() != 0) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

            for (int i = 0; i < learnsets.size(); i++) {
                databaseReference.child("learnsets").push().setValue(learnsets.get(i));
            }

            Toast.makeText(context, "Learnsets updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void removeLearnset(Context context, int position) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.child("learnsets/" + position).removeValue();

        Toast.makeText(context, "Learnset removed", Toast.LENGTH_SHORT).show();
    }

    public static void getLearnsetsFromDatabase(Context context) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + userId);
        if(ref.child("learnsets") != null) {
            ref.child("learnsets").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<Learnset> learnsets = new ArrayList<>();
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
                        myUser.setLearnsets(learnsets);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
