package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import jakimovich.nightlearn.classes.QuizSettings;
import jakimovich.nightlearn.interfaces.EnterKeyListener;

public class GeneralHelper {

    public static PictureDrawable convertSvgToDrawable (Context context, int inputStreamInt){

        try {
            return new PictureDrawable(SVG.getFromInputStream(context.getResources().openRawResource(inputStreamInt)).renderToPicture());
        } catch (SVGParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLayoutParams(Activity activity, TextView textView){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;

        int maxWidth = displayWidth * 9/20;

        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = maxWidth;
        textView.setWidth(maxWidth);

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

        intent.putExtra("gamesPlayed", learnset.getGamesPlayed());

        for(int i = 0; i < learncards.size(); i++ ){
            intent.putExtra("definition " + i, learncards.get(i).getDefinition());
            intent.putExtra("explanation " + i, learncards.get(i).getExplanation());
            intent.putExtra("timesSeen " + i, learncards.get(i).getTimesSeen());
            intent.putExtra("timesAnsweredRight " + i, learncards.get(i).getTimesAnsweredRight());
            intent.putExtra("learned " + i, learncards.get(i).getLearned());
        }

        return intent;

    }

    public static void onKeyEnter (EditText editText, EnterKeyListener listener){
        editText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                listener.onKeyEnter(v);

                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                return true;
            }
            return false;
        });
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
                new QuizSettings(intent.getExtras().getInt("quizQuestionsAmount"),
                        intent.getExtras().getInt("quizAnswerTimeSec"),
                        intent.getExtras().getInt("quizRightAnswersNumToBeLearned"),
                        intent.getExtras().getInt("quizQuestionType")),
                intent.getExtras().getInt("gamesPlayed"),
                learncards
        );

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

    public static void createNewLearnset(Activity activity){
        Learnset learnset = new Learnset("New learnset", new QuizSettings());
        learnset.addLearncard(new Learncard("Sample definition", "Sample explanation"));
        activity.startActivityForResult(GeneralHelper.putLearnsetIntoIntent(activity, learnset, UserService.myUser.getLearnsets().size(), LearnsetEditActivity.class), 0);
    }

    public static ArrayList<Learnset> createSampleLearnsets(){

        //        Learnset sampleLearnset1 = new Learnset("General history events (sample)", new QuizSettings());
        //        {
        //            sampleLearnset1.addLearncard(new Learncard("World War II", "The deadliest conflict in human history"));
        //            sampleLearnset1.addLearncard(new Learncard("The French Revolution", "A period of radical social and political change in France"));
        //            sampleLearnset1.addLearncard(new Learncard("The American Civil War", "A civil war in the United States fought between the northern states and the southern states"));
        //            sampleLearnset1.addLearncard(new Learncard("The Cold War", "A period of geopolitical tension between the Soviet Union and the United States"));
        //            sampleLearnset1.addLearncard(new Learncard("The Industrial Revolution", "A period of major industrialization that took place during the late 18th and early 19th centuries"));
        //            sampleLearnset1.addLearncard(new Learncard("The Renaissance", "A period in European history marking the transition from the Middle Ages to modernity"));
        //            sampleLearnset1.addLearncard(new Learncard("The Great Depression", "A severe worldwide economic depression that took place mostly during the 1930s"));
        //            sampleLearnset1.addLearncard(new Learncard("The Russian Revolution", "A period of political and social revolution across the territory of the Russian Empire"));
        //            sampleLearnset1.addLearncard(new Learncard("The Vietnam War", "A conflict in Vietnam, Laos, and Cambodia from November 1, 1955, to the fall of Saigon on April 30, 1975"));
        //            sampleLearnset1.addLearncard(new Learncard("The American Revolution", "A colonial revolt that took place between 1765 and 1783"));
        //        }

        Learnset sampleLearnset2 = new Learnset("Physics basic laws (sample)", new QuizSettings());
        {
            sampleLearnset2.addLearncard(new Learncard("Newton's first law", "An object at rest stays at rest and an object in motion stays in motion with the same speed and in the same direction unless acted upon by an unbalanced force"));
            sampleLearnset2.addLearncard(new Learncard("Newton's second law", "The acceleration of an object as produced by a net force is directly proportional to the magnitude of the net force, in the same direction as the net force, and inversely proportional to the mass of the object"));
            sampleLearnset2.addLearncard(new Learncard("Newton's third law", "For every action, there is an equal and opposite reaction"));
            sampleLearnset2.addLearncard(new Learncard("Law of universal gravitation", "Every point mass attracts every single other point mass by a force pointing along the line intersecting both points"));
            sampleLearnset2.addLearncard(new Learncard("First law of thermodynamics", "Energy can neither be created nor destroyed"));
            sampleLearnset2.addLearncard(new Learncard("Second law of thermodynamics", "The total entropy of an isolated system can never decrease over time"));
            sampleLearnset2.addLearncard(new Learncard("Law of conservation of momentum", "The total linear momentum of a closed system remains constant through time"));
            sampleLearnset2.addLearncard(new Learncard("Ohm's law", "The current through a conductor between two points is directly proportional to the voltage across the two points"));
            sampleLearnset2.addLearncard(new Learncard("Hooke's law", "The force needed to extend or compress a spring by some distance is proportional to that distance"));
            sampleLearnset2.addLearncard(new Learncard("Snell's law", "The ratio of the sines of the angles of incidence and refraction is equivalent to the ratio of phase velocities in the two media"));
        }
        Learnset sampleLearnset3 = new Learnset("Hebrew-English phrases (sample)", new QuizSettings(5, 30, 4, 33));
        {
            sampleLearnset3.addLearncard(new Learncard("שלום", "Hello"));
            sampleLearnset3.addLearncard(new Learncard("תודה", "Thank you"));
            sampleLearnset3.addLearncard(new Learncard("בבקשה", "Please"));
            sampleLearnset3.addLearncard(new Learncard("סליחה", "Exuse me"));
            sampleLearnset3.addLearncard(new Learncard("מה שלומך?", "How are you?"));
            sampleLearnset3.addLearncard(new Learncard("איך קוראים לך?", "What's your name?"));
            sampleLearnset3.addLearncard(new Learncard("מאיפה אתה?", "Where are you from?"));
            sampleLearnset3.addLearncard(new Learncard("מה המספר שלך?", "What's your phone number?"));
            sampleLearnset3.addLearncard(new Learncard("כמה זה עולה?", "How much does it cost?"));
            sampleLearnset3.addLearncard(new Learncard("לא, אני לא מדבר עברית", "No, I don't speak Hebrew"));
            sampleLearnset3.addLearncard(new Learncard("כן, אני רוצה ללכת לים", "Yes, I want to go to the beach"));
            sampleLearnset3.addLearncard(new Learncard("אזעקה, כולם במקלט!", "Alert, everybody in the shelter!"));
            sampleLearnset3.addLearncard(new Learncard("אני לא רוצה להתחתן איתך!", "I don't want to marry you!"));

        }
        Learnset sampleLearnset4 = new Learnset("Europe Countries and Capitals (sample)", new QuizSettings(5, 30, 4, 33));
        {//West europe
            sampleLearnset4.addLearncard(new Learncard("Portugal", "Lisbon"));
            sampleLearnset4.addLearncard(new Learncard("Spain", "Madrid"));
            sampleLearnset4.addLearncard(new Learncard("France", "Paris"));
            sampleLearnset4.addLearncard(new Learncard("Belgium", "Brussels"));
            sampleLearnset4.addLearncard(new Learncard("Netherlands", "Amsterdam"));
            sampleLearnset4.addLearncard(new Learncard("Luxembourg", "Luxembourg"));
            sampleLearnset4.addLearncard(new Learncard("Germany", "Berlin"));
            sampleLearnset4.addLearncard(new Learncard("Switzerland", "Bern"));
            sampleLearnset4.addLearncard(new Learncard("Austria", "Vienna"));
            sampleLearnset4.addLearncard(new Learncard("Liechtenstein", "Vaduz"));
            sampleLearnset4.addLearncard(new Learncard("Italy", "Rome"));
            sampleLearnset4.addLearncard(new Learncard("United Kingdom", "London"));
            sampleLearnset4.addLearncard(new Learncard("Ireland", "Dublin"));

            //City states
            sampleLearnset4.addLearncard(new Learncard("San Marino", "San Marino"));
            sampleLearnset4.addLearncard(new Learncard("Andorra", "Andorra la Vella"));
            sampleLearnset4.addLearncard(new Learncard("Monaco", "Monaco"));
            sampleLearnset4.addLearncard(new Learncard("Vatican City", "Vatican City"));

            //Nordic countries
            sampleLearnset4.addLearncard(new Learncard("Norway", "Oslo"));
            sampleLearnset4.addLearncard(new Learncard("Sweden", "Stockholm"));
            sampleLearnset4.addLearncard(new Learncard("Finland", "Helsinki"));
            sampleLearnset4.addLearncard(new Learncard("Denmark", "Copenhagen"));
            sampleLearnset4.addLearncard(new Learncard("Iceland", "Reykjavik"));

            //Baltic countries
            sampleLearnset4.addLearncard(new Learncard("Estonia", "Tallinn"));
            sampleLearnset4.addLearncard(new Learncard("Latvia", "Riga"));
            sampleLearnset4.addLearncard(new Learncard("Lithuania", "Vilnius"));

            //East europe
            sampleLearnset4.addLearncard(new Learncard("Poland", "Warsaw"));
            sampleLearnset4.addLearncard(new Learncard("Czech Republic", "Prague"));
            sampleLearnset4.addLearncard(new Learncard("Slovakia", "Bratislava"));
            sampleLearnset4.addLearncard(new Learncard("Hungary", "Budapest"));
            sampleLearnset4.addLearncard(new Learncard("Ukraine", "Kiev"));
            sampleLearnset4.addLearncard(new Learncard("Belarus", "Minsk"));
            sampleLearnset4.addLearncard(new Learncard("Russia", "Moscow"));
            sampleLearnset4.addLearncard(new Learncard("Moldova", "Chisinau"));

            //Balkans
            sampleLearnset4.addLearncard(new Learncard("Croatia", "Zagreb"));
            sampleLearnset4.addLearncard(new Learncard("Bosnia and Herzegovina", "Sarajevo"));
            sampleLearnset4.addLearncard(new Learncard("Montenegro", "Podgorica"));
            sampleLearnset4.addLearncard(new Learncard("Serbia", "Belgrade"));
            sampleLearnset4.addLearncard(new Learncard("Kosovo", "Pristina"));
            sampleLearnset4.addLearncard(new Learncard("Albania", "Tirana"));
            sampleLearnset4.addLearncard(new Learncard("Macedonia", "Skopje"));
            sampleLearnset4.addLearncard(new Learncard("Slovenia", "Ljubljana"));
            sampleLearnset4.addLearncard(new Learncard("Bulgaria", "Sofia"));
            sampleLearnset4.addLearncard(new Learncard("Romania", "Bucharest"));

            //Mediterranean
            sampleLearnset4.addLearncard(new Learncard("Malta", "Valletta"));
            sampleLearnset4.addLearncard(new Learncard("Greece", "Athens"));
            sampleLearnset4.addLearncard(new Learncard("Cyprus", "Nicosia"));
            sampleLearnset4.addLearncard(new Learncard("Turkey", "Ankara"));
            sampleLearnset4.addLearncard(new Learncard("Israel", "Jerusalem"));
        }

        //        Learnset sampleLearnset5 = new Learnset("Europe Autonomous regions and Capitals", new QuizSettings());
//        {
//            sampleLearnset5.addLearncard(new Learncard("Gibraltar", "Gibraltar"));
//            sampleLearnset5.addLearncard(new Learncard("Faroe Islands", "Torshavn"));
//            sampleLearnset5.addLearncard(new Learncard("Greenland", "Nuuk"));
//            sampleLearnset5.addLearncard(new Learncard("Aland Islands", "Mariehamn"));
//            sampleLearnset5.addLearncard(new Learncard("Azores", "Ponta Delgada"));
//            sampleLearnset5.addLearncard(new Learncard("Madeira", "Funchal"));
//            sampleLearnset5.addLearncard(new Learncard("Canary Islands", "Las Palmas"));
//            sampleLearnset5.addLearncard(new Learncard("Ceuta", "Ceuta"));
//            sampleLearnset5.addLearncard(new Learncard("Melilla", "Melilla"));
//            sampleLearnset5.addLearncard(new Learncard("Isle of Man", "Douglas"));
//            sampleLearnset5.addLearncard(new Learncard("Jersey", "Saint Helier"));
//            sampleLearnset5.addLearncard(new Learncard("Guernsey", "Saint Peter Port"));
//        }

        ArrayList<Learnset> learnsets = new ArrayList<>();
        //learnsets.add(sampleLearnset1);
        learnsets.add(sampleLearnset2);
        learnsets.add(sampleLearnset3);
        learnsets.add(sampleLearnset4);

        return learnsets;
    }


}
