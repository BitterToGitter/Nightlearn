package jakimovich.nightlearn.helpers;
import static jakimovich.nightlearn.helpers.AlertDialogHelper.showWarningAlertDialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.LearnsetEditActivity;
import jakimovich.nightlearn.activities.PlayActivity;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.UserService;

public class LearnsetAdapter extends RecyclerView.Adapter<LearnsetAdapter.ViewHolder> {

    private List<Learnset> learnsetList;
    private Context context;

    public LearnsetAdapter(Context context, List<Learnset> learnsetList) {
        this.context = context;
        this.learnsetList = learnsetList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learnset_layout_for_lernsets_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Learnset learnset = learnsetList.get(position);

        holder.tvLearnsetTitle.setText(learnset.getName());
        holder.tvCardsNum.setText("Cards amount: " + learnset.getLearncards().size());
        holder.tvSeenCards.setText("Cards seen in games: " + learnset.countCardsSeen());
        holder.tvLearnedCards.setText("Cards learned: " + learnset.countCardsLearned());
        holder.tvLearnsetProgress.setText("Progress: " + learnset.countProgress() + "%");
        holder.progressBar.setProgress(learnset.countProgress());

        holder.optionsMenu.setOnClickListener(v -> holder.showPopupWindow(v, learnset, position));
        holder.llLearncardPresentationBase.setOnClickListener(v -> AlertDialogHelper.showPlayAlertDialog((Activity) context, learnset.getName(), learnset.getQuizSettings().getQuestionsAmount(), learnset.getQuizSettings().getAnswerTimeSec(), v1 -> {((Activity) context).startActivityForResult(MethodsHelper.putLearnsetIntoIntent((Activity) context, learnset, position, PlayActivity.class), 0);}));

    }

    @Override
    public int getItemCount() {
        return learnsetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llLearncardPresentationBase;
        TextView tvLearnsetTitle, tvCardsNum, tvSeenCards, tvLearnedCards, tvLearnsetProgress;
        ImageView optionsMenu;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLearnsetTitle = itemView.findViewById(R.id.tvlearnsetTitle);
            tvCardsNum = itemView.findViewById(R.id.tvLearnsetCardsNum);
            tvSeenCards = itemView.findViewById(R.id.tvLearnsetSeenCards);
            tvLearnedCards = itemView.findViewById(R.id.tvLearnsetLearnedCards);
            tvLearnsetProgress = itemView.findViewById(R.id.tvLearnsetProgress);
            llLearncardPresentationBase = itemView.findViewById(R.id.llLearncardPresentationBase);
            progressBar = itemView.findViewById(R.id.progressBar);

            optionsMenu = itemView.findViewById(R.id.ivOptionsMenuButton);
            optionsMenu.setImageDrawable(MethodsHelper.convertSvgToDrawable(context, R.raw.ic_learnsets_options_menu_button));

        }

        private void showPopupWindow(View view, Learnset learnset, int position) {

            View popupView = LayoutInflater.from(context).inflate(R.layout.popup_menu_learnsets_layout, null);

            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            TextView tvEdit = popupView.findViewById(R.id.tvLearnsetsMenuEdit);
            TextView tvDelete = popupView.findViewById(R.id.tvLearnsetsMenuDelete);

            tvEdit.setOnClickListener(v -> {
                if (UserService.isGuest()){
                    showWarningAlertDialog((Activity) context, "This option is available for registered users only. \n Sign in to make all kinds of learnsets!", "Ok");
                    popupWindow.dismiss();
                } else {
                    ((Activity) context).startActivityForResult(MethodsHelper.putLearnsetIntoIntent((Activity) context, learnset, position, LearnsetEditActivity.class), 0);
                    popupWindow.dismiss();
                }
            });

            tvDelete.setOnClickListener(v -> {
                if(UserService.isGuest()){
                    showWarningAlertDialog((Activity) context, "Only registered users can delete the sample learnsets. \n Sign in to do manage the learnsets as you wish.", "Ok");
                    popupWindow.dismiss();
                } else{
                AlertDialogHelper.showOptionsAlertDialog((Activity) context, "Are you sure you want to delete the learnset? There will be no way to return in.", "Delete","Cancel", v1 -> {learnsetList.remove(position); UserService.removeLearnset(context, position); notifyDataSetChanged();});
                popupWindow.dismiss();
                }
            });

            popupWindow.setBackgroundDrawable(new ColorDrawable(0));

            popupWindow.showAsDropDown(view);
        }

        }

    }

