package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import jakimovich.nightlearn.activities.RulesInfoActivity;
import jakimovich.nightlearn.interfaces.AlertAcceptClickListener;
import jakimovich.nightlearn.interfaces.AlertDialogDismissListener;
import jakimovich.nightlearn.interfaces.AlertEnteredTextListener;
import jakimovich.nightlearn.R;

public class AlertDialogHelper {
    static AlertDialog alertDialog;
    public static void showMenuAlertDialog(Activity activity) {

        activity.getApplicationContext();

        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_menu, null);

        TextView tvCreateLearnset = view.findViewById(R.id.tvCreateLearnset);

        TextView tvToTheRules = view.findViewById(R.id.tvToTheRules);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        alertDialog = builder.create();

        Window window = alertDialog.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(0));
        window.getAttributes().windowAnimations = R.style.MenuAlertDialogAnimation;
        window.setGravity(Gravity.BOTTOM);

        tvCreateLearnset.setOnClickListener(v -> {
            if (UserService.isGuest()){
                alertDialog.dismiss();
                showWarningAlertDialog(activity, "This option is available for registered users only. \n Sign in to make all kinds of learnsets!", "Ok", vi -> {});
            } else {
                GeneralHelper.createNewLearnset(activity);
                alertDialog.dismiss();
            }
        });

        tvToTheRules.setOnClickListener(v -> {activity.startActivity(new Intent(activity, RulesInfoActivity.class));});

        alertDialog.show();
    }

    public static void showOptionsAlertDialog(Activity activity, String message, String accept, String decline, AlertAcceptClickListener acceptListener, AlertDialogDismissListener dismissListener )
    {

        activity.getApplicationContext();

        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_options, null);

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
        alertDialog = builder.create();

        alertAccept.setOnClickListener(v -> {acceptListener.onAlertAcceptClicked(v); alertDialog.dismiss();});

        alertDecline.setOnClickListener(v -> {dismissListener.onDialogDismissed(v); alertDialog.dismiss();} );

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
    }

    public static void showEditAlertDialog(Activity activity, String message, String editTextHint, String accept, String decline, String previousInfo, AlertEnteredTextListener listener){
        activity.getApplicationContext();

        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_edit, null);

        LinearLayout alertAccept = view.findViewById(R.id.llAlertEditAccept);
        LinearLayout alertDecline = view.findViewById(R.id.llAlertEditDecline);

        TextView tvMessage = view.findViewById(R.id.tvAlertEditMessage);
        TextView tvAccept = view.findViewById(R.id.tvAlertEditAccept);
        TextView tvDecline = view.findViewById(R.id.tvAlertEditDecline);

        EditText editText = view.findViewById(R.id.alertEditEditText);
        editText.setText(previousInfo);

        if(message != null){tvMessage.setText(message);}
        if(accept != null){tvAccept.setText(accept);}
        if(decline != null){tvDecline.setText(decline);}
        if(editTextHint != null){editText.setHint(editTextHint);}

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        alertDialog = builder.create();

        alertAccept.setOnClickListener(v -> {
                String output = editText.getText().toString().trim();
                if (!output.isEmpty()) {
                    listener.onTextEntered(output);
                }
        });

        alertDecline.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();

    }

    public static void showWarningAlertDialog(Activity activity, String message, String closeBtnMessage, AlertDialogDismissListener listener){
        activity.getApplicationContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_warning, null);

        TextView tvMessage = view.findViewById(R.id.tvWarningAlertMessage);
        LinearLayout closeBtn = view.findViewById(R.id.llWarningAlertBtn);
        TextView closeBtnText = view.findViewById(R.id.tvWarningAlertBtn);

        tvMessage.setText(message);
        closeBtnText.setText(closeBtnMessage);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        alertDialog = builder.create();

        closeBtn.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.setOnDismissListener(v -> listener.onDialogDismissed(null));

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();

    }

    public static void showLoadingAlertDialog(Activity activity, String title, String message){

        activity.getApplicationContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_loading, null);

        TextView tvTitle = view.findViewById(R.id.tvAlertLoadingTitle);
        TextView tvMessage = view.findViewById(R.id.tvAlertLoadingMessage);

        tvTitle.setText(title);
        tvMessage.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();

    }

    public static void showPlayAlertDialog(Activity activity, String learnsetName, int questionsAmount, int timeForAnsweringQuestion, AlertDialogDismissListener listener){
        activity.getApplicationContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_play, null);

        TextView tvLearnsetName = view.findViewById(R.id.tvPlayAlertLearnsetName);
        TextView tvQuestionsAmount = view.findViewById(R.id.tvPlayAlertQuestionsAmount);
        TextView tvTimeForAnsweringQuestion = view.findViewById(R.id.tvPlayAlertTimeForAnsweringOneQuestion);
        LinearLayout closeBtn = view.findViewById(R.id.llWarningAlertBtn);

        tvLearnsetName.setText(learnsetName);
        tvQuestionsAmount.setText("Questions amount: " + questionsAmount + " questions");
        tvTimeForAnsweringQuestion.setText("Time for answering one question: " + timeForAnsweringQuestion + " sec.");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        alertDialog = builder.create();

        closeBtn.setOnClickListener(v -> {alertDialog.dismiss(); listener.onDialogDismissed(v);});

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();

    }

    public static void dismissAlertDialog(){
        alertDialog.dismiss();
    }
//Todo: to copy again
}

