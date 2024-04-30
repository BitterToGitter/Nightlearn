package jakimovich.nightlearn.fragments;

import static jakimovich.nightlearn.helpers.AlertDialogHelper.showEditAlertDialog;
import static jakimovich.nightlearn.helpers.AlertDialogHelper.showOptionsAlertDialog;
import static jakimovich.nightlearn.helpers.MethodsHelper.signOut;
import static jakimovich.nightlearn.helpers.MethodsHelper.updateUserLastname;
import static jakimovich.nightlearn.helpers.MethodsHelper.updateUserName;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.view.ContentInfo;
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

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.MainActivity;
import jakimovich.nightlearn.classes.UserService;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.MethodsHelper;

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
        ibEditName.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Enter your Name", "Type here...", "Update", "Cancel", this::updateUserName));

        ibEditLastname = view.findViewById(R.id.ibEditLastname);
        ibEditLastname.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Enter your Lastname", "Type here...", "Update", "Cancel", this::updateUserLastname));

        imageViewProfile = view.findViewById(R.id.imageViewProfile);

        btnExit = view.findViewById(R.id.btnProfileExit);
        btnExit.setOnClickListener((v) -> showOptionsAlertDialog(getActivity(), "Are you sure you want to exit?", "Yeah \n Let's get out", "Nope \n Back to study", this::finishAffinity));

        btnDeleteAccount = view.findViewById(R.id.btnProfileDeleteAccount);

        btnSignOut = view.findViewById(R.id.btnProfileSignOut);
        btnSignOut.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Are you sure you want to sign out? \n Do you need that?", "Sign out!", "Nope, get back", this::signOut));


        setProfilePhoto(imageViewProfile);


    }

    private void setProfilePhoto(ImageView imageView) {
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
            getActivity().finishAffinity();
            //TODO: Didn't find a way for closing app windows of smartphone itself
    }

    private void signOut(){
        MethodsHelper.signOut(getContext(), getActivity());
    }

    private void updateUserName(String name) {
        MethodsHelper.updateUserName(getContext(), name);
        ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "profile");
    }

    private void updateUserLastname(String lastname) {
        MethodsHelper.updateUserLastname(getContext(), lastname);
        ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "profile");
    }
}

