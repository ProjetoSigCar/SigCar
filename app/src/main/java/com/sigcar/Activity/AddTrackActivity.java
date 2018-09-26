package com.sigcar.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sigcar.Adapter.TrackListAdapter;
import com.sigcar.Classes.Track;
import com.sigcar.R;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {


    TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    Button buttonAddTrack;
    ListView listViewTracks;

    DatabaseReference databaseTracks;


    List<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        textViewArtistName = (TextView) findViewById(R.id.textViewArtistName);
        editTextTrackName = (EditText) findViewById(R.id.editTextTrackName);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
        buttonAddTrack = (Button) findViewById(R.id.buttonAddTrack);

        listViewTracks = (ListView) findViewById(R.id.listViewTracks);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ARTIST_ID");
        String name = intent.getStringExtra("ARTIST_NAME");
        trackList = new ArrayList<>();

        textViewArtistName.setText(name);

        databaseTracks = FirebaseDatabase.getInstance().getReference("Tracks").child(id);

        buttonAddTrack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                saveTrack();
            }
        });
    }


    @Override
    protected void onStart() {

        super.onStart();

        databaseTracks.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                trackList.clear();

                for(DataSnapshot trackSnapshot : dataSnapshot.getChildren()) {

                    Track track = trackSnapshot.getValue(Track.class);

                    trackList.add(track);                }


                TrackListAdapter adapter = new TrackListAdapter(AddTrackActivity.this, trackList);


                listViewTracks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    private void saveTrack() {

        String trackName = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(!TextUtils.isEmpty(trackName)) {

            String id = databaseTracks.push().getKey();
            Track track = new Track(id, trackName, rating);
            databaseTracks.child(id).setValue(track);
            Toast.makeText(this, "Trilha gravada com sucesso!", Toast.LENGTH_LONG).show();
        }
        else {

            Toast.makeText(this, "Precisa digitar o nome da trilha!", Toast.LENGTH_SHORT).show();
        }
    }
}