package com.example.lenovo.laundry;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class circle_menu extends AppCompatActivity {
   String arrayName[]={"Mens","Womens", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_menu);

        CircleMenu circleMenu=(CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.plus,R.drawable.multiply)
                .addSubMenu(Color.parseColor("#258CFF"),R.drawable.man)
                .addSubMenu(Color.parseColor("#6d4c41"),R.drawable.female)
                .addSubMenu(Color.parseColor("#ff0000"),R.drawable.other)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {
                        Toast.makeText(circle_menu.this,"you selected "+arrayName[i],Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(circle_menu.this, home.class);
                        startActivity(intent);
                    }
                });
   }
}
