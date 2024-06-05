package jakimovich.nightlearn.classes;

import android.net.Uri;

import java.util.ArrayList;

public abstract class UserProfile  {

    protected String nickname;
    protected String name;
    protected String lastname;
    protected String eMail;
    protected String password;
    protected Uri profilePic;
    protected ArrayList<Learnset> learnsets;

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
    }//TODO: Uri isn't a way to upload an image. It wouldn't appear

    public void setLearnsets(ArrayList<Learnset> learnsets) {this.learnsets = learnsets;}




}
