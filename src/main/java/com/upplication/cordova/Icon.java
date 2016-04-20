package com.upplication.cordova;

/**
 * icon element of the config.xml
 * https://cordova.apache.org/docs/en/latest/config_ref/images.html
 */
public class Icon {

    private String src;
    private Integer height;
    private Integer width;
    private String density;

    public static Icon create() {
        return new Icon();
    }

    public Icon src(String src) {
        this.src = src;
        return this;
    }

    public Icon height(Integer height){
        this.height = height;
        return this;
    }

    public Icon width(Integer width) {
        this.width = width;
        return this;
    }

    public Icon density(String density) {
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
