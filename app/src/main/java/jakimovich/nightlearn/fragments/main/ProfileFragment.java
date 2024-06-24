package jakimovich.nightlearn.fragments.main;

import static jakimovich.nightlearn.helpers.AlertDialogHelper.showEditAlertDialog;
import static jakimovich.nightlearn.helpers.AlertDialogHelper.showOptionsAlertDialog;
import static jakimovich.nightlearn.helpers.AlertDialogHelper.showWarningAlertDialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.activities.AboutProjectActivity;
import jakimovich.nightlearn.activities.MainActivity;
import jakimovich.nightlearn.activities.RulesInfoActivity;
import jakimovich.nightlearn.helpers.AlertDialogHelper;
import jakimovich.nightlearn.helpers.GeneralHelper;
import jakimovich.nightlearn.helpers.InputChecker;
import jakimovich.nightlearn.helpers.UserService;
import jakimovich.nightlearn.helpers.ImageFilesManager;
import jakimovich.nightlearn.interfaces.OnMethodCompleted;

public class ProfileFragment extends Fragment {
//Todo: to copy again


    boolean resultFromGallery = false;
    boolean passwordHidden = true;
    ImageView ivProfilePicture;
    TextView profileName, profileLastname, profileNickname, profileGmail, profilePassword;
    Button btnSignOut, btnExit, btnDeleteAccount;
    ImageButton ibEditName, ibEditLastname, ibEditNickname, ibEditGmail, ibEditPassword, ibHidePassword;
    LinearLayout llAuthorInfo, llToQuizRules;
    ImageButton ivAuthorInfo, ivToQuizRules;
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

                            if (resultFromGallery){
                                ImageFilesManager.organizeInternalStorage(selectedImageUri, getContext(), true, new OnMethodCompleted() {
                                    @Override
                                    public void onCompleted() {
                                        ImageFilesManager.uploadPictureToStorage(getContext(), ImageFilesManager.getProfilePicRef(), ImageFilesManager.getProfilePicFile(getContext()));
                                        ((MainActivity) getActivity()).updateCurrentFragment();
                                    }
                                });

                            } else {
                                ImageFilesManager.organizeInternalStorage(selectedImageUri, getContext(), false, new OnMethodCompleted() {
                                    @Override
                                    public void onCompleted() {
                                        ImageFilesManager.uploadPictureToStorage(getContext(), ImageFilesManager.getProfilePicRef(), ImageFilesManager.getProfilePicFile(getContext()));
                                        ((MainActivity) getActivity()).updateCurrentFragment();
                                    }
                                });
                            }
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
        //Todo: to copy again
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            ivProfilePicture = view.findViewById(R.id.imageViewProfile);

