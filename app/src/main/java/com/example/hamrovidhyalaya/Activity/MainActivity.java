package com.example.hamrovidhyalaya.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hamrovidhyalaya.R;
import com.example.hamrovidhyalaya.models.Login;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.usernameid)
    EditText usernameId;

    @BindView(R.id.passwordid)
    EditText passwordId;

    @BindView(R.id.login)
    Button loginBtn;

    @BindView(R.id.log)
    TextView log;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loginBtn.setOnClickListener(this);

        realm = Realm.getDefaultInstance();

    }

    public void onClick(View view){
        writeToDB(usernameId.getText().toString().trim(), passwordId.getText().toString().trim());
        showData();
    }

    public void showData(){
        RealmResults<Login> studentResults = realm.where(Login.class).findAll();

        String op = "";
        realm.beginTransaction();
        for (Login guest:studentResults){
            op+=guest.toString();
        }

        log.setText(op);
    }

    public void writeToDB(final String username, final String password){
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
              }
          }, new Realm.Transaction.OnError() {
              @Override
              public void onError(Throwable error) {
                  Log.e("Database", error.getMessage());
              }
          }
        );
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
