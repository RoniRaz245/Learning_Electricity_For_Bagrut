package com.example.learningelectricityforbagrut;

import static androidx.core.content.FileProvider.getUriForFile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;


import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MakeQuestionActivity extends baseActivity {
    private EditText bodyTextView, option1TextView, option2TextView, option3TextView, option4TextView;
    private NumberPicker levelPicker, correctAnswerPicker;
    private String imageUrl;
    private Button upload, uploadImage;
    private ImageButton levelInfo;
    protected DatabaseReference mDatabase;
    protected StorageReference mStorage;
    private Uri uri;
    //create the photo picker to launch if user so requests
    protected ActivityResultLauncher<PickVisualMediaRequest>
            pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the photo picker
                if (uri != null) {
                    imageUrl=UUID.randomUUID().toString(); //generate random ID for this image
                    mStorage=FirebaseStorage.getInstance().getReference();
                    StorageReference path=mStorage.child("Images").child(imageUrl);
                    path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(uploadImage.getContext(), "התמונה הועלתה!",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(uploadImage.getContext(), "הייתה שגיאה בהעלאת התמונה, אנא נסה שוב",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    Toast.makeText(uploadImage.getContext(), "לא נבחרה תמונה",Toast.LENGTH_LONG).show();
                }
            });
    protected ActivityResultLauncher<Uri> startCamera= registerForActivityResult(
                                new ActivityResultContracts.TakePicture(),
                                new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result == true) {
                imageUrl=UUID.randomUUID().toString(); //generate random ID for this image
                mStorage=FirebaseStorage.getInstance().getReference();
                StorageReference path=mStorage.child("Images").child(imageUrl);
                path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(uploadImage.getContext(), "התמונה הועלתה!",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(uploadImage.getContext(), "הייתה שגיאה בהעלאת התמונה, אנא נסה שוב",Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                Toast.makeText(uploadImage.getContext(), "לא נבחרה תמונה",Toast.LENGTH_LONG).show();

            }
        }
    }
                        );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makequestion);

        bodyTextView=findViewById(R.id.questionBody);
        option1TextView=findViewById(R.id.firstAnswer);
        option2TextView=findViewById(R.id.secondAnswer);
        option3TextView=findViewById(R.id.thirdAnswer);
        option4TextView=findViewById(R.id.fourthAnswer);
        levelPicker=findViewById(R.id.levelPicker);
        levelInfo=findViewById(R.id.levelInfo);
        correctAnswerPicker=findViewById(R.id.correctAnswerPicker);
        upload=findViewById(R.id.uploadQuestion);
        uploadImage=findViewById(R.id.uploadImage);


        mDatabase= FirebaseDatabase.getInstance().getReference();
        levelInfo.setOnClickListener(v->giveInfo());

        uploadImage.setOnClickListener(v -> getImageFromUser());
        upload.setOnClickListener(v -> uploadGivenQuestion()); //calls function that takes info from fields and uploads question based on them
         }
    private void giveInfo(){
        DialogFragment info = new infoFragment();
        info.show(getSupportFragmentManager(), "info");
    }
    private void uploadGivenQuestion(){
        //get details of question
        String body=bodyTextView.getText().toString();
        String option1= option1TextView.getText().toString();
        String option2= option2TextView.getText().toString();
        String option3= option3TextView.getText().toString();
        String option4= option4TextView.getText().toString();
        String[] options= {option1,option2,option3,option4};
        int level=levelPicker.getValue();
        int correctAnswer=correctAnswerPicker.getValue();

        //fail cases in case part of given question is invalid
        if(level==0) {
            Toast.makeText(this.getApplicationContext(),"דרושה רמה לשאלה", Toast.LENGTH_LONG).show();
            return;
        }
        if(correctAnswer==0) {
            Toast.makeText(this.getApplicationContext(),"דרוש מספר התשובה הנכונה", Toast.LENGTH_LONG).show();
            return;
        }
        if(body.isEmpty()){
            Toast.makeText(this.getApplicationContext(),"דרוש גוף לשאלה", Toast.LENGTH_LONG).show();
            return;
        }
        if(option1.isEmpty()||option2.isEmpty()||option3.isEmpty()||option4.isEmpty()){
            Toast.makeText(this.getApplicationContext(),"דרושות ארבע אפשרויות לתשובות", Toast.LENGTH_LONG).show();
            return;
        }

        int answerIndex=correctAnswer-1;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        String levelChild=String.join("level_",String.valueOf(level)); //the firebase branch the question will be under

        database.child("questions").child(levelChild).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long serialNum;
                if (snapshot.hasChild(String.valueOf(level))) {
                    serialNum = snapshot.child(String.valueOf(level)).getChildrenCount();
                }
                else{
                    serialNum = 1;
                }
                String UID= mAuth.getCurrentUser().getUid();
                String image=imageUrl;
                Question newQuestion= new Question(body, image, options, answerIndex, level, UID, serialNum);
                mDatabase.child("questions").child(String.valueOf(level)).push().setValue(newQuestion);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"אנא נסו שוב", Toast.LENGTH_LONG).show();

            }
        });
    }
    private void getImageFromUser() {
        DialogFragment imageSource = new imageSourceFragment();
        imageSource.show(getSupportFragmentManager(), imageSourceFragment.TAG);

        FragmentResultListener listener=new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                CharSequence source = result.getCharSequence("source");
                if (source != null) {
                    if (source == "gallery") {
                        pickMedia.launch(new PickVisualMediaRequest.Builder()
                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                .build());
                    }
                    else if(source == "camera"){
                        //make a uri to store photo that will be taken in
                        File imagePath = new File(getApplicationContext().getFilesDir(), "camera_images");
                        File newFile = new File(imagePath, imageUrl=UUID.randomUUID().toString()+".jpg");
                        uri = getUriForFile(getApplicationContext(), "com.example.learningelectricityforbagrut.fileprovider", newFile);
                        //make sure image directory exists- create it if not
                        boolean pathCreated=true;
                        if(!imagePath.exists())
                            pathCreated=imagePath.mkdirs();
                        
                        if(pathCreated)
                            startCamera.launch(uri);
                        else
                            Toast.makeText(getApplicationContext(),"אנא נסו שוב מאוחר יותר", Toast.LENGTH_LONG).show();
                    }
                    }
                }
            };

        getSupportFragmentManager().setFragmentResultListener("image source", imageSource, listener);
    }
}