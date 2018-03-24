package advaitam.digitaluniv;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;


public class SplashScreen extends AppCompatActivity {
    private TextView textView1;
   // private ImageView splashImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);




//        ImageView splashImageView = (ImageView)findViewById(R.id.splash);
//        Glide.with(SplashScreen.this).load(R.drawable.logo).into(splashImageView);
//
//
//
//        Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
//
//        splashImageView.setAnimation(zoomout);
//
//        final Intent intent=new Intent(SplashScreen.this,MainActivity.class);
//        Thread timerThread = new Thread(){
//            public void run() {
//                try {
//                    sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };timerThread.start();


                    //animation purpose.
       // textView1= (TextView)findViewById(R.id.textView1);      //animation purpose.
              //animation purpose.
       // Animation myanim= AnimationUtils.loadAnimation(this,R.anim.mytransition); //animation purpose..for loading animation to the image and texts
          //animation purpose.. loading animation to the image
       // textView1.startAnimation(myanim);   //animation purpose.
         //animation purpose.
        // animation purpose.....i have made mytransition.xml in res>>anim folder


       // code start ....for transition of splash screen to main screen
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will run after 3 sec
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();   // when we press back button then it willnot allow to go to splash screen again. it will ext from the app
            }
        },2000);
    }
}
