package com.example.hamrovidhyalaya.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hamrovidhyalaya.R;
import com.example.hamrovidhyalaya.models.Login;
import com.google.android.material.snackbar.Snackbar;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.usernameid)
    EditText usernameId;

    @BindView(R.id.passwordid)
    EditText passwordId;

    @BindView(R.id.signUp)
    Button signUpBtn;

    @BindView(R.id.login)
    TextView login;

    Realm realm;
    private Login userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        Realm.getDefaultInstance();

        signUpBtn.setOnClickListener(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view){
        if (usernameId.length() == 0){
            showSnackBar("Enter username");
            usernameId.requestFocus();
        } else if (passwordId.length() == 0){
            showSnackBar("Enter Password");
            passwordId.requestFocus();
        } else{
            try{

//                realm.beginTransaction();
//
//                userDetails = realm.createObject(Login.class);
//                userDetails.setUsername(usernameId.getText().toString());
//                userDetails.setPassword(passwordId.getText().toString());
//
//                realm.commitTransaction();
//
//                showSnackBar("Save Success");

                writeToDB(usernameId.getText().toString().trim(), passwordId.getText().toString().trim());

            }catch (RealmPrimaryKeyConstraintException e){
                e.printStackTrace();
                showSnackBar("User already exists");
            }
        }

    }

    public void writeToDB(String username, String password){
        realm.executeTransactionAsync(new Realm.Transaction() {
              @Override
              public void execute(Realm bgRealm) {

                  Login user = bgRealm.createObject(Login.class, UUID.randomUUID().toString());
                  user.setUsername(username);
                  user.setPassword(password);

              }
          }, new Realm.Transaction.OnSuccess() {
              @Override
              public void onSuccess() {
                  Log.v("Database", "Data inserted");
                  Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                  startActivity(intent);
              }
          }, new Realm.Transaction.OnError() {
              @Override
              public void onError(Throwable error) {
                  Log.e("Database", error.getMessage());
              }
          }
        );
    }

    private void showSnackBar(String msg) {
        try {
            Snackbar.make(findViewById(R.id.content), msg, Snackbar.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
