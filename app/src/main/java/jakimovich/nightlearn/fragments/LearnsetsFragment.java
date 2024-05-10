package jakimovich.nightlearn.fragments;

import static jakimovich.nightlearn.classes.UserService.myUser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.UserService;
import jakimovich.nightlearn.helpers.LearnsetAdapter;

public class LearnsetsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Learnset> learnsetsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learnsets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.learnsetsRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        learnsetsList = new ArrayList<>();

        learnsetsList.add(new Learnset("French", null));
        learnsetsList.add(new Learnset("Quantum Physics", null));
        //TODO: To set a reaction on touch

        recyclerView.setAdapter(new LearnsetAdapter(getContext(), learnsetsList));

    }
}