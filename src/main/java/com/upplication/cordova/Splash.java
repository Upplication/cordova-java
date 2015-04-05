package com.upplication.cordova;

public class Splash {

    private String src;
    private Integer height;
    private Integer width;
    private String density;

    public static Splash create() {
        return new Splash();
    }

    public Splash src(String src) {
        this.src = src;
        return this;
    }

    public Splash height(Integer height){
        this.height = height;
        return this;
    }

    public Splash width(Integer width) {
        this.width = width;
        return this;
    }

    public Splash density(String density) {
        this.density = density;
        return this;
    }

    public String getSrc() {
        return src;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public String getDensity() {
        return density;
    }
}
