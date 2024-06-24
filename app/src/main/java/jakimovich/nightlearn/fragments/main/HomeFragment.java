package jakimovich.nightlearn.fragments.main;

import static jakimovich.nightlearn.helpers.UserService.myUser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.MainActivity;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.helpers.HomeLearnsetAdapter;
import jakimovich.nightlearn.helpers.LearnsetsLearnsetAdapter;
import jakimovich.nightlearn.helpers.UserService;

public class HomeFragment extends Fragment {

    TextView tvTitle;
    TextView tvGamesPlayed, tvCardsLearned, tvPointsEarned, tvRatingPlace;
    LinearLayout llRatingList;
    RecyclerView recyclerView;
    ArrayList<Learnset> learnsetsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = view.findViewById(R.id.tvTitleHome);

        tvGamesPlayed = view.findViewById(R.id.tvHomeGamesPlayed);
        tvCardsLearned = view.findViewById(R.id.tvHomeCardsLearned);
        tvPointsEarned = view.findViewById(R.id.tvHomePointsEarned);
        tvRatingPlace = view.findViewById(R.id.tvHomeRatingPlace);

        llRatingList = view.findViewById(R.id.llHomeRatingList);
        llRatingList.setOnClickListener(v -> ((MainActivity) getActivity()).replaceFragmentWithMenuItem(new RatingFragment(), R.id.menuRating));

        recyclerView = view.findViewById(R.id.homeRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        updateUI();

    }

    private void updateUI() {
        if (UserService.isGuest()){
            tvTitle.setText("Nice to see you, Guest");
        }else {
            tvTitle.setText("Happy to see you, " + myUser.getNickname());
        }

        tvGamesPlayed.setText("Games played: " + myUser.getGamesPlayed());
        tvCardsLearned.setText("Cards learned: " + myUser.getCardsLearned());
        tvPointsEarned.setText("Points earned: " + myUser.getPoints());
        UserService.getRatingPlace(getContext(), myUser.getNickname(), place -> {if (place == -1) {tvRatingPlace.setText("Rating place: N/A");} else {tvRatingPlace.setText("Rating place: #" + place);}});

        learnsetsList = new ArrayList<>();
        if(myUser.getLearnsets() != null){
            for (Learnset learnset : myUser.getLearnsets()){
                if(learnset.getGamesPlayed() > 0){
                    learnsetsList.add(learnset);
                }
            }
        }
        recyclerView.setAdapter(new HomeLearnsetAdapter(getContext(), learnsetsList));
    }
}