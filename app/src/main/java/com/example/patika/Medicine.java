package com.example.patika;

public class Medicine {
    private String name;
    private String info;
    private String price;
    private float ratedInfo;
    private final int imageResorce;


    public Medicine(String name, String info, String price, float ratedInfo, int imageResorce) {
        this.name = name;
        this.info = info;
        this.price = price;
        this.ratedInfo = ratedInfo;
        this.imageResorce = imageResorce;
    }
    String getName() {return name;}
    String getInfo() {return info;}
    String getPrice() {return price;}
    float getRatedInfo() {return ratedInfo;}
    public int getImageResorce() {return imageResorce; }

}
