package com.example.learningelectricityforbagrut;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class User {
    private int level;
    private boolean isTeacher;
    private String UID;
    private long license;
    private int phoneNum;

    public User(){
        this.level=3;
        this.isTeacher =false;
        this.UID=null;
        license=0;
        phoneNum=0;
    }
    public User(boolean _isTeacher, String _UID, long _license, int _phoneNum){
        this.level=3;
        this.isTeacher =_isTeacher;
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
    public void setUID(String UID) { this.UID = UID; }
    public void setLicense(long license) { this.license = license; }
    public void setPhoneNum(int phoneNum) { this.phoneNum = phoneNum; }

    public int getLevel() {
        return level;
    }
    public boolean isTeacher(){
        return isTeacher;
    }
    public String getUID() { return UID; }
    public long getLicense() { return license; }
    public int getPhoneNum() { return phoneNum; }

    public int updateLevel(){
        //if user consistently gets low grades take him down levels, if consistently gets high grades take up levels
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user=this;
        int prevLevel=user.getLevel();
        db.collection("tests").whereEqualTo("UID", this.getUID()).whereEqualTo("level", this.level).orderBy("timeTaken", Query.Direction.DESCENDING).limit(5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int amount=0;
                    double sumLastThree=0;
                    double sumLastFive=0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        amount++;
                        double thisGrade=document.toObject(Test.class).getGrade();
                        if(amount<=3)
                            sumLastThree+=thisGrade;
                        if(amount<=5)
                            sumLastFive+=thisGrade;
                        if(amount>5)
                            break;
                    }
                    double lastThreeAvg=sumLastThree/3;
                    double lastFiveAvg=sumLastFive/5;

                    if((lastThreeAvg>=90||lastFiveAvg>=80)&&user.getLevel()<5)
                        user.setLevel(prevLevel + 1);
                    else if(lastThreeAvg<=30||lastFiveAvg<=55)
                        user.setLevel(prevLevel-1);

                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                }
            }
        });
        return Integer.compare(user.getLevel(), prevLevel);
    }
}
