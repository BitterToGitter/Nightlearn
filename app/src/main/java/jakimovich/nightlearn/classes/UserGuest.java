package jakimovich.nightlearn.classes;

import android.net.Uri;

import java.util.ArrayList;

import jakimovich.nightlearn.helpers.MethodsHelper;

public class UserGuest extends UserProfile {


    public UserGuest(){

        this.nickname = "Sign in to type";
        this.name = "Sign in to type";
        this.lastname = "Sign in to type";
        this.eMail = "Sign in to type";
        this.password = "Sign in to type";
        learnsets = MethodsHelper.createSampleLearnsets();

    }

    @Override
    public void setNickname(String nickname) {
        super.setNickname(nickname);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setLastname(String lastname) {
        super.setLastname(lastname);
    }

    @Override
    public void setEMail(String eMail) {
        super.setEMail(eMail);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void setProfilePic(Uri profilePic) {
        super.setProfilePic(profilePic);
    }

    @Override
    public void setLearnsets(ArrayList<Learnset> learnsets) {
        super.setLearnsets(learnsets);
    }
}
