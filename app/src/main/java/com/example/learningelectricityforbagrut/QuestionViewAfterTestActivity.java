package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class QuestionViewAfterTestActivity extends baseActivity {
    TextView questionBody;
    RadioGroup answers;
    RadioButton firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;
    ImageView imageView;
    BottomNavigationView questionNav;
    Chronometer chronoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_view);
        questionBody=findViewById(R.id.questionBody);
        answers=findViewById(R.id.answers);
        firstAnswer=findViewById(R.id.firstAnswer);
        secondAnswer=findViewById(R.id.secondAnswer);
        thirdAnswer=findViewById(R.id.thirdAnswer);
        fourthAnswer=findViewById(R.id.fourthAnswer);
        imageView=findViewById(R.id.imageView);
        chronoView=findViewById(R.id.chronometer);
        questionNav=findViewById(R.id.bottom_navigation);

        //make pause timer button invisible and unclickable since it's irrelevant here
        questionNav.getMenu().findItem(R.id.pause).setIcon(0);
        questionNav.getMenu().findItem(R.id.pause).setTitle(null);
        questionNav.getMenu().findItem(R.id.pause).setCheckable(false);

        Intent testIntent = getIntent();
        Test currTest=testIntent.getSerializableExtra("test", Test.class);
        assert currTest != null;
        int questionAmount=currTest.getQuestions().size();
        final int[] currQuestion = {0};

        setUpQuestion(currQuestion, currTest);

        questionNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.next) {
                    if(currQuestion[0] +1<questionAmount) {//if this isn't the last question
                        currQuestion[0] += 1;
                        setUpQuestion(currQuestion, currTest);
                    }
                    else{
                        Toast.makeText(QuestionViewAfterTestActivity.this, "מחזיר למסך נתוני הבוחן...", Toast.LENGTH_SHORT).show();
                        Intent quizzStats=new Intent(getApplicationContext(), quizzStatsActivity.class);
                        getApplicationContext().startActivity(quizzStats);
                    }

                }
                else if(id==R.id.back) {
                    if (currQuestion[0] > 0) {//if this isn't the first question
                        currQuestion[0] -= 1;
                        setUpQuestion(currQuestion, currTest);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "זוהי השאלה הראשונה!", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

    }

    private void setUpQuestion(int[] questionNum, Test test){
        Question question=test.getQuestions().get(questionNum[0]);
        questionBody.setText(question.getQuestionBody());
        firstAnswer.setText(question.getAnswers().get(0));
        secondAnswer.setText(question.getAnswers().get(1));
        thirdAnswer.setText(question.getAnswers().get(2));
        fourthAnswer.setText(question.getAnswers().get(3));


        int correctAnswer=test.getQuestions().get(questionNum[0]).getCorrectAnswer()+1;
        boolean correctAnswerGiven=test.getCorrectAnswerGiven().get(questionNum[0]);
        switch (test.getAnswerGiven().get(questionNum[0])){
            case(-1): //means this question hadn't gotten an answer
                break;
            case(0):
                firstAnswer.setChecked(true);
                if(!correctAnswerGiven)
                    firstAnswer.setBackgroundColor(Color.RED);
                break;
            case(1):
                secondAnswer.setChecked(true);
                if(!correctAnswerGiven)
                    secondAnswer.setBackgroundColor(Color.RED);
                break;
            case(2):
                thirdAnswer.setChecked(true);
                if(!correctAnswerGiven)
                    thirdAnswer.setBackgroundColor(Color.RED);
                break;
            case(3):
                fourthAnswer.setChecked(true);
                if(!correctAnswerGiven)
                    fourthAnswer.setBackgroundColor(Color.RED);
                break;
        }
        switch(correctAnswer){
            case(0):
                firstAnswer.setBackgroundColor(Color.GREEN);
                break;
            case(1):
                secondAnswer.setBackgroundColor(Color.GREEN);
                break;
            case(2):
                thirdAnswer.setBackgroundColor(Color.GREEN);
                break;
            case(3):
                fourthAnswer.setBackgroundColor(Color.GREEN);
        }
        chronoView.setBase(SystemClock.elapsedRealtime() - test.getTimers().get(questionNum[0])* 1000L);

        String imageURL=question.getImageUrl();
        if(!Objects.equals(imageURL, "0")){
            StorageReference path= FirebaseStorage.getInstance().getReference().child("Images").child(imageURL);
            path.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "הייתה שגיאה בהעלאת התמונה, אנא עבור שאלה וחזור מאוחר יותר", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            imageView.setVisibility(View.INVISIBLE);
        }
    }


}