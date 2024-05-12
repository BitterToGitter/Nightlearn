package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVGParseException;

import java.util.List;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learncard;

public class LearncardAdapter extends RecyclerView.Adapter<LearncardAdapter.ViewHolder> {

    private List<Learncard> learncardList;
    private Context context;

    public LearncardAdapter(Context context, List<Learncard> learncardList) {
        this.context = context;
        this.learncardList = learncardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learncard_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Learncard learncard = learncardList.get(position);

        holder.tvDefinitionTitle.setText(Html.fromHtml("<u>Definition</u>"));
        holder.tvExplanationTitle.setText(Html.fromHtml("<u>Explanation</u>"));
        holder.llDefinition.setOnClickListener(v -> {AlertDialogHelper.showEditAlertDialog((Activity) context, "Update Definition", "Type Here...", "Update", "Cancel", definition -> {learncard.setDefinition(definition); onBindViewHolder(holder, position); });});
        holder.llExplanation.setOnClickListener(v -> {AlertDialogHelper.showEditAlertDialog((Activity) context, "Update Explanation", "Type Here...", "Update", "Cancel", explanation -> {learncard.setExplanation(explanation); onBindViewHolder(holder, position); });});
        holder.tvDefinition.setText(learncard.getDefinition());
        holder.tvExplanation.setText(learncard.getExplanation());

    }

    @Override
    public int getItemCount() {
        return learncardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDefinition, tvExplanation, tvDefinitionTitle, tvExplanationTitle;
        LinearLayout llDefinition, llExplanation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDefinition = itemView.findViewById(R.id.tvDefinition);
            tvExplanation = itemView.findViewById(R.id.tvExplanation);
            tvDefinitionTitle = itemView.findViewById(R.id.tvDefinitionTitle);
            tvExplanationTitle = itemView.findViewById(R.id.tvExplanationTitle);
            llDefinition = itemView.findViewById(R.id.llDefinition);
            llExplanation = itemView.findViewById(R.id.llExplanation);

        }
    }
}
