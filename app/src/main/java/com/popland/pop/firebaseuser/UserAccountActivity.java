package com.popland.pop.firebaseuser;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.net.URL;

public class UserAccountActivity extends AppCompatActivity {
ImageView IVphoto;
    TextView TVname, TVemail, TVid;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        IVphoto = (ImageView)findViewById(R.id.IVphoto);
        TVname = (TextView)findViewById(R.id.TVname);
        TVemail = (TextView)findViewById(R.id.TVemail);
        TVid = (TextView)findViewById(R.id.TVid);

        mAuth = FirebaseAuth.getInstance();

        //get user's info
        user = mAuth.getCurrentUser();
        if(user!=null){
            String name = user.getDisplayName();
            if(!TextUtils.isEmpty(name)){
                TVname.setText(name);
            }
            String email = user.getEmail();
            TVemail.setText(email);
            Uri photoUrl = user.getPhotoUrl();

            String id = user.getUid();
            TVid.setText(id);
        }
    }

    public void Update(View v){
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName("pass from editText")
                .setPhotoUri(Uri.parse("link URL"))
                .build();

        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("update: ","successful");
                        }
                    }
                });
    }

    public void Signout(View v){
        mAuth.signOut();
        Intent i = new Intent(UserAccountActivity.this,MainActivity.class);
        startActivity(i);
    }
}
