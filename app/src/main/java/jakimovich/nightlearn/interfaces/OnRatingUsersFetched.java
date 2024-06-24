package jakimovich.nightlearn.interfaces;

import java.util.ArrayList;

import jakimovich.nightlearn.classes.UserRatingInfo;

public interface OnRatingUsersFetched {
    void onFetched(ArrayList<UserRatingInfo> ratingUsers);

}
