package com.example.lenovo.laundry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class launcher extends AppCompatActivity {
    //private Button logout1;
   // private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        /*logout1 = (Button) findViewById(R.id.sign_out);
        firebaseAuth = FirebaseAuth.getInstance();
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(launcher.this, MainActivity.class));
            }
        });  */

        progressBar=(ProgressBar)findViewById(R.id.progress_bar_start);
        progressBar.setVisibility(progressBar.VISIBLE);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);   // set the duration of splash screen
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(launcher.this, home.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
        }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
