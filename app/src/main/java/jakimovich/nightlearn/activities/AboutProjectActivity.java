package jakimovich.nightlearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import jakimovich.nightlearn.R;
import jakimovich.nightlearn.fragments.info.AuthorFragment;
import jakimovich.nightlearn.fragments.info.ProjectFragment;

public class AboutProjectActivity extends AppCompatActivity {

    public static String PROJECT_INFO;
    public static String AUTHOR_INFO;
    Button btnBack;
    TextView tvEnglish, tvHebrew;
    ViewPager viewPager;
    public static int PROJECT_AUTHOR_INFO_GRAVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_info);

        viewPager = findViewById(R.id.authorView_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        btnBack = findViewById(R.id.btnAuthorGetBack);
        tvEnglish = findViewById(R.id.tvEnglish);
        tvHebrew = findViewById(R.id.tvHebrew);

        //Default settings
        onChosenEnglish();

        tvEnglish.setOnClickListener(v -> onChosenEnglish());
        tvHebrew.setOnClickListener(v -> onChosenHebrew());

        btnBack.setOnClickListener(v -> finish());

    }

    private void onChosenEnglish() {
        tvEnglish.setTextColor(R.color.darkGray);
        tvHebrew.setTextColor(Color.parseColor("#D9D9D9"));

        PROJECT_INFO = "About an app: " +
                "\nThe primary purpose of NightLearn is to assist individuals in their learning journey. " +
                "Utilizing flashcard technology, NightLearn enables users to store various types of information within learning cards " +
                "and reinforce their memory through short, customizable quizzes. " +
                "Recognizing that one of the optimal times for learning is before sleep, " +
                "the application sends notifications encouraging users to take a quiz." +
                " 3-4 minutes taken by playing it before sleep-time may significantly increase learning progress and totally review the idea of learning ways.";

        AUTHOR_INFO = "About an author: " +
                "\n\n Name: Maxim Yakimovich" +
                "\n\n ID number: 346814221" +
                "\n\n Teachers' names: Avital Shein, Eli Sinyanski" +
                "\n\n School name: Makif Gimel Ha-Amit" +
                "\n\n Year: 2024";

        PROJECT_AUTHOR_INFO_GRAVITY = Gravity.LEFT;

        updateFragments();

    }


    private void onChosenHebrew() {
        tvEnglish.setTextColor(Color.parseColor("#D9D9D9"));
        tvHebrew.setTextColor(R.color.darkGray);

        PROJECT_INFO = "על האפליקציה: " +
                "\n המטרה העיקרית של NightLearn היא לסייע לאנשים במסע הלמידה שלהם." +
                " באמצעות טכנולוגיית קלפים מיוחדת, NightLearn מאפשר למשתמשים לאחסן מגוון רחב של מידע בתוך קלפי למידה" +
                " ולחזק את הזיכרון שלהם דרך חידושי קצרים וניתנים להתאמה אישית." +
                " מודעת לעובדה שאחת הזמנים האופטימליים ללמידה הוא לפני השינה," +
                " האפליקציה שולחת התראות שמציעות למשתמשים לבצע חידוש." +
                "3-4 דקות של משחק לפני השינה עשויות להגדיל באופן משמעותי את התקדמות הלמידה ולשנות לחלוטין את תפיסת שיטות הלמיד";

        AUTHOR_INFO = " על היצרן: " +
                "\n\n שם: מקסים יקימוביץ" +
                "\n\n תעודת זהות: 346814221" +
                "\n\n שמות המורים: אביטל שיין, אלי סיניאנסקי" +
                "\n\n יכון: מקיף ג האמית " +
                "\n\n שנה: 2024";

        PROJECT_AUTHOR_INFO_GRAVITY = Gravity.RIGHT;

        updateFragments();

    }

    private void updateFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment instanceof ProjectFragment) {
                ((ProjectFragment) fragment).updateTextView();
            } else if (fragment instanceof AuthorFragment) {
                ((AuthorFragment) fragment).updateTextView();
            }
        }
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ProjectFragment();
                case 1:
                    return new AuthorFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2; // Number of fragments
        }
    }
}

