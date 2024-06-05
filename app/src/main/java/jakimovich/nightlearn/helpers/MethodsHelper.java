package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import jakimovich.nightlearn.activities.LearnsetEditActivity;
import jakimovich.nightlearn.classes.Learncard;
import jakimovich.nightlearn.classes.Learnset;
import jakimovich.nightlearn.classes.Quiz;

public class MethodsHelper {

    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    public static PictureDrawable convertSvgToDrawable (Context context, int inputStreamInt){

        try {
            return new PictureDrawable(SVG.getFromInputStream(context.getResources().openRawResource(inputStreamInt)).renderToPicture());
        } catch (SVGParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static Intent putLearnsetIntoIntent(Activity activity, Learnset learnset, int positionInArray, Class<?> activityClass){

        List<Learncard> learncards = learnset.getLearncards();

        Intent intent = new Intent(activity, activityClass);

        intent.putExtra("positionInArray", positionInArray);

        intent.putExtra("learnsetName", learnset.getName());
        intent.putExtra("cardsInTotal", learncards.size());

        intent.putExtra("quizQuestionsAmount", learnset.getQuizSettings().getQuestionsAmount());
        intent.putExtra("quizAnswerTimeSec", learnset.getQuizSettings().getAnswerTimeSec());
        intent.putExtra("quizRightAnswersNumToBeLearned", learnset.getQuizSettings().getRightAnswersNumToBeLearned());
        intent.putExtra("quizQuestionType", learnset.getQuizSettings().getQuestionType());

        for(int i = 0; i < learncards.size(); i++ ){
            intent.putExtra("definition " + i, learncards.get(i).getDefinition());
            intent.putExtra("explanation " + i, learncards.get(i).getExplanation());
            intent.putExtra("timesSeen " + i, learncards.get(i).getTimesSeen());
            intent.putExtra("timesAnsweredRight " + i, learncards.get(i).getTimesAnsweredRight());
            intent.putExtra("learned " + i, learncards.get(i).getLearned());
        }

        return intent;

    }

    public static Learnset getLearnsetFromIntent(Intent intent){

        ArrayList<Learncard> learncards = new ArrayList<>();
        for (int i = 0; i < intent.getExtras().getInt("cardsInTotal"); i++){
            learncards.add(new Learncard(
                    intent.getExtras().getString("definition " + i),
                    intent.getExtras().getString("explanation " + i),
                    intent.getExtras().getInt("timesSeen " + i),
                    intent.getExtras().getInt("timesAnsweredRight " + i),
                    intent.getExtras().getBoolean("learned " + i)));
        }

        return new Learnset(
                intent.getExtras().getString("learnsetName"),
                new Quiz(intent.getExtras().getInt("quizQuestionsAmount"),
                        intent.getExtras().getInt("quizAnswerTimeSec"),
                        intent.getExtras().getInt("quizRightAnswersNumToBeLearned"),
                        intent.getExtras().getInt("quizQuestionType")),
                learncards
        );

    }

    public static void setPictureFromFirebaseStorage(Context context, ImageView imageView){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference profilePicRef = FirebaseStorage.getInstance().getReference("users/" + userId + "/profilePic");

        Glide.with(context).load(profilePicRef).apply(RequestOptions.circleCropTransform()).into(imageView);

    }

    public static Fragment getVisibleFragment(FragmentActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public static ArrayList<Learnset> createSampleLearnsets(){
        Learnset sampleLearnset1 = new Learnset("General history events", new Quiz());
        sampleLearnset1.addLearncard(new Learncard("World War II", "The deadliest conflict in human history"));
        sampleLearnset1.addLearncard(new Learncard("The French Revolution", "A period of radical social and political change in France"));
        sampleLearnset1.addLearncard(new Learncard("The American Civil War", "A civil war in the United States fought between the northern states and the southern states"));
        sampleLearnset1.addLearncard(new Learncard("The Cold War", "A period of geopolitical tension between the Soviet Union and the United States"));
        sampleLearnset1.addLearncard(new Learncard("The Industrial Revolution", "A period of major industrialization that took place during the late 18th and early 19th centuries"));
        sampleLearnset1.addLearncard(new Learncard("The Renaissance", "A period in European history marking the transition from the Middle Ages to modernity"));
        sampleLearnset1.addLearncard(new Learncard("The Great Depression", "A severe worldwide economic depression that took place mostly during the 1930s"));
        sampleLearnset1.addLearncard(new Learncard("The Russian Revolution", "A period of political and social revolution across the territory of the Russian Empire"));
        sampleLearnset1.addLearncard(new Learncard("The Vietnam War", "A conflict in Vietnam, Laos, and Cambodia from November 1, 1955, to the fall of Saigon on April 30, 1975"));
        sampleLearnset1.addLearncard(new Learncard("The American Revolution", "A colonial revolt that took place between 1765 and 1783"));

        Learnset sampleLearnset2 = new Learnset("Chemistry, basic terms", new Quiz());
        sampleLearnset2.addLearncard(new Learncard("Atom", "The basic unit of a chemical element"));
        sampleLearnset2.addLearncard(new Learncard("Molecule", "A group of atoms bonded together"));
        sampleLearnset2.addLearncard(new Learncard("Chemical bond", "A lasting attraction between atoms"));
        sampleLearnset2.addLearncard(new Learncard("Chemical reaction", "A process that leads to the transformation of one set of chemical substances to another"));
        sampleLearnset2.addLearncard(new Learncard("Periodic table", "A tabular arrangement of the chemical elements"));
        sampleLearnset2.addLearncard(new Learncard("Acid", "A molecule or ion capable of donating a hydron"));
        sampleLearnset2.addLearncard(new Learncard("Base", "A substance that can accept hydrogen ions"));
        sampleLearnset2.addLearncard(new Learncard("pH", "A scale used to specify how acidic or basic a water-based solution is"));
        sampleLearnset2.addLearncard(new Learncard("Chemical formula", "A way of expressing information about the proportions of atoms that constitute a particular chemical compound"));
        sampleLearnset2.addLearncard(new Learncard("Chemical equation", "The symbolic representation of a chemical reaction"));

        Learnset sampleLearnset3 = new Learnset("Interesting geography", new Quiz());
        sampleLearnset3.addLearncard(new Learncard("Russia", "The largest country in the world"));
        sampleLearnset3.addLearncard(new Learncard("China", "The most populous country in the world"));
        sampleLearnset3.addLearncard(new Learncard("Canada", "The second largest country in the world"));
        sampleLearnset3.addLearncard(new Learncard("Buthan", "The only country in the world that measures its success by the Gross National Happiness of its citizens"));
        sampleLearnset3.addLearncard(new Learncard("Vatican", "The smallest country in the world"));
        sampleLearnset3.addLearncard(new Learncard("Germany", "The country with the most neighboring countries in Europe"));
        sampleLearnset3.addLearncard(new Learncard("Japan", "The country with the most vending machines in the world"));
        sampleLearnset3.addLearncard(new Learncard("India", "The country with the most languages spoken in the world"));
        sampleLearnset3.addLearncard(new Learncard("Brazil", "The country with the most species of monkeys in the world"));
        sampleLearnset3.addLearncard(new Learncard("Australia", "The country with the most poisonous snakes in the world"));

        ArrayList<Learnset> learnsets = new ArrayList<>();
        learnsets.add(sampleLearnset1);
        learnsets.add(sampleLearnset2);
        learnsets.add(sampleLearnset3);

        return learnsets;
    }


}
