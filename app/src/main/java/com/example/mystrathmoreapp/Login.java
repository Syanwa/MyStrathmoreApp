package com.example.mystrathmoreapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import static com.example.mystrathmoreapp.R.layout.activity_main;

public class Login extends AppCompatActivity {

    public  String Email = "Email";
    public  String FirstName ,LastName,Course;
    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);

        mFirestore = FirebaseFirestore.getInstance();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //buttons
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailField.getText().toString();
                final String password = mPasswordField.getText().toString();
               signIn(email,password);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }



        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            assert user != null;
                            String user_email = user.getEmail();

                            session_management (user_email);
                            openActivity (user_email);

//                           updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                           updateUI(null);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    public void openActivity(String user){
        Intent intent = new Intent(this,menu.class);
        startActivity(intent);
    }


    //session management
    public void session_management(final String email){
        mFirestore.collection("users")
                .whereEqualTo("Email",email)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG, "ERROR :"+ e.getMessage());
                }

                assert documentSnapshots != null;
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    String first_name = doc.getDocument().getString("First name");
                    String last_name = doc.getDocument().getString("Last name");
                    String course = doc.getDocument().getString("Course");
                    String email = doc.getDocument().getString("Email");
                    String admission_year = doc.getDocument().getString("Admission year");
                    String graduation_year = doc.getDocument().getString("Graduation year");
                    String year = doc.getDocument().getString("Year");


                    SharedPreferences user_info = getSharedPreferences("user_info",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit_user = user_info.edit();
                    edit_user.putString("FirstName",first_name);
                    edit_user.putString("LastName",last_name);
                    edit_user.putString("Course",course);
                    edit_user.putString("Email",email);
                    edit_user.putString("Admission Year",admission_year);
                    edit_user.putString("Graduation Year",graduation_year);
                    edit_user.putString("Year",year);
                    edit_user.apply();
                    edit_user.commit();

                }
            }
        });

    }
}
