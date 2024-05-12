package jakimovich.nightlearn.helpers;
import static jakimovich.nightlearn.helpers.AlertDialogHelper.showWarningAlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVGParseException;

import java.util.List;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.LearnsetEditActivity;
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
        try {
            return new ViewHolder(view);
        } catch (SVGParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Learnset learnset = learnsetList.get(position);

        holder.tvLearnsetTitle.setText(learnset.getName());
        holder.tvCardsNum.setText("Cards amount: " + learnset.getLearncards().size());
        holder.tvSeenCards.setText("Cards seen in games: " + learnset.cardsSeen());
        holder.tvLearnedCards.setText("Learned cards: " + learnset.cardsLearned());
        holder.tvLearnsetProgress.setText("Progress: " + learnset.countProgress() + "%");

        holder.optionsMenu.setOnClickListener(v -> holder.showPopupWindow(v, learnset));


    }

    @Override
    public int getItemCount() {
        return learnsetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLearnsetTitle, tvCardsNum, tvSeenCards, tvLearnedCards, tvLearnsetProgress;
        ImageView optionsMenu;
        public ViewHolder(@NonNull View itemView) throws SVGParseException {
            super(itemView);
            tvLearnsetTitle = itemView.findViewById(R.id.tvlearnsetTitle);
            tvCardsNum = itemView.findViewById(R.id.tvLearnsetCardsNum);
            tvSeenCards = itemView.findViewById(R.id.tvLearnsetSeenCards);
            tvLearnedCards = itemView.findViewById(R.id.tvLearnsetLearnedCards);
            tvLearnsetProgress = itemView.findViewById(R.id.tvLearnsetProgress);
            optionsMenu = itemView.findViewById(R.id.ivOptionsMenuButton);
            optionsMenu.setImageDrawable(MethodsHelper.convertSvgToDrawable(context, R.raw.ic_learnsets_options_menu_button));

        }

        private void showPopupWindow(View view, final Learnset learnset) {

            View popupView = LayoutInflater.from(context).inflate(R.layout.menu_learnsets_layout, null);

            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            TextView tvEdit = popupView.findViewById(R.id.tvLearnsetsMenuEdit);
            TextView tvDelete = popupView.findViewById(R.id.tvLearnsetsMenuDelete);

            tvEdit.setOnClickListener(v -> {
                if (UserService.isGuest()){
                    showWarningAlertDialog((Activity) context, "This option is available for registered users only. \n Sign in to make all kinds of learnsets!", "Ok");
                    popupWindow.dismiss();
                } else {
                    context.startActivity(MethodsHelper.putLearnsetIntoIntent((Activity) context, learnset));
                    popupWindow.dismiss();
                }
            });

            tvDelete.setOnClickListener(v -> {
                // Perform delete action
                popupWindow.dismiss();
            });

            popupWindow.setBackgroundDrawable(new ColorDrawable(0));

            popupWindow.showAsDropDown(view);
        }

        }

    }

