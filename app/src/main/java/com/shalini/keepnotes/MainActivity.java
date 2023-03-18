package com.shalini.keepnotes;
//import androidx.appcompat.app.AppCompatActivity;
////whats
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
 import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//
import java.util.Objects;
public class MainActivity extends AppCompatActivity {

    private EditText mloginemail,mloginpassword;

    public FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        //data variables
        mloginemail=findViewById(R.id.loginemail);
        mloginpassword=findViewById(R.id.loginpassword);

        //UI variables
        Button mlogin = findViewById(R.id.login);
        Button mgotosignup = findViewById(R.id.gotosignup);
        TextView mTextView = findViewById(R.id.TextView);

        //f
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,forgotpassword.class);
                startActivity(intent);
            }
        });

        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,signup.class);
                startActivity(intent);
            }
        });
            // jab user signup kr chuka hai account activate ho gya hia
        // tab vo login kr skta hia... using email and password.........
        //so here we need to verify if the user is registered or not.... if he is not registered then we'll show a toast(notification)
        //to handle login button
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_email=mloginemail.getText().toString();
                String text_password=mloginpassword.getText().toString();

                if (TextUtils.isEmpty(text_email)||TextUtils.isEmpty(text_password)){
                    Toast.makeText(MainActivity.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
                }else if (text_password.length()<6){
                    Toast.makeText(MainActivity.this, "Password must be of minimum 6 characters", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(text_email,text_password);
                }
            }
        });

    }

    private void loginUser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            //if(task.isSuccessful())
            if(task.isComplete())//just for checking I have used this isComplete, if it does not work then use isSuccessful
            {
                checkEmailVerification();//this fun will check if the account is verified or not
            }
            else
            {
                Toast.makeText(MainActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
    private void checkEmailVerification() {
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        /*To avoid Null Pointer Exception*/ // assert Fu!= null;
        boolean man = false;
           // Toast.makeText(this, "entered into check email", Toast.LENGTH_SHORT).show();
        assert firebaseUser != null;
        if(firebaseUser.isEmailVerified())
            {
                Toast.makeText(this, "Logged in ", Toast.LENGTH_SHORT).show();
                finish();
                man=true;
            }
            else
            {
                Toast.makeText(MainActivity.this, "Please check email and verify your account ", Toast.LENGTH_SHORT).show();
            }
        if (man) {
            Intent intent=new Intent(MainActivity.this,NotesActivity.class);
            startActivity(intent);
        }
    }
}
