package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jakimovich.nightlearn.R;

public class AuthorInfoActivity extends AppCompatActivity {

    Button btnBack;
    TextView tvEnglish, tvHebrew, tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_info);

        btnBack = findViewById(R.id.btnAuthorGetBack);
        tvEnglish = findViewById(R.id.tvEnglish);
        tvHebrew = findViewById(R.id.tvHebrew);
        tvInfo = findViewById(R.id.tvInfo);
        tvInfo.setMovementMethod(new ScrollingMovementMethod());

        //Default settings
        onChosenEnglish();

        tvEnglish.setOnClickListener(v -> onChosenEnglish());
        tvHebrew.setOnClickListener(v -> onChosenHebrew());

        btnBack.setOnClickListener(v -> finish());

    }
//        TODO: To split activities about app/author

    private void onChosenEnglish(){
        tvEnglish.setTextColor(R.color.darkGray);
        tvHebrew.setTextColor(Color.parseColor("#D9D9D9"));
        String info = "About an app: " +
                "\nThe primary purpose of NightLearn is to aid individuals in their learning journey. " +
                "Utilizing flashcard technology, NightLearn enables users to store various types of information within learning cards " +
                "and reinforce their memory through short, customizable quizzes. " +
                "Recognizing that one of the optimal times for learning is before sleep, " +
                "the application sends notifications encouraging users to take a quiz. " +
                "Furthermore, users have the option to set alarms within the app. " +
                "These alarms can only be deactivated by answering a few questions based on the user's learning cards, " +
                "leaving them with no choice but to engage in studying!" +
                "\n\n About an author: " +
                "\n Name: Maxim Yakimovich " +
                "\n ID number: 346814221 " +
                "\n Teachers' names: Avital Shain, Eli Sinyanski " +
                "\n School name: Makif Gimel Ha-Amit " +
                "\n Year: 2024";

        tvInfo.setGravity(Gravity.LEFT);
        tvInfo.setText(info);
    }

    private void onChosenHebrew(){
        tvEnglish.setTextColor(Color.parseColor("#D9D9D9"));
        tvHebrew.setTextColor(R.color.darkGray);
        String info = "על האפליקציה: " +
                "\n המטרה העיקרית של NightLearn היא לסייע לאנשים במסע הלמידה שלהם." +
                " באמצעות טכנולוגיית קלפים מיוחדת, NightLearn מאפשר למשתמשים לאחסן מגוון רחב של מידע בתוך קלפי למידה" +
                " ולחזק את הזיכרון שלהם דרך חידושי קצרים וניתנים להתאמה אישית." +
                " מודעת לעובדה שאחת הזמנים האופטימליים ללמידה הוא לפני השינה," +
                " האפליקציה שולחת התראות שמציעות למשתמשים לבצע חידוש." +
                " בנוסף, המשתמשים יכולים להגדיר שעונים מעוררים באפליקציה." +
                " השעונים האלה ניתן לבטל רק על ידי עניין במספר שאלות מבוססות על קלפי הלמידה של המשתמש," +
                " משאירים אותם עם אפשרות רק להתעסק בלימודים!" +
                "\n\n על היצרן: " +
                "\n שם: מקסים יקימוביץ " +
                "\n תעודת זהות: 346814221 " +
                "\n שמות המורים: אביטל שיין, אלי סיניאנסקי " +
                "\n תיכון: מקיף ג האמית " +
                "\n שנה: 2024";
        tvInfo.setGravity(Gravity.RIGHT);
        tvInfo.setText(info);
    }

}