package com.example.mypersonaltrainer;

        import java.util.jar.Attributes;

public class FoodItem {
    public String id;
    public String name;
    public String des;


    public FoodItem(String name,String des,String id){
        this.name = name;
        this.des = des;
        this.id = id;


    }
    public String toString(){return name;}

}
