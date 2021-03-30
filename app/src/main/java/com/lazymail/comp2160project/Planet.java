package com.lazymail.comp2160project;

import org.json.JSONException;
import org.json.JSONObject;

public class Planet {
    //DATA OF A PLANET.
    private String body ="";
    private String parentBody ="";
    private String mass = "";
    private String volume = "";
    private String meanRadius = "";
    private String gravity = "";
    private String siderealOrbitalPeriod = "";
    private String siderealRotationPeriod = "" ;
    private String semiMajorAxis= "";
    private boolean expanded;


    //CONSTRUCTOR - CONVERT JSONOject TO PLANET
    public Planet(JSONObject jsonObject) throws JSONException {
        this.body = (String) jsonObject.getString("Body");
        this.parentBody = (String) jsonObject.getString("ParentBody");
        this.mass = jsonObject.getString("Mass");
        this.volume = jsonObject.getString("Volume");
        this.meanRadius =  jsonObject.getString("MeanRadius");
        this.gravity =  jsonObject.getString("Gravity");
        this.siderealOrbitalPeriod = jsonObject.getString("SiderealOrbitalPeriod");
        this.siderealRotationPeriod = jsonObject.getString("SiderealRotationPeriod");
        this.semiMajorAxis = jsonObject.getString("SemiMajorAxis");


    }
    @Override
    public String toString(){
        return  "\n"+ body
                + "\n"+parentBody
                + "\n" +mass
                + "\n" +volume
                + "\n"+meanRadius
                + "\n"+gravity
                + "\n"+meanRadius
                + "\n"+siderealOrbitalPeriod
                + "\n"+siderealRotationPeriod;
    }
}

