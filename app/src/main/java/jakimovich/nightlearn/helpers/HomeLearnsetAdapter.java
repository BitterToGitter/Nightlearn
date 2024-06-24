package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.MainActivity;
import jakimovich.nightlearn.activities.PlayActivity;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.fragments.main.LearnsetsFragment;

public class HomeLearnsetAdapter extends RecyclerView.Adapter<HomeLearnsetAdapter.ViewHolder>{

    private List<Learnset> learnsetList;

    private int size;
    private Context context;

    public HomeLearnsetAdapter(Context context, List<Learnset> learnsetList) {
        this.context = context;
        this.learnsetList = learnsetList;

        if (learnsetList != null) {
            size = learnsetList.size();
        } else {
            size = 0;
        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learnset_layout_for_home_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLearnsetAdapter.ViewHolder holder, int position) {

        if (position == size) {

            holder.divider.setVisibility(View.GONE);
            holder.tvGamesPlayed.setVisibility(View.GONE);
            holder.tvCardsLearned.setVisibility(View.GONE);
            holder.tvProgress.setVisibility(View.GONE);

            holder.learnsetBaseLayout.setBackground(context.getResources().getDrawable(R.drawable.flashcard_add_new_learnset_background));

            holder.tvLearnsetTitle.setText("Start a new learnset!");
            holder.tvLearnsetTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            holder.tvLearnsetTitle.setGravity(Gravity.CENTER);
            holder.tvLearnsetTitle.setTextColor(context.getResources().getColor(R.color.brightGray));

            holder.learnsetLayout.setOnClickListener(v -> ((MainActivity) context).replaceFragmentWithMenuItem(new LearnsetsFragment(), R.id.menuLearnsets));
            return;
        }

    Learnset learnset = learnsetList.get(position);

        holder.tvLearnsetTitle.setText(learnset.getName());
        holder.tvGamesPlayed.setText("Games played: " + learnset.getGamesPlayed());
        holder.tvCardsLearned.setText("Cards learned: " + learnset.countCardsLearned() + "/" + learnset.getLearncards().size());
        holder.tvProgress.setText("Completed: " + learnset.countProgress() + "%");

        holder.learnsetLayout.setOnClickListener(v -> AlertDialogHelper.showPlayAlertDialog((Activity) context, learnset.getName(), learnset.getQuizSettings().getQuestionsAmount(), learnset.getQuizSettings().getAnswerTimeSec(), v1 -> {((Activity) context).startActivityForResult(GeneralHelper.putLearnsetIntoIntent((Activity) context, learnset, position, PlayActivity.class), 0);}));

    }

    @Override
    public int getItemCount() {
        return size + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout learnsetBaseLayout, learnsetLayout;
        TextView tvLearnsetTitle;
        TextView tvGamesPlayed;
        TextView tvCardsLearned;
        TextView tvProgress;
        View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            learnsetBaseLayout = itemView.findViewById(R.id.homeLearnsetBaseLayout);
            learnsetLayout = itemView.findViewById(R.id.homeLearnsetLayout);
            tvLearnsetTitle = itemView.findViewById(R.id.tvHomeLearnsetLayoutTitle);
            tvGamesPlayed = itemView.findViewById(R.id.tvHomeLearnsetLayoutGamesPlayed);
            tvCardsLearned = itemView.findViewById(R.id.tvHomeLearnsetLayoutCardsLearned);
            tvProgress = itemView.findViewById(R.id.tvHomeLearnsetLayoutProgress);
            divider = itemView.findViewById(R.id.homeLearnsetDivider);

        }
    }
}
