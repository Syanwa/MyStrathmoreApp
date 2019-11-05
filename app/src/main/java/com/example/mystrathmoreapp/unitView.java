package com.example.mystrathmoreapp;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Locale;

import javax.annotation.Nullable;

public class unitView extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private DocumentReference mRefrence;
    private FirebaseFirestore mEvent_Ref;
    private TextView mUnitname;
    private TextView mLecturerName;
    private TextView mClassTime;
    private TextView mClassLoc;
    private TextView mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_view);

        mUnitname = findViewById(R.id.UnitName);
        mLecturerName = findViewById(R.id.lec_name);
        mClassTime = findViewById(R.id.ClassTime);
        mClassLoc = findViewById(R.id.ClassLocation);
        mEvents = findViewById(R.id.Event);



        //Get day of the week
        Calendar calendar = Calendar.getInstance();
        String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        Toast.makeText(unitView.this,day,Toast.LENGTH_SHORT).show();

        //Receive intent data
        String unit_id = getIntent().getExtras().getString("COURSE_ID");
        String unit_name = getIntent().getExtras().getString("UNIT_NAME");

        //firestore
        mFirestore = FirebaseFirestore.getInstance();
        assert unit_id != null;
        mRefrence = mFirestore.collection(day).document(unit_id);

        loadData(unit_name);


    }
        public void loadData(String unit_name){
            mRefrence.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                String unit_name = documentSnapshot.getString("unit_name");
                                String lecturer_name = documentSnapshot.getString("lecturer_name");
                                String class_time = documentSnapshot.getString("class_time");
                                String class_loc = documentSnapshot.getString("class_loc");
//                                String course = documentSnapshot.getString("Course");
//                                String year = documentSnapshot.getString("Year");

                                mLecturerName.setText(lecturer_name);
                                mClassTime.setText(class_time);
                                mUnitname.setText(unit_name);
                                mClassLoc.setText(class_loc);

                            } else {
                                Toast.makeText(unitView.this,"Document does not exist",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

           mFirestore.collection("Events").whereEqualTo("updateUnit",unit_name)
                   .addSnapshotListener(new EventListener<QuerySnapshot>() {
                       @Override
                       public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                           for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                               if(doc.getType() == DocumentChange.Type.ADDED){
                                String event_details = doc.getDocument().getString("updateDescription");
                                Log.d("TAG", "onEvent: "+event_details);
                                mEvents.setText(event_details);
//                                   Updates updates = doc.getDocument().toObject(Updates.class);
//                                   updatesList.add(updates);
//
//                                   updatesListAdapter.notifyDataSetChanged();
                               }
                               else if (doc.getType() == DocumentChange.Type.REMOVED){
                                   Log.d("TAG", "onEvent: Deleted");
                               }
                           }
                       }
                   });
        }
}
