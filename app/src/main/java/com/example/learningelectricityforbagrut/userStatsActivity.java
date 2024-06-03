package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class userStatsActivity extends baseActivity implements TestViewAdapter.ItemClickListener {
    TestViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().show();

        //get tests
        ArrayList<Test> tests=new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db.collection("tests")
                .whereEqualTo("UID", mAuth.getCurrentUser().getUid()).orderBy("timeTaken", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Test currTest=document.toObject(Test.class);
                                tests.add(currTest);
                            }
                        } else {
                            //generic error message
                            Toast.makeText(getApplicationContext(), "הייתה שגיאה, נסה שוב מאוחר יותר", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.viewTests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TestViewAdapter(this, tests);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent testStats=new Intent(getApplicationContext(), quizzStatsActivity.class);
        testStats.putExtra("test", adapter.getItem(position));
        userStatsActivity.this.startActivity(testStats);
    }
}