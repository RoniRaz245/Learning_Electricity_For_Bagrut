package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class userStatsActivity extends baseActivity implements TestViewAdapter.ItemClickListener {
    TestViewAdapter adapter;
    TextView levelView, gradeAvg;
    ImageButton levelInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().show();

        levelView=findViewById(R.id.level);
        gradeAvg=findViewById(R.id.gradeAvg);
        levelInfo=findViewById(R.id.levelInfo);

        levelInfo.setOnClickListener(v->giveInfo());
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        database.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
        if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);
                int userLevel = user.getLevel();
                String level = getString(R.string.ur_level) + " " + String.valueOf(userLevel);
                levelView.setText(level);
                }
            }
        });

            //get tests
        ArrayList<Test> tests = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tests").whereEqualTo("uid",mAuth.getCurrentUser().getUid()).orderBy("timeTaken",Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete (@NonNull Task < QuerySnapshot > task) {
                    if (task.isSuccessful()) {
                        double sum=0;
                        int amount=0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Test currTest = document.toObject(Test.class);
                            tests.add(currTest);
                            sum+=currTest.calculateGrade();
                            amount++;
                        }
                        double avg=sum/amount;
                        gradeAvg.setText(getString(R.string.ur_avg)+" "+ avg);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                }
            });

            // set up the RecyclerView
            RecyclerView recyclerView = findViewById(R.id.viewTests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =new TestViewAdapter(this, tests);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        }

    @Override
    public void onItemClick(View view, int position) {
        Intent testStats=new Intent(getApplicationContext(), quizzStatsActivity.class);
        testStats.putExtra("test", adapter.getItem(position));
        userStatsActivity.this.startActivity(testStats);
    }
}