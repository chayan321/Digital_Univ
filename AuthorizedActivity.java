package advaitam.digitaluniv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthorizedActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editText;
    private EditText editText1;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    private void userlogin() {
        String email = editText.getText().toString().trim();
        String password = editText1.getText().toString().trim();
        //checking if email and password are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        //if email and password are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Registering Please wait.....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            finish();
                            //start Login activity
                            startActivity(new Intent(getApplicationContext(), LogIn.class));

                        }
                    }
                });


    }

    public void one() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonLogin) {
                    userlogin();        //method for log in
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);


        editText = (EditText) findViewById(R.id.editText);
        editText1 = (EditText) findViewById(R.id.editText1);
        firebaseAuth = firebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            //Login activity here
            startActivity(new Intent(getApplicationContext(), LogIn.class));

        }


        one();
    }
}
