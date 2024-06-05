package jakimovich.nightlearn.fragments.main;

import static jakimovich.nightlearn.helpers.UserService.myUser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.helpers.LearnsetAdapter;

public class LearnsetsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Learnset> learnsetsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_learnsets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.learnsetsRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

          if(myUser.getLearnsets() != null){
             learnsetsList = myUser.getLearnsets();
              recyclerView.setAdapter(new LearnsetAdapter(getContext(), learnsetsList));
          }
    }


}