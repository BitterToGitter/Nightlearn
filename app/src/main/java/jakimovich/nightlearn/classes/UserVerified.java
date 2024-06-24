package jakimovich.nightlearn.classes;

import android.net.Uri;

import java.util.ArrayList;

public class UserVerified extends UserProfile {

    public UserVerified(String nickname, String name, String lastname, String eMail, String password, int points, ArrayList<Learnset> learnsets){
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
        this.eMail = eMail;
        this.password = password;
        this.learnsets = learnsets;
        this.points = points;
        this.gamesPlayed = getGamesPlayed();
        this.cardsLearned = getCardsLearned();
    }

    @Override
    public int getGamesPlayed() {
        int gamesPlayed = 0;
        for (Learnset learnset : learnsets) {
            gamesPlayed += learnset.getGamesPlayed();
        }
        return gamesPlayed;
    }

    @Override
    public int getCardsLearned() {
        int cardsLearned = 0;
        for (Learnset learnset : learnsets) {
            cardsLearned += learnset.countCardsLearned();
        }
        return cardsLearned;
    }

}
