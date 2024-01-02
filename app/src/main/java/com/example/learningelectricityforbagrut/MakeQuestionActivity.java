package com.example.learningelectricityforbagrut;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.UUID;
import android.app.Dialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MakeQuestionActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setContentView(R.layout.level_info);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        TextView okay_text = dialog.findViewById(R.id.okay_text);
        okay_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void uploadGivenQuestion(){
        if(levelPicker.getValue()==0) {
            Toast.makeText(this.getApplicationContext(),"בבקשה תכניס רמה!", Toast.LENGTH_LONG).show();
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
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth= FirebaseAuth.getInstance();

        String UID= String.valueOf(database.child("users").child(mAuth.getCurrentUser().getUid()));
        String image=imageUrl;
        Question newQuestion= new Question(body, image, options, answerIndex, level, UID);
        mDatabase.child("questions").push().setValue(newQuestion);
    }
    private void getImageFromUser(){
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }
}