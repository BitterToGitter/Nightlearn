package jakimovich.nightlearn.fragments.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.PlayActivity;

public class GameResultsFragment extends Fragment {

    TextView roundsPlayed, rightAnswersNum, cardsLearnedNum, pointsEarned;
    LinearLayout btnBackHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundsPlayed = view.findViewById(R.id.gameResultRoundsPlayed);
        rightAnswersNum = view.findViewById(R.id.gameResultRightAnswersGiven);
        cardsLearnedNum = view.findViewById(R.id.gameResultCardsLearned);
        pointsEarned = view.findViewById(R.id.gameResultPointsEarned);
        btnBackHome = view.findViewById(R.id.gameResultBtnBackHome);

        roundsPlayed.setText("Rounds played: " );
        rightAnswersNum.setText("Right answers given: ");
        cardsLearnedNum.setText("Cards learned for session: ");
        pointsEarned.setText("Points earned: ");
        //Todo: to end up with these

        btnBackHome.setOnClickListener(v -> getActivity().finish());

    }



}