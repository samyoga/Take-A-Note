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
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.usernameid)
    EditText usernameId;

    @BindView(R.id.passwordid)
    EditText passwordId;

    @BindView(R.id.login)
    Button loginBtn;

    @BindView(R.id.log)
    TextView log;

    @BindView(R.id.signUp)
    TextView signUp;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameId.length() == 0){
                    showSnackBar("Enter username");
                    usernameId.requestFocus();
                } else if (passwordId.length() == 0){
                    showSnackBar("Enter Password");
                    passwordId.requestFocus();
                }
//                showData();
            }

            private boolean checkUser(String username, String password){
                RealmResults<Login> realmObjects = realm.where(Login.class).findAll();
                for (Login userDetails : realmObjects) {
                    if (username.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword())) {
                        Log.d("User Details", userDetails.getUsername());
                        return true;
                    }
            }
                Log.d("Value", String.valueOf(realm.where(Login.class).contains("username", username)));
                return false;
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    public void showData(){
        RealmResults<Login> studentResults = realm.where(Login.class).findAll();

        String op = "";
        realm.beginTransaction();
        for (Login guest:studentResults){
            op+=guest.toString();
        }

//        log.setText(op);
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
