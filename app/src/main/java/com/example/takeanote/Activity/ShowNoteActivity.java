package com.example.takeanote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.takeanote.R;
import com.example.takeanote.models.Note;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class ShowNoteActivity extends AppCompatActivity {

    @BindView(R.id.ShowTextNote)
            TextView showNote;

    @BindView(R.id.ShowTextTitle)
            TextView showTitle;

    Realm realm;

    private RecyclerView mNoteList;
//    ArrayList<Note> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        ButterKnife.bind(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

//        mNoteList = findViewById(R.id.rv);
//        mNoteList.setHasFixedSize(true);
//        mNoteList.setLayoutManager(new LinearLayoutManager(this));


        RealmResults<Note> results = realm.where(Note.class).findAllAsync();
        for (Note note:results) {
//            arrayList.clear();
          String titleShow = note.getTitle();
          String noteShow = note.getNote();

          showTitle.setText(titleShow);
          showNote.setText(noteShow);

//            String id = note.getId();

//          arrayList.add(new Note(id));

//            mAdapter = new NoteAdapter(ShowNoteActivity.this, results);
//            mNoteList.setLayoutManager(new LinearLayoutManager(ShowNoteActivity.this));
//            mNoteList.setAdapter(mAdapter);

          finish();
        }

    }

    public void onDestroy() {


        super.onDestroy();
    }
}
