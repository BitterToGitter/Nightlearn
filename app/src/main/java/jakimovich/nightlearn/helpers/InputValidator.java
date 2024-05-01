package jakimovich.nightlearn.helpers;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern patternName = Pattern.compile("^[A-Z][a-z]*$");
    private static final Pattern patternNickname = Pattern.compile("^[^\\s]+$");


    public static boolean isValidName(Context context, String name) {
        if (patternName.matcher(name).matches()){
            return true;
        }
        else{
            Toast.makeText(context, "Invalid input. Name and Lastname should contain letters only, starting with a capital letter.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }//TODO: to make validators more specific

    public static boolean isValidNickname(Context context, String nickname){
        if(nickname.length() <= 10){
            Toast.makeText(context, "Invalid input. Nickname has to contain no more than 10 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!patternNickname.matcher(nickname).matches()){
            Toast.makeText(context, "Invalid input. Nickname may contain anything except spaces", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }//Todo: smth wrong with nickname

}
