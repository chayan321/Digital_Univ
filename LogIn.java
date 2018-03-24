package advaitam.digitaluniv;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private Button button_Logout;
    private Button buttonUploaded;
    private Button buttonUploadImage;
    private Button studyMaterial;
//    private EditText myEditMail;
//    private EditText myEditPass;
//
//    private Button myAdd;
//
//    String myStringData,myStringData2;
//    Firebase myFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

//        myEditMail=(EditText) findViewById(R.id.email_st);
//        myEditPass=(EditText) findViewById(R.id.pass_st);
//        myAdd=(Button) findViewById(R.id.button_add);
//
//
        buttonUploaded=(Button)findViewById(R.id.buttonUploaded);
        buttonUploadImage=(Button)findViewById(R.id.buttonUploadImage);
        studyMaterial=(Button)findViewById(R.id.studyMaterial);
        buttonUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogIn.this,ViewUploadsActivity.class);
                startActivity(intent);
            }
        });

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogIn.this,ViewUploadsImageActivity.class);
                startActivity(intent);
            }
        });
       studyMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogIn.this,StudyMaterial.class);
                startActivity(intent);
            }
        });




        button_Logout = (Button) findViewById(R.id.button_Logout);
        button_Logout.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, AuthorizedActivity.class));
        }
//
//
//        Firebase.setAndroidContext(getApplicationContext());
//        String DeviceID= Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
//
//
//        myFirebase=new Firebase("https://digital-univ.firebaseio.com/Users" + DeviceID);
//        myAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myStringData=myEditMail.getText().toString();
//                myStringData2=myEditPass.getText().toString();
//                Firebase myNewChild=myFirebase.child(myStringData2);
//                myNewChild.setValue(myStringData);
//                Toast.makeText(LogIn.this,"UserName & Password is updated",Toast.LENGTH_SHORT);
//            }
//        });
//
//
//
//    }
//
    }

    @Override
    public void onClick(View view) {
        if (view == button_Logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, AuthorizedActivity.class));
        }
    }
}
