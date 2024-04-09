package jakimovich.nightlearn;

import static androidx.core.app.ActivityCompat.finishAffinity;
import static jakimovich.nightlearn.MethodsHelper.onTouch;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogHelper {

    public static void showMenuAlertDialog(Activity activity) {

        activity.getApplicationContext();

        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_menu, null);

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

    public static void showOptionsAlertDialog(Activity activity, String message, String accept, String decline, AlertAcceptClickListener acceptListener )
    {

        activity.getApplicationContext();

        View view = LayoutInflater.from(activity).inflate(R.layout.options_alert_dialog, null);

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

        alertAccept.setOnClickListener(v -> acceptListener.onAlertAcceptClicked());

        alertDecline.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
    }

    public static void showEditAlertDialog(Activity activity, String message, String editTextHint, String accept, String decline, AlertEnteredTextListener listener){
        activity.getApplicationContext();

        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_edit, null);

        LinearLayout alertAccept = view.findViewById(R.id.llAlertEditAccept);
        LinearLayout alertDecline = view.findViewById(R.id.llAlertEditDecline);

        TextView tvMessage = view.findViewById(R.id.tvAlertEditMessage);
        TextView tvAccept = view.findViewById(R.id.tvAlertEditAccept);
        TextView tvDecline = view.findViewById(R.id.tvAlertEditDecline);

        EditText editText = view.findViewById(R.id.alertEditEditText);

        if(message != null){tvMessage.setText(message);}
        if(accept != null){tvAccept.setText(accept);}
        if(decline != null){tvDecline.setText(decline);}
        if(editTextHint != null){editText.setHint(editTextHint);}

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        alertAccept.setOnTouchListener((v, event) -> onTouch(v, event, tvAccept));
        alertDecline.setOnTouchListener((v, event) -> onTouch(v, event, tvDecline));

        alertAccept.setOnClickListener(v -> {
                String output = editText.getText().toString();
                if (!output.isEmpty()) {
                    listener.onTextEntered(output);
                    alertDialog.dismiss();
                }
                alertDialog.dismiss();
        });

        alertDecline.setOnClickListener(v -> alertDialog.dismiss());


        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();

    }
}

