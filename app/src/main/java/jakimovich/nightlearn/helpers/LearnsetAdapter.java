package jakimovich.nightlearn.helpers;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

        // Bind data to TextViews
        holder.tvLearnsetTitle.setText(learnset.getName());
        holder.tvCardsNum.setText("Cards amount: " + learnset.getCardsInTotal());
        holder.tvSeenCards.setText("Cards seen in games: " + learnset.cardsSeen());
        holder.tvLearnedCards.setText("Learned cards: " + learnset.cardsLearned());
        holder.tvLearnsetProgress.setText("Progress: " + learnset.getProgress() + "%");
        // Similarly bind other TextViews
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

            optionsMenu.setOnClickListener(v -> showPopupWindow(v));

        }

        private void showPopupWindow(View view) {

            View popupView = LayoutInflater.from(context).inflate(R.layout.menu_learnsets_layout, null);

            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            TextView tvEdit = popupView.findViewById(R.id.tvLearnsetsMenuEdit);
            TextView tvDelete = popupView.findViewById(R.id.tvLearnsetsMenuDelete);

            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, LearnsetEditActivity.class));
                    popupWindow.dismiss();
                }
            });

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform delete action
                    popupWindow.dismiss();
                }
            });

            popupWindow.setBackgroundDrawable(new ColorDrawable(0));

            popupWindow.showAsDropDown(view);
        }

        }

    }

