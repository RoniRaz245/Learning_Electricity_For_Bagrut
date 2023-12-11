package com.example.learningelectricityforbagrut;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;
import java.util.UUID;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MakeQuestionActivity extends AppCompatActivity {
    private EditText bodyTextView, option1TextView, option2TextView, option3TextView, option4TextView;
    private ImageButton goHome;
    private NumberPicker levelPicker, correctAnswerPicker;
    private String imageUrl;
    private Button upload, uploadImage;
    protected DatabaseReference mDatabase;
    protected StorageReference mStorage;
    //create the photo picker to launch if user so requests
    protected ActivityResultLauncher<PickVisualMediaRequest>
            pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the photo picker
                if (uri != null) {
                    imageUrl=UUID.randomUUID().toString(); //generate random address for this image
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makequestion);

        goHome=findViewById(R.id.goHome);
        bodyTextView=findViewById(R.id.questionBody);
        option1TextView=findViewById(R.id.firstAnswer);
        option2TextView=findViewById(R.id.secondAnswer);
        option3TextView=findViewById(R.id.thirdAnswer);
        option4TextView=findViewById(R.id.fourthAnswer);
        levelPicker=findViewById(R.id.levelPicker);
        correctAnswerPicker=findViewById(R.id.correctAnswerPicker);
        upload=findViewById(R.id.uploadQuestion);
        uploadImage=findViewById(R.id.uploadImage);


        mDatabase= FirebaseDatabase.getInstance().getReference();

        uploadImage.setOnClickListener(v -> getImageFromUser());
        upload.setOnClickListener(v -> uploadGivenQuestion()); //calls function that takes info from fields and uploads question based on them
        goHome.setOnClickListener(v -> this.getApplicationContext().startActivity(new Intent(this.getApplicationContext(), HomeActivity.class)));
    }
    private void uploadGivenQuestion(){
        if(levelPicker.getValue()==0) {
            Toast.makeText(this.getApplicationContext(),"בבקשה תכניס רמה!", Toast.LENGTH_LONG);
        }//TODO: add other fail cases

        String body=bodyTextView.getText().toString();
        String option1= option1TextView.getText().toString();
        String option2= option2TextView.getText().toString();
        String option3= option3TextView.getText().toString();
        String option4= option4TextView.getText().toString();
        String[] options= {option1,option2,option3,option4};
        int level=levelPicker.getValue();
        int correctAnswer=correctAnswerPicker.getValue();
        int answerIndex=correctAnswer-1;
        String image=imageUrl;
        Question newQuestion= new Question(body, image, options, answerIndex, level);
        mDatabase.child("questions").push().setValue(newQuestion);
    }
    private void getImageFromUser(){
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }
}