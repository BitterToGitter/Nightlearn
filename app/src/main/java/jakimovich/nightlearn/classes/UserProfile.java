package jakimovich.nightlearn.classes;

import android.net.Uri;

import java.util.ArrayList;

public abstract class UserProfile  {

    //Todo: To copy again

    protected String nickname;
    protected String name;
    protected String lastname;
    protected String eMail;
    protected String password;
    protected ArrayList<Learnset> learnsets;

    protected int points;
    protected int gamesPlayed;
    protected int cardsLearned;

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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getCardsLearned() {
        return cardsLearned;
    }

    public int getPoints() {return points;}
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
    public void setPoints(int points) {this.points = points;}

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setCardsLearned(int cardsLearned) {
        this.cardsLearned = cardsLearned;
    }

    public void setLearnsets(ArrayList<Learnset> learnsets) {this.learnsets = learnsets;}

    public void addPoints(int points) {this.points += points;}

    public void removePoints(int points) {this.points -= points;}
}
