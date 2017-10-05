package com.popland.pop.firebaseuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText edtEmail ,edtPass;
    TextView TVforget;
    Button btnDangki, btnDangnhap;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edtEmail = (EditText)findViewById(R.id.EDTemail);
        edtPass = (EditText)findViewById(R.id.EDTpass);
        TVforget = (TextView)findViewById(R.id.TVforget);
        btnDangki = (Button)findViewById(R.id.BTNdangki);
        btnDangnhap = (Button)findViewById(R.id.BTNdangnhap);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user!=null){
                    Log.d("AuthState: ",user.getUid()+": user signed in");
                }else{
                    Log.d("AuthState: ","user signed out");
                }
            }
        };

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKi();
            }
        });

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangNhap();
            }
        });

        TVforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "nguyenpk007@gmail.com";
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                           Log.d("task: ","email sent");
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void DangKi(){
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            Toast.makeText(MainActivity.this, "authentication succeeds", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "authentication fails", Toast.LENGTH_LONG).show();

                    }
                });
        Intent i = new Intent(MainActivity.this,UserAccountActivity.class);
        startActivity(i);
    }

    public void DangNhap(){
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"oops",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        Intent i = new Intent(MainActivity.this,UserAccountActivity.class);
        startActivity(i);
    }
}
