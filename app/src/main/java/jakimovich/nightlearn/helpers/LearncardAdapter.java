package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVGParseException;

import java.util.List;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;

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
        holder.llDefinition.setOnClickListener(v -> AlertDialogHelper.showEditAlertDialog((Activity) context, "Update Definition", "Type Here...", "Update", "Cancel", definition -> {learncard.setDefinition(definition); notifyDataSetChanged(); }));
        holder.llExplanation.setOnClickListener(v -> AlertDialogHelper.showEditAlertDialog((Activity) context, "Update Explanation", "Type Here...", "Update", "Cancel", explanation -> {learncard.setExplanation(explanation); notifyDataSetChanged(); }));
        holder.tvDefinition.setText(learncard.getDefinition());
        holder.tvExplanation.setText(learncard.getExplanation());

        holder.btnOptionsMenu.setOnClickListener(v -> holder.showPopupWindow(v, holder, position));

    }

    @Override
    public int getItemCount() {
        return learncardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDefinition, tvExplanation, tvDefinitionTitle, tvExplanationTitle;
        LinearLayout llDefinition, llExplanation;
        ImageView btnOptionsMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDefinition = itemView.findViewById(R.id.tvDefinition);
            tvExplanation = itemView.findViewById(R.id.tvExplanation);
            tvDefinitionTitle = itemView.findViewById(R.id.tvDefinitionTitle);
            tvExplanationTitle = itemView.findViewById(R.id.tvExplanationTitle);
            llDefinition = itemView.findViewById(R.id.llDefinition);
            llExplanation = itemView.findViewById(R.id.llExplanation);

            btnOptionsMenu = itemView.findViewById(R.id.learncardOptionsMenuBtn);
            btnOptionsMenu.setImageDrawable(MethodsHelper.convertSvgToDrawable(context, R.raw.ic_learnsets_options_menu_button));

        }

        private void showPopupWindow(View view, ViewHolder holder, int position) {

            View popupView = LayoutInflater.from(context).inflate(R.layout.menu_learncard_layout, null);

            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            TextView tvDelete = popupView.findViewById(R.id.tvLearncardMenuDelete);

            tvDelete.setOnClickListener(v -> {
                popupWindow.dismiss();
                learncardList.remove(position);
                notifyDataSetChanged();
            });

            popupWindow.setBackgroundDrawable(new ColorDrawable(0));

            popupWindow.showAsDropDown(view);
        }

    }
}

