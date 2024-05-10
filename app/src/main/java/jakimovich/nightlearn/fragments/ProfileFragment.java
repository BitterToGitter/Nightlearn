package jakimovich.nightlearn.fragments;

import static jakimovich.nightlearn.R.drawable.ic_password_hide;
import static jakimovich.nightlearn.R.drawable.ic_password_show;
import static jakimovich.nightlearn.helpers.AlertDialogHelper.showEditAlertDialog;
import static jakimovich.nightlearn.helpers.AlertDialogHelper.showOptionsAlertDialog;
import static jakimovich.nightlearn.helpers.AlertDialogHelper.showWarningAlertDialog;
import static jakimovich.nightlearn.helpers.MethodsHelper.signOut;
import static jakimovich.nightlearn.helpers.MethodsHelper.updateUserLastname;
import static jakimovich.nightlearn.helpers.MethodsHelper.updateUserName;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DrawableRes;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.InputStream;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.MainActivity;
import jakimovich.nightlearn.activities.NotificationSettingsActivity;
import jakimovich.nightlearn.activities.SignUpActivity;
import jakimovich.nightlearn.activities.SplashActivity;
import jakimovich.nightlearn.classes.UserService;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.MethodsHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileFragment extends Fragment {

    private static boolean passwordHidden = true;
    ImageView profilePicture;
    TextView profileName, profileLastname, profileNickname, profileGmail, profilePassword, profileNotificationSettings, profileUsersRating;
    Button btnSignOut, btnExit, btnDeleteAccount;
    ImageButton ibEditName, ibEditLastname, ibEditNickname, ibEditGmail, ibEditPassword, ibHidePassword;
    LinearLayout llUsersRating, llNotificationSettings;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!UserService.isGuest()){
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            MethodsHelper.setProfilePic(getContext(), selectedImageUri, profilePicture);
                            UserService.myUser.setProfilePic(selectedImageUri);
                            UserService.uploadProfilePic();
                        }
                    }
                }
        );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            profilePicture = view.findViewById(R.id.imageViewProfile);

            profileName = view.findViewById(R.id.tvProfileName);
            ibEditName = view.findViewById(R.id.ibEditName);

            profileLastname = view.findViewById(R.id.tvProfileLastname);
            ibEditLastname = view.findViewById(R.id.ibEditLastname);

            profileNickname = view.findViewById(R.id.tvProfileNickname);
            ibEditNickname = view.findViewById(R.id.ibEditNickname);

            profileGmail = view.findViewById(R.id.tvProfileGmail);
            ibEditGmail = view.findViewById(R.id.ibEditGmail);

            profilePassword = view.findViewById(R.id.tvProfilePassword);
            ibEditPassword = view.findViewById(R.id.ibEditPassword);

            ibHidePassword = view.findViewById(R.id.ibHidePassword);

            llNotificationSettings = view.findViewById(R.id.llProfileNotificationsSettings);
            llNotificationSettings.setOnClickListener(v -> startActivity(new Intent(getActivity(), NotificationSettingsActivity.class)));

            llUsersRating = view.findViewById(R.id.llProfileUsersRating);
            llUsersRating.setOnClickListener(v -> startActivity(new Intent(getActivity(), NotificationSettingsActivity.class)));

            btnExit = view.findViewById(R.id.btnProfileExit);
            btnExit.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Are you sure you want to exit?", "Yeah \n Let's get out", "Nope \n Back to study", this::finishAffinity));

            btnDeleteAccount = view.findViewById(R.id.btnProfileDeleteAccount);

            btnSignOut = view.findViewById(R.id.btnProfileSignOut);

        setDefaultProfilePhoto(profilePicture);

        if(!UserService.isGuest()){

            profileName.setText(UserService.myUser.getName());
            profileLastname.setText(UserService.myUser.getLastname());
            profileNickname.setText("Nickname: " + UserService.myUser.getNickname());
            profileGmail.setText("Gmail: " + UserService.myUser.getEMail());
            profilePassword.setText("Password: " + UserService.myUser.getPassword().substring(0,1) + "*******");

            ibEditName.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Name", "Type here...", "Update", "Cancel", this::updateUserName));
            ibEditLastname.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Lastname", "Type here...", "Update", "Cancel", this::updateUserLastname));
            ibEditNickname.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Nickname", "Type here...", "Update", "Cancel", this::updateUserNickname));
            ibEditGmail.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Gmail", "Type here...", "Update", "Cancel", this::updateUserGmail));
            ibEditPassword.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Password", "Type here...", "Update", "Cancel", this::updateUserPassword));

            ibHidePassword.setOnClickListener(V -> hidePassword());

            btnDeleteAccount.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Want to delete an account?\n" +
                            "What’s wrong with you?\n" +
                            "Think twice, man",
                    "Delete anyway",
                    "No, get back!",
                    this::deleteAccount));

            btnSignOut.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Are you sure you want to sign out? \n Do you need that?", "Sign out!", "Nope, get back", this::signOut));

            profilePicture.setOnClickListener(v -> setProfilePhoto());

        }else {


            profileName.setText("Sign up to type your name");
            profileLastname.setText("Sign up to type your lastname");
            profileNickname.setText("Nickname: " + "Sign up to type");
            profileGmail.setText("Gmail: " + "Sign up to type");
            profilePassword.setText("Password: " + "Sign up to type");

            ibEditName.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok"));
            ibEditLastname.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok"));
            ibEditNickname.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok"));
            ibEditGmail.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok"));
            ibEditPassword.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok"));

            passwordHidden = false;
            ibHidePassword.setVisibility(View.INVISIBLE);

            btnDeleteAccount.setVisibility(View.INVISIBLE);

            btnSignOut.setText("Sign Up");
            btnSignOut.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Congrats! You decided to sign up. \n Just you to know, In your new account you'll start making progress from scratch",  "Sign Up!", "Get back", this::deleteAccount));

        }
    }

    private void setDefaultProfilePhoto(ImageView imageView) {
        if(UserService.myUser.getProfilePic() == null) {
            //Just converts svg file to readable default profile icon
            try {
                imageView.setImageDrawable(MethodsHelper.convertSvgToDrawable(getContext(), R.raw.profile));
            } catch (SVGParseException e) {
                e.printStackTrace();
            }
        }
        else {
            selectedImageUri = UserService.myUser.getProfilePic();
            MethodsHelper.setProfilePic(getContext(), selectedImageUri, profilePicture);

        }

    }

    private void setProfilePhoto(){

        ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });



    }

    private void finishAffinity() {
            getActivity().finishAffinity();
            //TODO: Didn't find a way for closing app windows of smartphone itself
    }

    private void signOut(){
        MethodsHelper.signOut(getContext(), getActivity());
    }

    private void deleteAccount() {MethodsHelper.deleteAccount(getContext(), getActivity());}

    private void updateUserName(String name) {
        MethodsHelper.updateUserName(getContext(), name);
        ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "profile");
    }

    private void updateUserLastname(String lastname) {
        MethodsHelper.updateUserLastname(getContext(), lastname);
        ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "profile");
    }

    private void updateUserNickname(String nickname){
        MethodsHelper.updateUserNickname(getContext(), nickname);
        ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "profile");
    }

    private void updateUserGmail(String gmail){
        MethodsHelper.updateUserGmail(getContext(), gmail);
        ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "profile");
    }
    private void updateUserPassword(String password){
        MethodsHelper.updateUserPassword(getContext(), password);
        ((MainActivity) getActivity()).replaceFragment(new ProfileFragment(), "profile");
    }

    private void hidePassword(){
        if(passwordHidden){
            ibHidePassword.setImageResource(ic_password_show);
            profilePassword.setText("Password: " + UserService.myUser.getPassword());
            passwordHidden = false;
        }
        else {
            ibHidePassword.setImageResource(ic_password_hide);
            profilePassword.setText("Password: " + UserService.myUser.getPassword().substring(0,1) + "*******");
            passwordHidden = true;
        }
    }


}

