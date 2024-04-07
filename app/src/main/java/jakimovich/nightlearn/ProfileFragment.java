package jakimovich.nightlearn;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.InputStream;

public class ProfileFragment extends Fragment {

    ImageView imageViewProfile;
    TextView profileName, profileLastname;
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
        profileLastname = view.findViewById(R.id.tvProfileLastname);

        ibEditName = view.findViewById(R.id.ibEditName);
        ibEditLastname = view.findViewById(R.id.ibEditLastname);

        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        setProfilePhoto(imageViewProfile);

        profileName.setText(UserService.myUser.getName());
        profileLastname.setText(UserService.myUser.getLastname());

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

    private void editProfileName(){

    }

}

