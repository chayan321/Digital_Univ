package advaitam.digitaluniv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class StudentActivity extends AppCompatActivity {

   private LinearLayout mPhoneLayout;
    private LinearLayout mCodeLayout;


    private EditText mPhoneText;
    private EditText mCodeText;


    private ProgressBar mPhoneBar;
    private ProgressBar mCodeBar;
    private TextView mErrorText;

    private Button mSendbtn;
    private String mVerificationId;
     private int btnType=0;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private FirebaseAuth mAuth;


  //  private ProgressDialog progressDialog;
   // private FirebaseAuth firebaseAuth;


//    private void userlogin() {
//        String email = editText.getText().toString().trim();
//        String password = editText1.getText().toString().trim();
//        //checking if email and password are empty
//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(password)) {
//            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //if email and password are not empty
//        //displaying a progress dialog
//        progressDialog.setMessage("Registering Please wait.....");
//        progressDialog.show();
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//
//                        if (task.isSuccessful()) {
//                            finish();
//                            //start Login activity
//                            startActivity(new Intent(getApplicationContext(), StudentsInfo.class));
//
//                        }
//                    }
//                });
//
//
//    }
//
//    public void one() {
//        buttonLogin = (Button) findViewById(R.id.buttonLogin);
//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v == buttonLogin) {
//                    userlogin();        //method for log in
//                }
//            }
//        });
//    }
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);



//        firebaseAuth = firebaseAuth.getInstance();
//        progressDialog = new ProgressDialog(this);
//
//
//        if (firebaseAuth.getCurrentUser() != null) {
//            finish();
//            //Login activity here
//            startActivity(new Intent(getApplicationContext(), StudentsInfo.class));
//
//        }
//
//
//        one();


     mPhoneLayout=(LinearLayout) findViewById(R.id.phoneLayout);
     mCodeLayout=(LinearLayout) findViewById(R.id.codeLayout);

     mPhoneText=(EditText) findViewById(R.id.phoneEditText);
     mCodeText=(EditText) findViewById(R.id.codeEditText);

    mPhoneBar=(ProgressBar) findViewById(R.id.phoneProgress);
    mCodeBar=(ProgressBar) findViewById(R.id.codeProgress);
    mErrorText=(TextView)findViewById(R.id.errorText);

    mSendbtn=(Button) findViewById(R.id.buttonSend);
    mAuth=FirebaseAuth.getInstance();





//        Intent intent1 = new Intent(StudentActivity.this, MainActivity.class);
//        intent1.putExtra("e1",flag);








    mSendbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(btnType==0) {

                mPhoneBar.setVisibility(View.VISIBLE);
                mPhoneText.setEnabled(false);
                mSendbtn.setEnabled(false);

                String phoneNumber = mPhoneText.getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        StudentActivity.this,
                        mCallbacks
                );
            }else{
                mSendbtn.setEnabled(false);
                mCodeBar.setVisibility(View.VISIBLE);

                String verificationCode=mCodeText.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,verificationCode);
                signInWithPhoneAuthCredential(credential);
            }
        }
    });
    mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            mErrorText.setText("There was some error in verification");
            mErrorText.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {


            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;

            btnType=1;
            mPhoneBar.setVisibility(View.INVISIBLE);
            mCodeLayout.setVisibility(View.VISIBLE);
            mCodeText.setVisibility(View.VISIBLE);
            mSendbtn.setText("Verify Code");
            mSendbtn.setEnabled(true);

            // ...
        }
    };

   }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            FirebaseUser user = task.getResult().getUser();

                            Intent mIntent=new Intent(StudentActivity.this,StudentsInfo.class);
                            startActivity(mIntent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                           mErrorText.setText("There was some error in log in");
                           mErrorText.setVisibility(View.VISIBLE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
