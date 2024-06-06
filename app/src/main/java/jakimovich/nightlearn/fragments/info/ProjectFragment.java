package jakimovich.nightlearn.fragments.info;

import static jakimovich.nightlearn.activities.AboutProjectActivity.PROJECT_AUTHOR_INFO_GRAVITY;
import static jakimovich.nightlearn.activities.AboutProjectActivity.PROJECT_INFO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import jakimovich.nightlearn.R;

public class ProjectFragment extends Fragment {

    TextView tvProjectInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvProjectInfo = view.findViewById(R.id.tvProjectInfo);
        tvProjectInfo.setText(PROJECT_INFO);
        tvProjectInfo.setGravity(PROJECT_AUTHOR_INFO_GRAVITY);

    }

    public void updateTextView() {
        if (tvProjectInfo != null) {
            tvProjectInfo.setText(PROJECT_INFO);
            tvProjectInfo.setGravity(PROJECT_AUTHOR_INFO_GRAVITY);
        }
    }

}
