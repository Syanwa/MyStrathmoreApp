package com.example.mystrathmoreapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class timetable extends AppCompatActivity {




    private static final String TAG ="FireLog" ;
    private RecyclerView recyclerView;
    private FirebaseFirestore mFirestore;

    private List<Unit> unitsList;
    private UnitAdapter unitAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        unitsList = new ArrayList<>();
        unitAdapter = new UnitAdapter(getApplicationContext(),unitsList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(unitAdapter);

        //SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_info", MODE_PRIVATE);
        String course = sharedPreferences.getString("Course", "");
        String year = sharedPreferences.getString("Year", "");

        //Get day of the week
        Calendar calendar = Calendar.getInstance();
        String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
//        Log.d("day: ",day);

        //Firestore
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection(day)
                .whereEqualTo("Course",course)
                .whereEqualTo("Year",year)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots,FirebaseFirestoreException e) {
                        if(e != null){
                            Log.d(TAG, "ERROR :"+ e.getMessage());
                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                            if(doc.getType() == DocumentChange.Type.ADDED){

                                String id = doc.getDocument().getId();
                                Unit unit = doc.getDocument().toObject(Unit.class).withId(id);
                                unitsList.add(unit);
                                unitAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}

