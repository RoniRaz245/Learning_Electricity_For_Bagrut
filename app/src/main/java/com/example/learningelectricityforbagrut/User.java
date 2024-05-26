package com.example.learningelectricityforbagrut;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class User {
    private int level;
    private boolean isTeacher;
    private String name;
    private String UID;
    private long license;
    private int phoneNum;

    public User(){
        this.level=3;
        this.isTeacher =false;
        this.name=null;
        this.UID=null;
        license=0;
        phoneNum=0;
    }
    public User(boolean _isTeacher, String _name, String _UID, long _license, int _phoneNum){
        this.level=3;
        this.isTeacher =_isTeacher;
        this.name=_name;
        this.UID=_UID;
        this.license=_license;
        this.phoneNum=_phoneNum;
    }


    public void setLevel(int level) {
        this.level = level;
    }
    public void setTeacher(boolean _isTeacher){
        this.isTeacher =_isTeacher;
    }
    public void setName(String name) { this.name = name; }
    public void setUID(String UID) { this.UID = UID; }
    public void setLicense(long license) { this.license = license; }
    public void setPhoneNum(int phoneNum) { this.phoneNum = phoneNum; }

    public int getLevel() {
        return level;
    }
    public boolean isTeacher(){
        return isTeacher;
    }
    public String getName() { return name; }
    public String getUID() { return UID; }
    public long getLicense() { return license; }
    public int getPhoneNum() { return phoneNum; }

    public void updateLevel(){
        //if user consistently gets low grades take him down levels, if consistently gets high grades take up levels
        //TODO: decide on grade ranges later
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("tests").whereEqualTo("UID", this.getUID()).orderBy("timeTaken", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for (QueryDocumentSnapshot document : task.getResult()) {

                    }
                }
            }
        });
    }
}
