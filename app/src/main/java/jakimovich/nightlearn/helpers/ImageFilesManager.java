package jakimovich.nightlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jakimovich.nightlearn.interfaces.OnMethodCompleted;


public class ImageFilesManager {


    public  static StorageReference getProfilePicRef() {return FirebaseStorage.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profilePic.jpg");}
    public static File getProfilePicFile(Context context) {return new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM) +"/userProfilePictures/"+ FirebaseAuth.getInstance().getCurrentUser().getUid(), "profilePic.jpg");}
    public static void uploadPictureToStorage(Context context, StorageReference pictureRef, File pictureFile) {

        AlertDialogHelper.showLoadingAlertDialog((Activity) context, "Just a moment...", "Your new amazing profile picture is uploading to the server!");

         pictureRef.putFile(Uri.fromFile(pictureFile))
             .addOnSuccessListener(taskSnapshot -> {
                 AlertDialogHelper.dismissAlertDialog();
                 Toast.makeText(context, "Profile picture has been uploaded successfully!", Toast.LENGTH_SHORT).show();})
             .addOnFailureListener(exception -> {
                 AlertDialogHelper.dismissAlertDialog();
                 Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
             });
    }

    public static void downloadPictureFromStorage(Context context, StorageReference pictureRef, File pictureFile, OnMethodCompleted callback) {

        pictureRef.getDownloadUrl().addOnSuccessListener(uri ->{

            pictureRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                {
                    //File pictureFile = new File(
                    // Create parent directories if they don't exist
                    if (!pictureFile.getParentFile().exists()) {
                        pictureFile.getParentFile().mkdirs();
                    }

                    try (FileOutputStream fos = new FileOutputStream(pictureFile)) {
                        fos.write(bytes);
                    } catch (IOException e) {
                        Log.e("SaveImage", "Saving image failed", e);
                    }

                    callback.onCompleted();

                }
            }).addOnFailureListener(e ->
                    {   AlertDialogHelper.dismissAlertDialog();
                        Toast.makeText(context, "Oops... Some issue with profile picture downloading appeared", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseStorage", "Download failed", e);
                        callback.onCompleted();
                    }
            );

        }).addOnFailureListener(e -> {callback.onCompleted();});

    }

    public static void uploadUriPicToDatabase(Uri fileUri) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.child("profilePic").setValue(fileUri.getPath());
    }

    public static void saveBitmapToFile(File file, Bitmap bitmap){
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (IOException e) {
            Log.e("SaveImage", "Saving image failed", e);
        }
    }

    public Bitmap cropBitmapToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newDimension = Math.min(width, height);

        int x = (width - newDimension) / 2;
        int y = (height - newDimension) / 2;

        return Bitmap.createBitmap(bitmap, x, y, newDimension, newDimension);
    }


    public static void organizeInternalStorage(Uri newProfilePicUri, Context context, Boolean fileFromGallery, OnMethodCompleted callback) {

        if (getProfilePicFile(context).exists()) {
            getProfilePicFile(context).delete();
            new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM) +"/ratingProfiles/", UserService.myUser.getNickname() + ".jpg").delete();
        } else {
            if (!getProfilePicFile(context).getParentFile().exists()) {
                getProfilePicFile(context).getParentFile().mkdirs();
            }
        }

        if (fileFromGallery){
            try {
                Bitmap bitmap = Bitmap.createScaledBitmap(new ImageFilesManager().cropBitmapToSquare(MediaStore.Images.Media.getBitmap(context.getContentResolver(), newProfilePicUri)), 512, 512, false);
                saveBitmapToFile(getProfilePicFile(context), bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            File newProfilePicFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), newProfilePicUri.getLastPathSegment().toString());
            newProfilePicFile.renameTo(getProfilePicFile(context));
        }

        if (callback != null) {
            callback.onCompleted();
        }
    }

    public static void deletePictureFile(Context context, File pictureFile, StorageReference pictureRef, Boolean deleteParentFile, OnMethodCompleted callback){

        if (!pictureFile.exists()){
            Toast.makeText(context, "Picture file set to delete doesn't exist", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pictureRef != null){
        pictureRef.delete();
        }

        if (deleteParentFile) {
            if (pictureFile.delete()){
                if (pictureFile.getParentFile().delete()){
                    callback.onCompleted();
                } else {
                    Toast.makeText(context, "Parent file wasn't deleted as it supposed to be", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "File wasn't deleted as it supposed to be", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (pictureFile.delete()){
                callback.onCompleted();
            } else {
                Toast.makeText(context, "File wasn't deleted as it supposed to be", Toast.LENGTH_SHORT).show();
            }
        }

    }
    
    public static void setPicIntoImageView(Context context, File file, ImageView imageView){
        Glide.with(context).load(Uri.fromFile(file)).signature(new ObjectKey(System.currentTimeMillis())).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

}
