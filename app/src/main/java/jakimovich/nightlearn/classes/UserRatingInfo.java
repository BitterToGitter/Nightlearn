package jakimovich.nightlearn.classes;

public class UserRatingInfo extends UserProfile {
    private String userId;
    public UserRatingInfo(String userId, String userNickname, int gamesPlayed, int cardsLearned, int pointsEarned) {
        this.userId = userId;
        this.nickname = userNickname;
        this.gamesPlayed = gamesPlayed;
        this.cardsLearned = cardsLearned;
        this.points = pointsEarned;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
