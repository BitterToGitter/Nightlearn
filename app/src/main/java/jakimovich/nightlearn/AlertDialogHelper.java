package jakimovich.nightlearn;

import static androidx.core.app.ActivityCompat.finishAffinity;
import static jakimovich.nightlearn.MethodsHelper.onTouch;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogHelper {

    public static void showMenuAlertDialog(Activity activity, View vi) {
        activity.getApplicationContext();
        LinearLayout alertDialogMenu = vi.findViewById(R.id.llAlertDialogMenu);
        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_menu, alertDialogMenu);

        TextView tvCreateLearnsetsFolder = view.findViewById(R.id.tvCreateLearnsetsFolder);
        TextView tvCreateLearnset = view.findViewById(R.id.tvCreateLearnset);
        TextView tvCreateAlarm = view.findViewById(R.id.tvCreateAlarm);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        tvCreateLearnsetsFolder.setOnTouchListener((v, event) -> onTouch(v, event, tvCreateLearnsetsFolder));
        tvCreateLearnset.setOnTouchListener((v, event) -> onTouch(v, event, tvCreateLearnset));
        tvCreateAlarm.setOnTouchListener((v, event) -> onTouch(v, event, tvCreateAlarm));

        Window window = alertDialog.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(0));
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setGravity(Gravity.BOTTOM);

        alertDialog.show();
    }

    public static void showOptionsAlertDialog(Activity activity, View vi, String message, String accept, String decline,AlertAcceptClickListener acceptListener )
    {
        activity.getApplicationContext();
        LinearLayout alertDialogExit = vi.findViewById(R.id.llOptionsAlertMessage);
        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_exit, alertDialogExit);
        LinearLayout alertAccept = view.findViewById(R.id.llOptionsAlertAccept);
        LinearLayout alertDecline = view.findViewById(R.id.llOptionsAlertDecline);
        TextView tvMessage = view.findViewById(R.id.tvOptionsAlertDialogMessage);
        TextView tvAccept = view.findViewById(R.id.tvOptionsAlertDialogAccept);
        TextView tvDecline = view.findViewById(R.id.tvOptionsAlertDialogDecline);

        tvMessage.setText(message);
        tvAccept.setText(accept);
        tvDecline.setText(decline);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        alertAccept.setOnTouchListener((v, event) -> onTouch(v, event, tvAccept));
        alertDecline.setOnTouchListener((v, event) -> onTouch(v, event, tvDecline));
        alertAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptListener.onAlertAcceptClicked();
            }
        });
        alertDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
    }
}

