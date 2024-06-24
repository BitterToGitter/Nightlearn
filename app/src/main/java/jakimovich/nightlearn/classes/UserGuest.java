package jakimovich.nightlearn.classes;

import jakimovich.nightlearn.helpers.GeneralHelper;

public class UserGuest extends UserProfile {


    public UserGuest(){

        this.nickname = "Sign in to type";
        this.name = "Sign in to type";
        this.lastname = "Sign in to type";
        this.eMail = "Sign in to type";
        this.password = "Sign in to type";
        this.points = 0;
        this.gamesPlayed = 0;
        this.cardsLearned = 0;
        learnsets = GeneralHelper.createSampleLearnsets();

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
