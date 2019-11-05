package com.example.mystrathmoreapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class profile extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private DocumentReference mRefrence;
    private static final String TAG ="Firestore";

    private TextView mCourse;
    private TextView mFirstname;
    private TextView mLastname;
    private TextView mEmail;
    private TextView mAdmission_year;
    private TextView mGraduation_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mCourse = findViewById(R.id.course);
        mFirstname = findViewById(R.id.first_name);
        mLastname = findViewById(R.id.last_name);
        mEmail = findViewById(R.id.email);
        mAdmission_year = findViewById(R.id.admission_YearTextView);
        mGraduation_year = findViewById(R.id.graduation_YearTextView);

        //Call for SharedPreference
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("preference_file_key", MODE_PRIVATE);
        String user_email = sharedPreferences.getString("Email", "None");

        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("users").whereEqualTo("Email",user_email)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.d(TAG, "ERROR :"+ e.getMessage());
                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                            String first_name = doc.getDocument().getString("First name");
                            String last_name = doc.getDocument().getString("Last name");
                            String course = doc.getDocument().getString("Course");
                            String email = doc.getDocument().getString("Email");
                            String admission_year = doc.getDocument().getString("Admission year");
                            String graduation_year = doc.getDocument().getString("Graduation year");

                            personalise_profile(first_name,last_name,course,email,admission_year,graduation_year);

                        }

                    }
                });

    }

    public void personalise_profile(String first_name , String last_name, String course, String Email,String admission_year,
                                    String graduation_year){
        mCourse.setText(course);
        mFirstname.setText(first_name);
        mLastname.setText(last_name);
        mEmail.setText(Email);
        mGraduation_year.setText(graduation_year);
        mAdmission_year.setText(admission_year);

        Log.d(TAG,course);
    }
}
