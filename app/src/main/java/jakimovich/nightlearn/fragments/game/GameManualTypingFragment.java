package jakimovich.nightlearn.fragments.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.helpers.MethodsHelper;

public class GameManualTypingFragment extends GameFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_manual_typing, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    protected void onRightAnswer(){
        super.onRightAnswer();
    }

    @Override
    protected void onWrongAnswer(){
        super.onRightAnswer();
    };

}
