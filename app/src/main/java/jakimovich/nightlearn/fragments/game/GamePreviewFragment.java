package jakimovich.nightlearn.fragments.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.PlayActivity;

public class GamePreviewFragment extends Fragment {

    TextView tvPreCountDown, tvReadySteadyGo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPreCountDown = view.findViewById(R.id.tvPreCountDown);
        tvReadySteadyGo = view.findViewById(R.id.tvReadySteadyGo);

        new CountDownTimer(3000, 1000){

            @Override
            public void onTick(long l) {
                tvPreCountDown.setText(((l / 1000) + 1) + " ");

                switch (tvPreCountDown.getText().toString()) {

                    case "3 ":
                        tvReadySteadyGo.setText("Ready");
                        break;

                    case "2 ":
                        tvReadySteadyGo.setText("Steady");
                        break;

                    case "1 ":
                        tvReadySteadyGo.setText("GO!");
                        break;
                }
            }

            @Override
            public void onFinish() {
                ((PlayActivity) getActivity()).goNextRound();
            }
        }.start();

    }
}