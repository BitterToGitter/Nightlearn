package jakimovich.nightlearn.classes;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private String nickname;
    private String name;
    private String lastname;
    private String eMail;
    private String password;
    private Uri profilePic;
    private ArrayList<Learnset> learnsets;

    public UserProfile (String nickname, String name, String lastname, String eMail, String password){
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
        this.eMail = eMail;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }
    public String getName() {
        return name;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEMail() {
        return eMail;
    }
    public String getPassword() {
        return password;
    }

    public Uri getProfilePic() {
        return profilePic;
    }
    public ArrayList<Learnset> getLearnsets() {return learnsets;}

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfilePic(Uri profilePic) {
        this.profilePic = profilePic;
    }

    public void setLearnsets(ArrayList<Learnset> learnsets) {this.learnsets = learnsets;}
}
