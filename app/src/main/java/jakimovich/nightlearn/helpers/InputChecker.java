package jakimovich.nightlearn.helpers;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

public class InputChecker {

    public static boolean nameCheck(Context context, String name){
        Pattern pattern = Pattern.compile("^[A-Z][a-z]*$");
        if(pattern.matcher(name).matches()){
        return true;
        }
        else {
            Toast.makeText(context, "Invalid input: Name must contain latin letters only, starting with a capital letter", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean lastnameCheck(Context context, String lastname){
        Pattern pattern = Pattern.compile("^[A-Z][a-z]*$");
        if(pattern.matcher(lastname).matches()){
        return true;
        }
        else {
            Toast.makeText(context, "Invalid input: Lastname may contain latin letters only, starting with a capital letter", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean nicknameCheck(Context context, String nickname){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]*$");
        if(pattern.matcher(nickname).matches()){
        return true;
        }
        else{
            Toast.makeText(context, "Invalid input: Nickname may contain latin letters, numbers and underscores only", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean gmailCheck(Context context, String gmail){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if(pattern.matcher(gmail).matches()){
            return true;
        }
        else{
            Toast.makeText(context, "Invalid input: Invalid Email address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean passwordCheck(Context context, String password){
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_+=])[A-Za-z\\d!@#$%^&*()-_+=]{8,}$");
        if(pattern.matcher(password).matches()){
            return true;
        }
        else{
            Toast.makeText(context, "Invalid input: Password must contain minimum 8 characters: latin letters in both cases, numbers and symbols", Toast.LENGTH_SHORT).show();
            return false;
        } //Todo smth wrong here
    }

}
