package jakimovich.nightlearn.classes;

import android.net.Uri;

import java.util.ArrayList;

public class UserVerified extends UserProfile {



    public UserVerified(String nickname, String name, String lastname, String eMail, String password){
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
        this.eMail = eMail;
        this.password = password;
        learnsets = new ArrayList<>();
    }

    public UserVerified(String nickname, String name, String lastname, String eMail, String password, ArrayList<Learnset> learnsets){
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
        this.eMail = eMail;
        this.password = password;
        this.learnsets = learnsets;
    }


}
