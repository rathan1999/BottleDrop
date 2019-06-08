package com.example.sairathan.botttledrop;

public class User {
  private String Name;
  private String email;
  private String Address;
  private long aadharcardnumber;
private double Lati,Longi;

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPostalcode() {
        return Postalcode;
    }

    public void setPostalcode(String postalcode) {
        Postalcode = postalcode;
    }

    private String Position,Area,City,Country,Postalcode;
    public User() {
    }

    public double getLati() {
        return Lati;
    }

    public void setLati(double lati) {
        Lati = lati;
    }

    public double getLongi() {
        return Longi;
    }

    public void setLongi(double longi) {
        Longi = longi;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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
}
