package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

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

}
