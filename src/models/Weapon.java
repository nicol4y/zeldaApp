package com.example.workshop30.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Weapon implements Serializable {
    public int attack;
    public String category;
    @SerializedName("common_locations")
    private List<String> commonLocations;
    public int defense;
    public String description;
    public int id;
    public String image;
    public String name;
    public ArrayList<String> getLoc(){
        if(commonLocations!=null){
            ArrayList<String> temp = new ArrayList<>(commonLocations);
    return temp;}
    return null;}
public String getDescription(){return description;}

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
    int getId(){
        return id;
    }
    String getCategory(){
        return category;
    }
   public String getImage(){
        return image;
    }
    String getAttackS(){
        return "Attack: "+getAttack() ;
    }
    String getDefS(){
        return "Defense: "+getDefense() ;
    }
    public int getAttack(){
        return attack;
    }
    public int getDefense(){
        return defense;
    }


}