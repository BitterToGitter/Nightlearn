package jakimovich.nightlearn.fragments.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.PlayActivity;
import jakimovich.nightlearn.helpers.AlertDialogHelper;

public class GamePreviewFragment extends GameFragment {

    //Todo: to copy again
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

        tvPreCountDown = view.findViewById(R.id.tvPreCountDown);
        tvReadySteadyGo = view.findViewById(R.id.tvReadySteadyGo);

        startCountDownTimer(tvPreCountDown);

    }

    @Override
    public void startCountDownTimer(TextView timeUpdate) {

        long previewTime;

        if (timeLeftInMillis != 0) {
            previewTime = timeLeftInMillis;
        } else {
            previewTime = 3000;
        }
        countDownTimer = new CountDownTimer(previewTime, 1000){

            @Override
            public void onTick(long l) {

                timeLeftInMillis = l;

                timeUpdate.setText(((l / 1000) + 1) + " ");

                switch (timeUpdate.getText().toString()) {

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
                timeLeftInMillis = 0;
                ((PlayActivity) getActivity()).goNextRound();
            }
        }.start();
    }

    @Override
    public void onGamePaused() {
        if (timeLeftInMillis != 0){
            countDownTimer.cancel();
        }
        AlertDialogHelper.showOptionsAlertDialog(getActivity(), "The game is paused \nChoose your next action", "Stop and exit the game", "Continue playing", v -> getActivity().finish(), v -> { if(timeLeftInMillis != 0){startCountDownTimer((tvPreCountDown));}});
    }
}