            profileName = view.findViewById(R.id.tvProfileName);
            ibEditName = view.findViewById(R.id.ibEditName);
            ibEditName.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(),R.raw.ic_edit));

            profileLastname = view.findViewById(R.id.tvProfileLastname);
            ibEditLastname = view.findViewById(R.id.ibEditLastname);
            ibEditLastname.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(),R.raw.ic_edit));

            profileNickname = view.findViewById(R.id.tvProfileNickname);
            ibEditNickname = view.findViewById(R.id.ibEditNickname);
            ibEditNickname.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(),R.raw.ic_edit));

            profileGmail = view.findViewById(R.id.tvProfileGmail);
            ibEditGmail = view.findViewById(R.id.ibEditGmail);
            ibEditGmail.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(),R.raw.ic_edit));

            profilePassword = view.findViewById(R.id.tvProfilePassword);
            ibEditPassword = view.findViewById(R.id.ibEditPassword);
            ibEditPassword.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(),R.raw.ic_edit));

            ibHidePassword = view.findViewById(R.id.ibHidePassword);
            ibHidePassword.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(), R.raw.ic_password_hidden));

            llToQuizRules = view.findViewById(R.id.llProfileToQuizRules);
            ivToQuizRules = view.findViewById(R.id.ivProfileToQuizRules);
            llToQuizRules.setOnClickListener(v -> startActivity(new Intent(getActivity(), RulesInfoActivity.class)));

            ivToQuizRules.setOnClickListener(v -> startActivity(new Intent(getActivity(), RulesInfoActivity.class)));

            llAuthorInfo = view.findViewById(R.id.llProfileAuthorInfo);
            ivAuthorInfo = view.findViewById(R.id.ivProfileAuthorInfo);
            llAuthorInfo.setOnClickListener(v -> startActivity(new Intent(getActivity(), AboutProjectActivity.class)));
            ivAuthorInfo.setOnClickListener(v -> startActivity(new Intent(getActivity(), AboutProjectActivity.class)));

            btnExit = view.findViewById(R.id.btnProfileExit);
            btnExit.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Are you sure you want to exit?", "Yeah \n Let's get out", "Nope \n Back to study", vi -> {getActivity().finishAndRemoveTask();}, v2 ->{}));

            btnDeleteAccount = view.findViewById(R.id.btnProfileDeleteAccount);

            btnSignOut = view.findViewById(R.id.btnProfileSignOut);


        setDefaultProfilePhoto(ivProfilePicture);

        if(!UserService.isGuest()){

            profileName.setText(UserService.myUser.getName());
            profileLastname.setText(UserService.myUser.getLastname());
            profileNickname.setText(UserService.myUser.getNickname());
            profileGmail.setText(UserService.myUser.getEMail());
            profilePassword.setText(UserService.myUser.getPassword().substring(0,1) + "*******");

            ibEditName.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Name", "Type here...", "Update", "Cancel", profileName.getText().toString(), this::updateUserName));
            ibEditLastname.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Lastname", "Type here...", "Update", "Cancel", profileLastname.getText().toString(), this::updateUserLastname));
            ibEditNickname.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Nickname", "Type here...", "Update", "Cancel", profileNickname.getText().toString(), this::updateUserNickname));
            ibEditGmail.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Gmail", "Type here...", "Update", "Cancel", profileGmail.getText().toString(), this::updateUserGmail));
            ibEditPassword.setOnClickListener(v -> showEditAlertDialog(getActivity(), "Update your Password", "Type here...", "Update", "Cancel", UserService.myUser.getPassword().toString(), this::updateUserPassword));

            ibHidePassword.setOnClickListener(V -> hidePassword());

            btnDeleteAccount.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Want to delete an account?\n" +
                            "What’s wrong with you?\n" +
                            "Think twice, man",
                    "Delete anyway",
                    "No, get back!",
                    v1 -> UserService.deleteAccount(getContext(), getActivity()), v2 ->{}));

            btnSignOut.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Are you sure you want to sign out? \n Do you need that?", "Sign out!", "Nope, get back", v1 -> UserService.signOut(getContext(), getActivity()), v2 ->{}));

            ivProfilePicture.setOnClickListener(v -> setProfilePhoto());

        }else {


            profileName.setText("Sign up to type a name");
            profileLastname.setText("Sign up to type a lastname");
            profileNickname.setText("Sign up to type");
            profileGmail.setText("Sign up to type");
            profilePassword.setText("Sign up to type");

            ibEditName.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok", vi -> {}));
            ibEditLastname.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok", vi -> {}));
            ibEditNickname.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok", vi -> {}));
            ibEditGmail.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok", vi -> {}));
            ibEditPassword.setOnClickListener(v -> showWarningAlertDialog(getActivity(), "This option is available for registered users only. \n Sign in to set up your own profile as you wish!", "Ok", vi -> {}));

            passwordHidden = false;
            ibHidePassword.setVisibility(View.INVISIBLE);

            btnDeleteAccount.setVisibility(View.INVISIBLE);

            btnSignOut.setText("Sign Up");
            btnSignOut.setOnClickListener(v -> showOptionsAlertDialog(getActivity(), "Congrats! You decided to sign up. \n Just you to know, In your new account you'll start making progress from scratch. This guest account will be deleted.",  "Sign Up!", "Get back", v1 -> UserService.deleteAccount(getContext(), getActivity()), v2 ->{}));

        }
    }

    private void setDefaultProfilePhoto(ImageView imageView) {

        if (UserService.isGuest()){
            imageView.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(), R.raw.profile));
            imageView.setLongClickable(false);
            return;
        }
        if(!ImageFilesManager.getProfilePicFile(getContext()).exists()) {
            imageView.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(), R.raw.profile));
            imageView.setLongClickable(false);
        }
        else {
            ImageFilesManager.setPicIntoImageView(getContext(), ImageFilesManager.getProfilePicFile(getContext()) ,ivProfilePicture);
            imageView.setLongClickable(true);
            imageView.setOnLongClickListener(v -> {showPopupDeleteWindow(v); return true;});
        }

    }

    private void setProfilePhoto(){

        AlertDialogHelper.showOptionsAlertDialog(
                getActivity(),
                "Choose an option",
                "Camera",
                "Gallery",
                v -> {
                    resultFromGallery = false;
                    ImagePicker.with(this)
                            .cameraOnly()
                            .cropSquare()
                            .compress(512)
                            .maxResultSize(512,512)
                            .createIntent(intent -> {
                                imagePickLauncher.launch(intent);
                                return null;
                            });
                },
                v -> {
                        resultFromGallery = true;
                        Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        imagePickLauncher.launch(intentGallery);
                }
        );

    }

    private void showPopupDeleteWindow(View view) {

        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_menu_learncard_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tvDelete = popupView.findViewById(R.id.tvLearncardPopupLayoutText);

        tvDelete.setOnClickListener(v -> {
            popupWindow.dismiss();
            AlertDialogHelper.showOptionsAlertDialog(getActivity(), "Are you sure you want to remove your profile picture? \n It's beautiful though!", "Yup, delete", "No, get back", v1 -> {
                ImageFilesManager.deletePictureFile(getContext(), ImageFilesManager.getProfilePicFile(getContext()) , ImageFilesManager.getProfilePicRef(), true, () -> {
                    Toast.makeText(getContext(), "Profile picture has been deleted successfully", Toast.LENGTH_SHORT).show();
                    setDefaultProfilePhoto(ivProfilePicture);
                }); }, v2 ->{});
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        popupWindow.showAsDropDown(view);
    }

    private void updateUserName(String name) {
        if (name.equals(UserService.myUser.getName())) {
            AlertDialogHelper.dismissAlertDialog();
            return;
        }
        if (InputChecker.nameCheck(getContext(), name)) {
        UserService.updateUserName(getContext(), name);
        ((MainActivity) getActivity()).updateCurrentFragment();
        AlertDialogHelper.dismissAlertDialog();
        }
    }

    private void updateUserLastname(String lastname) {
        if (lastname.equals(UserService.myUser.getLastname())) {
            AlertDialogHelper.dismissAlertDialog();
            return;
        }
        if (InputChecker.lastnameCheck(getContext(), lastname)) {
        UserService.updateUserLastname(getContext(), lastname);
        ((MainActivity) getActivity()).updateCurrentFragment();
        AlertDialogHelper.dismissAlertDialog();
        }
    }

    private void updateUserNickname(String nickname){
        if (nickname.equals(UserService.myUser.getNickname())) {
            AlertDialogHelper.dismissAlertDialog();
            return;
        }
        if (InputChecker.nicknameCheck(getContext(), nickname)) {
        UserService.updateUserNickname(getContext(), nickname);
        ((MainActivity) getActivity()).updateCurrentFragment();
        AlertDialogHelper.dismissAlertDialog();
        }
    }

    private void updateUserGmail(String gmail){
        if (gmail.equals(UserService.myUser.getEMail())) {
            AlertDialogHelper.dismissAlertDialog();
            return;
        }
        if (InputChecker.gmailCheck(getContext(), gmail)) {
        UserService.updateUserGmail(getContext(), gmail);
        ((MainActivity) getActivity()).updateCurrentFragment();
        AlertDialogHelper.dismissAlertDialog();
        }
    }

    private void updateUserPassword(String password){
        if (password.equals(UserService.myUser.getPassword())) {
            AlertDialogHelper.dismissAlertDialog();
            return;
        }
        if (InputChecker.passwordCheck(getContext(), password)) {
        UserService.updateUserPassword(getContext(), password);
        ((MainActivity) getActivity()).updateCurrentFragment();
        AlertDialogHelper.dismissAlertDialog();
        }
    }

    private void hidePassword()
    {
        if(passwordHidden){
            ibHidePassword.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(), R.raw.ic_password_shown));
            profilePassword.setText(UserService.myUser.getPassword());
            passwordHidden = false;
        }
        else {
            ibHidePassword.setImageDrawable(GeneralHelper.convertSvgToDrawable(getContext(), R.raw.ic_password_hidden));
            profilePassword.setText(UserService.myUser.getPassword().substring(0,1) + "*******");
            passwordHidden = true;
        }
    }

}

