package jakimovich.nightlearn.fragments.main;

import static jakimovich.nightlearn.activities.AuthorInfoActivity.AUTHOR_INFO;
import static jakimovich.nightlearn.activities.AuthorInfoActivity.PROJECT_AUTHOR_INFO_GRAVITY;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import jakimovich.nightlearn.R;

public class AuthorFragment extends Fragment {

    TextView tvAuthorInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_author, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAuthorInfo = view.findViewById(R.id.tvAuthorInfo);
        tvAuthorInfo.setText(AUTHOR_INFO);
        tvAuthorInfo.setGravity(PROJECT_AUTHOR_INFO_GRAVITY);

    }

    public void updateTextView() {
        if (tvAuthorInfo != null) {
            tvAuthorInfo.setText(AUTHOR_INFO);
            tvAuthorInfo.setGravity(PROJECT_AUTHOR_INFO_GRAVITY);
        }
    }

}
