package jakimovich.nightlearn.classes;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import jakimovich.nightlearn.helpers.MethodsHelper;

public class UserService {
    public static UserProfile myUser;

    public static Task<Void> setMyUser(UserProfile user){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference("users/" + userId);

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("nickname", user.getNickname());
        userMap.put("name", user.getName());
        userMap.put("lastname", user.getLastname());
        userMap.put("eMail", user.getEMail());
        userMap.put("password", user.getPassword());

        user.setLearnsets(new ArrayList<>());

        return ref.setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                myUser = user;
            }
        });

    }

    public static Task<UserProfile> getUserById(String userId){

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users/" + userId);

    return ref.get().continueWith(task -> {
     if (task.isSuccessful()){

         String nickname = task.getResult().child("nickname").getValue(String.class);
         String name = task.getResult().child("name").getValue(String.class);
         String lastname = task.getResult().child("lastname").getValue(String.class);
         String eMail = task.getResult().child("eMail").getValue(String.class);
         String password = task.getResult().child("password").getValue(String.class);


         UserProfile profile = new UserProfile(nickname, name, lastname, eMail, password);
         profile.setLearnsets(new ArrayList<>());
         //TODO: To import learnsets from database;

         if (Objects.equals(userId, FirebaseAuth.getInstance().getCurrentUser().getUid())){
             myUser = profile;
         }
            Uri profilePic = Uri.parse(task.getResult().child("profilePic").getValue(String.class));
            if (profilePic != null){
             myUser.setProfilePic(profilePic);
            }


         return profile;

     } else {
         throw task.getException();
     }
    });
    }

    public static Task<Void> uploadProfilePic(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference("users/" + userId);

        return ref.child("profilePic").setValue(myUser.getProfilePic().toString());

    }

    public static boolean isGuest(){
     return FirebaseAuth.getInstance().getCurrentUser().isAnonymous();
    }

}
