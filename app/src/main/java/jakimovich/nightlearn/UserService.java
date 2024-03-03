package jakimovich.nightlearn;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class UserService {
    static UserProfile myUser;
    static Task<Void> setMyUser(UserProfile user){

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

    static Task<UserProfile> getUserById (String userId){

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

         if (Objects.equals(userId, FirebaseAuth.getInstance().getCurrentUser().getUid())){
             myUser = profile;
         }
         return profile;

     } else {
         throw task.getException();
     }
    });
    }

}
