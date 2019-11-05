package com.example.mystrathmoreapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class updatesActivity extends AppCompatActivity {

    private static final String TAG = "FireStore";
    private RecyclerView eventRecyclerView;
    private FirebaseFirestore mFirestore;
    private UpdatesListAdapter updatesListAdapter;
    private List<Updates> updatesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        updatesList = new ArrayList<>();
        updatesListAdapter = new UpdatesListAdapter(updatesList);

        eventRecyclerView = findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setHasFixedSize(true);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventRecyclerView.setAdapter(updatesListAdapter);

        //sharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_info", MODE_PRIVATE);
        String course = sharedPreferences.getString("Course", "");
        String year = sharedPreferences.getString("Year", "");

        mFirestore = FirebaseFirestore.getInstance();


        mFirestore.collection("Events")
                .whereEqualTo("Course",course)
                .whereEqualTo("Year",year)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){

                    Log.d(TAG,"Error:" + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
//                        String event_details = doc.getDocument().getString("event_details");
//                        Log.d(TAG, "onEvent: "+event_details);
                        Updates updates = doc.getDocument().toObject(Updates.class);
                        updatesList.add(updates);

                        updatesListAdapter.notifyDataSetChanged();
                    }
                    else if (doc.getType() == DocumentChange.Type.REMOVED){
                        Log.d(TAG, "onEvent: Deleted");
                    }
                }
            }
        });
    }
}
