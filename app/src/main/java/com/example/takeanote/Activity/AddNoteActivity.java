package com.example.takeanote.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takeanote.R;
import com.example.takeanote.models.Note;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    @BindView(R.id.EditTextTitle)
    EditText title;

    @BindView(R.id.EditTextNote)
    EditText note;

    @BindView(R.id.Add)
    Button add;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add();
            }
        });
    }

    public void Add(){

        String Title = title.getText().toString();
        String Notes = note.getText().toString();

        realm.beginTransaction();
        Note notedetails = realm.createObject(Note.class,  UUID.randomUUID().toString());

        notedetails.setTitle(Title);
        notedetails.setNote(Notes);
        realm.commitTransaction();
        finish();

        Toast.makeText(getApplicationContext(), "Note successfully added!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddNoteActivity.this, ShowNoteActivity.class);
        startActivity(intent);
    }
}
