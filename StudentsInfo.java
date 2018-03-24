package advaitam.digitaluniv;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class StudentsInfo extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_AN_REQUEST =234;
    private FirebaseAuth firebaseAuth;
    private Button button_Logout;
    private ImageView imageView;
    private EditText txtImageName;
    private Button buttonChoose,buttonUpload,buttonChoosepdf;
    private Uri filePath;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    public static final String FB_STORAGE_PATH="image/";
    public static final String FB_DATABASE_PATH="Students_Photo";
    private TextView studyM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_info);
        button_Logout=(Button)findViewById(R.id.button_Logout);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        buttonChoose=(Button) findViewById(R.id.buttonChoose);
        buttonUpload=(Button) findViewById(R.id.buttonUpload);
        imageView=(ImageView) findViewById(R.id.imageView);
        buttonChoosepdf=(Button)findViewById(R.id.buttonChoosepdf);
        txtImageName=(EditText)findViewById(R.id.imageName);
       studyM=(TextView)findViewById(R.id.studyM);
        button_Logout.setOnClickListener(this);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

        buttonChoosepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentsInfo.this,PdfActivity.class);
                startActivity(intent);
            }
        });
       studyM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentsInfo.this,ViewUploadsStudyMaterial.class);
                startActivity(intent);
            }
        });






        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,StudentActivity.class));
        }

    }
    private void showFileChooser(){
        Intent mIntent=new Intent();
        mIntent.setType("image/*");
        mIntent.setAction(Intent.ACTION_GET_CONTENT);

//        Intent nIntent=new Intent();
//        nIntent.setType("application/pdf");
//        nIntent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(Intent.createChooser(mIntent,"Select an File"),PICK_AN_REQUEST);
    }

    private void uploadFile(){

        if(filePath!=null) {

            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference riversRef = mStorageRef.child(FB_STORAGE_PATH+System.currentTimeMillis()+"."+getImageExt(filePath));

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Photo Uploaded", Toast.LENGTH_SHORT).show();

                                ImageUpload imageUpload = new ImageUpload(txtImageName.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                                String uploadId = mDatabaseRef.push().getKey();
                                mDatabaseRef.child(uploadId).setValue(imageUpload);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),exception.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage(((int)progress)+" % Uploaded...");
                }
            })
            ;
        }else{

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_AN_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath=data.getData();


            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==button_Logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,StudentActivity.class));
        }
     else
        if(v==buttonChoose){
            showFileChooser();

        }else
            if(v==buttonUpload){
                   uploadFile();
            }


    }
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

}
