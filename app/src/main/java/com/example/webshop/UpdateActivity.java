package com.example.webshop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 78;
    private SharedPreferences preferences;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText phoneNumberEditText;

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        Log.i(LOG_TAG, "onCreate");

        user = mAuth.getCurrentUser();
        String UID = user.getUid();
        DocumentReference userRef = db.collection("users").document(UID);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(LOG_TAG, "DocumentSnapshot data: " + document.getData());
                        String tempi = "";
                        tempi=(String) document.get("userName");
                        String tempii = "";
                        tempii=(String) document.get("phoneNumber");
                        profile = new Profile(tempi,user.getEmail(),"...",tempii);
                        userNameEditText.setText(profile.getUserName());
                        userEmailEditText.setText(profile.getEmail());
                        phoneNumberEditText.setText(profile.getPhoneNumber());
                    } else {
                        Log.d(LOG_TAG, "No such document");
                    }
                } else {
                    Log.d(LOG_TAG, "get failed with ", task.getException());
                }
            }
        });


    }

    public void updateData(View view) {
        if(!passwordEditText.getText().toString().isEmpty()) {
            if(!userEmailEditText.getText().toString().isEmpty()) {
                profile.setUserName(userNameEditText.getText().toString());
                profile.setEmail(userEmailEditText.getText().toString());
                profile.setPassword(passwordEditText.getText().toString());
                profile.setPhoneNumber(phoneNumberEditText.getText().toString());
                String UID = user.getUid();
                user.updatePassword(profile.getPassword());
                user.updateEmail(profile.getEmail());
                Log.d(LOG_TAG, "Email jelszó frissítve");
                DocumentReference userRef = db.collection("users").document(UID);
                userRef
                        .update("userName", profile.getUserName(),"phoneNumber",profile.getPhoneNumber())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(LOG_TAG, "DocumentSnapshot successfully updated!");
                                Toast.makeText(UpdateActivity.this, "User frissítve", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(LOG_TAG, "Error updating document", e);
                            }
                        });
            }
            else{
                Toast.makeText(UpdateActivity.this, "Nem lehet üres az új email", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(UpdateActivity.this, "Nem lehet üres az új jelszó", Toast.LENGTH_LONG).show();
        }
    }

    public void cancel(View view) {
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }
}