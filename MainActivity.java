package advaitam.digitaluniv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private Button sButton;
private Button aButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sButton=(Button) findViewById(R.id.button_st);
        aButton=(Button) findViewById(R.id.button_au);


        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,StudentActivity.class);
                startActivity(intent);
            }
        });


        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AuthorizedActivity.class);
                startActivity(intent);
            }
        });
    }
}
