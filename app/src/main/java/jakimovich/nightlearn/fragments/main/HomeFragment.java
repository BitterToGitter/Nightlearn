package jakimovich.nightlearn.fragments.main;

import static jakimovich.nightlearn.helpers.UserService.myUser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.helpers.UserService;

public class HomeFragment extends Fragment {

    TextView tvTitle;
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

        if (UserService.isGuest()){
         tvTitle.setText("Nice to see you, Guest");
        }else {
        tvTitle.setText("Happy to see you, " + myUser.getNickname());
        }

        //Toast.makeText(getContext(), myUser.getProfilePic().getPath().toString(), Toast.LENGTH_SHORT).show();


    }
}