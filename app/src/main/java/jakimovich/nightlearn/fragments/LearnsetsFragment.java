package jakimovich.nightlearn.fragments;

import static jakimovich.nightlearn.classes.UserService.myUser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.Quiz;
import jakimovich.nightlearn.classes.UserService;
import jakimovich.nightlearn.helpers.LearnsetAdapter;

public class LearnsetsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Learnset> learnsetsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_learnsets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.learnsetsRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        learnsetsList = new ArrayList<>();

        learnsetsList.add(new Learnset("French", new Quiz()));
        learnsetsList.add(new Learnset("Quantum Physics", new Quiz()));
        //TODO: To set a reaction on touch

        recyclerView.setAdapter(new LearnsetAdapter(getContext(), learnsetsList));

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==0)
//        {
//            if(resultCode==RESULT_OK)
//            {
//                String title = data.getExtras().getString("title");
//                String subtitle = data.getExtras().getString("sub");
//                String price = data.getExtras().getString("price");
//                Bitmap bitmap=Helper.byteArrayToBitmap(data.getExtras().getByteArray("bitmap"));
//
//                lastSelected.setPrice(Integer.valueOf(price));
//                lastSelected.setTitle(title);
//                lastSelected.setSubTitle(subtitle);
//                lastSelected.setBitmap(bitmap);
//
//                toyAdapter.notifyDataSetChanged();
//
//                Toast.makeText(this,"data saved",Toast.LENGTH_LONG).show();
//            }
//            else if(resultCode==RESULT_CANCELED)
//            {
//                Toast.makeText(this,"action have been canceled", Toast.LENGTH_LONG).show();
//            }
//        }
//
//        if(requestCode==1) //come from add mode
//        {
//            if(resultCode==RESULT_OK)
//            {
//                String title = data.getExtras().getString("title");
//                String subtitle = data.getExtras().getString("sub");
//                String price = data.getExtras().getString("price");
//                Bitmap bitmap = Helper.byteArrayToBitmap(data.getExtras().getByteArray("bitmap"));
//
//                Toy toy=new Toy(Integer.valueOf(price),title,subtitle,bitmap);
//
//                toyAdapter.add(toy);
//                toyAdapter.notifyDataSetChanged();
//
//                Toast.makeText(this,"data saved",Toast.LENGTH_LONG).show();
//            }
//            else if(resultCode==RESULT_CANCELED)
//            {
//                Toast.makeText(this,"action have been canceled",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}