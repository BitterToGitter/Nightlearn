package jakimovich.nightlearn.fragments.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.helpers.RatingAdapter;
import jakimovich.nightlearn.helpers.UserService;

public class RatingFragment extends Fragment {

    RecyclerView recyclerView;
    TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rating, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = view.findViewById(R.id.tvRatingFragmentTitle);
        tvTitle.setText("Rating list is loading, just a moment...");

        recyclerView = view.findViewById(R.id.ratingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        UserService.getRatingUsersList(getContext(), ratingUsers -> {
            recyclerView.setAdapter(new RatingAdapter(getContext(), ratingUsers));
            tvTitle.setText("See others' success!");
        });

    }
}