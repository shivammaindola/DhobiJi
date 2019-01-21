package model;

import java.util.Locale;

/**
 * Created by Lenovo on 04-05-2018.
 */

public class Category{
    private String Name;
    private String Image;
    private String Price;
    private String Description;
    private String Quantity;

  public Category(String name, String image, String price,String description,String quantity){
        Name= name;
        Image= image;
        Price=price;
        Description=description;
        Quantity=quantity;
  }

  public Category(){

    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDescription(){
        return Description;
    }
    public String getName(){
      return Name;
    }
    public String getPrice(){
        return Price;
    }
    public String getImage(){
        return Image;
    }

    public void setDescription(String description){ Description=description;}
    public void setName(String name){
    Name= name;
 }
    public void setPrice(String price){
        Price=price;
    }

    public void setImage(String image){
        Image=image;
    }

}
