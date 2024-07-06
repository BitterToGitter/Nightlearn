package jakimovich.nightlearn.helpers;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.List;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.UserRatingInfo;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    List<UserRatingInfo> users;
    Context context;

    public RatingAdapter(Context context, List<UserRatingInfo> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_user_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RatingAdapter.ViewHolder holder, int position) {

        UserRatingInfo user = users.get(position);

        holder.tvNickname.setText("User: " + user.getNickname());
        holder.tvGamesPlayed.setText("Games played: " + user.getGamesPlayed());
        holder.tvCardsLearned.setText("Cards learned: " + user.getCardsLearned());
        holder.tvPointsEarned.setText("Points in total: " + user.getPoints());
        holder.tvPlace.setText("#" + (position + 1));
        holder.ivUserPicture.setImageDrawable(GeneralHelper.convertSvgToDrawable(context, R.raw.profile));

        setRatingUserProfilePicture(context, user, holder.ivUserPicture);

        if (user.getNickname().equals(UserService.myUser.getNickname())){
            holder.tvNickname.setText("User: " + user.getNickname() + " (You)");
            holder.tvNickname.setTextColor(context.getResources().getColor(R.color.myUserInRatingList));
            holder.tvGamesPlayed.setTextColor(context.getResources().getColor(R.color.myUserInRatingList));
            holder.tvCardsLearned.setTextColor(context.getResources().getColor(R.color.myUserInRatingList));
            holder.tvPointsEarned.setTextColor(context.getResources().getColor(R.color.myUserInRatingList));
            holder.tvPlace.setTextColor(context.getResources().getColor(R.color.myUserInRatingList));
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics()));

        if (position == 0) {
            holder.upperSeparator.setLayoutParams(params);
        }
        if (position == users.size() - 1) {
            holder.lowerSeparator.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNickname, tvGamesPlayed, tvCardsLearned, tvPointsEarned, tvPlace;
        ImageView ivUserPicture;
        View upperSeparator, lowerSeparator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserPicture = itemView.findViewById(R.id.ivRatingUserPicture);
            tvNickname = itemView.findViewById(R.id.tvRatingNickname);
            tvGamesPlayed = itemView.findViewById(R.id.tvRatingGamesPlayed);
            tvCardsLearned = itemView.findViewById(R.id.tvRatingCardsLearned);
            tvPointsEarned = itemView.findViewById(R.id.tvRatingPointsEarned);
            tvPlace = itemView.findViewById(R.id.tvRatingUserPlace);
            upperSeparator = itemView.findViewById(R.id.vRatingUserUpperSeparator);
            lowerSeparator = itemView.findViewById(R.id.vRatingUserLowerSeparator);
        }


    }
    private void setRatingUserProfilePicture(Context context, UserRatingInfo user, ImageView ivUserPicture) {

        StorageReference ratingProfilePicRef = FirebaseStorage.getInstance().getReference("users/" + user.getUserId() + "/profilePic.jpg");

        ratingProfilePicRef.getDownloadUrl().addOnSuccessListener(uri -> {
            ImageFilesManager.setPicIntoImageView(context, uri, ivUserPicture);
        });
    }
}
