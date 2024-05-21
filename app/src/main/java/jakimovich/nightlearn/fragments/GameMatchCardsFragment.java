package jakimovich.nightlearn.fragments;

import static jakimovich.nightlearn.activities.AuthorInfoActivity.PROJECT_AUTHOR_INFO_GRAVITY;
import static jakimovich.nightlearn.activities.AuthorInfoActivity.PROJECT_INFO;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.PlayActivity;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class GameMatchCardsFragment extends Fragment {

    TextView tvRound;
    TextView tvTime;
    Learnset learnsetToPlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_match_cards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        learnsetToPlay = MethodsHelper.getLearnsetFromIntent(getActivity().getIntent());

        tvRound = view.findViewById(R.id.tvMatchCardsRound);
        tvRound.setText("Round " + getArguments().getInt("round"));

        tvTime = view.findViewById(R.id.tvMatchCardsTime);

        ((PlayActivity) getActivity()).startCountDownTimer(tvTime);

    }


}
