package com.example.learningelectricityforbagrut;

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

    public void updateLevel(double grade){
        //if user got low grade take him down levels, if got a high grade take up levels
        //TODO: decide on grade ranges later
    }
}
