package com.example.sairathan.botttledrop;

public class User {
  private String Name;
  private String email;
  private long aadharcardnumber;
  private long aadharnumber;

    public User() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getAadharcardnumber() {
        return aadharcardnumber;
    }

    public void setAadharcardnumber(long aadharcardnumber) {
        this.aadharcardnumber = aadharcardnumber;
    }

    public long getAadharnumber() {
        return aadharnumber;
    }

    public void setAadharnumber(long aadharnumber) {
        this.aadharnumber = aadharnumber;
    }
}
