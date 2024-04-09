package jakimovich.nightlearn;

import static jakimovich.nightlearn.AlertDialogHelper.showEditAlertDialog;
import static jakimovich.nightlearn.AlertDialogHelper.showOptionsAlertDialog;
import static jakimovich.nightlearn.MethodsHelper.signOut;
import static jakimovich.nightlearn.MethodsHelper.updateUserLastname;
import static jakimovich.nightlearn.MethodsHelper.updateUserName;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.InputStream;

public class ProfileFragment extends Fragment {

    ImageView imageViewProfile;
    TextView profileName, profileLastname;
    Button btnSignOut, btnExit, btnDeleteAccount;
    ImageButton ibEditName, ibEditLastname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileName = view.findViewById(R.id.tvProfileName);
        profileName.setText(UserService.myUser.getName());

        profileLastname = view.findViewById(R.id.tvProfileLastname);
        profileLastname.setText(UserService.myUser.getLastname());

        ibEditName = view.findViewById(R.id.ibEditName);
        ibEditName.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Enter your Name", "Type here...", "Update", "Cancel", this::updateProfileUserName ));

        ibEditLastname = view.findViewById(R.id.ibEditLastname);
        ibEditName.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Enter your Lastname", "Type here...", "Update", "Cancel", this::updateProfileUserLastname ));

        imageViewProfile = view.findViewById(R.id.imageViewProfile);

        btnExit = view.findViewById(R.id.btnProfileExit);
        btnExit.setOnClickListener((t) -> showOptionsAlertDialog(getActivity(), "Are you sure you want to exit?", "Yeah \n Let's get out", "Nope \n Back to study", this::finishAffinity));

        btnDeleteAccount = view.findViewById(R.id.btnProfileDeleteAccount);

        btnSignOut = view.findViewById(R.id.btnProfileSignOut);
        btnSignOut.setOnClickListener(v -> signOut(getContext()));


        setProfilePhoto(imageViewProfile);


    }

    private void setProfilePhoto(ImageView imageView){
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.profile);
            SVG svg = SVG.getFromInputStream(inputStream);
            PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());
            imageView.setImageDrawable(drawable);
        } catch (SVGParseException e) {
            e.printStackTrace();
        }

    }

    private void finishAffinity() {
        if (getActivity() != null) {
            getActivity().finishAffinity();
        }
    }

    private void updateProfileUserName(String name){
        updateUserName(getContext(), name);
    }
    private void updateProfileUserLastname(String lastname){
        updateUserLastname(getContext(), lastname);
    }
    private void editProfileName(){

    }

}

