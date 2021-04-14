package com.ossovita.firestoreexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("Notebook/My First Note");//db.collection("Notebook").document("My First Note"); bu şekilde de referans verebiliriz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewData = findViewById(R.id.text_view_data);

    }

    @Override
    protected void onStart() {//listenerı başlat(dinlemeye başla)
        super.onStart();
        noteRef.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {//hata yoksa
                    if (value.exists()) {//gelen veri varsa
                        String title = value.getString(KEY_TITLE);
                        String description = value.getString(KEY_DESCRIPTION);

                        Map<String, Object> note = value.getData();
                        textViewData.setText("Title: " + title + "\nDescription: " + description);
                    }else{//gelen veri yoksa
                        textViewData.setText("");
                    }
                } else {//hata varsa
                    Toast.makeText(MainActivity.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onEvent: Error: " + error.toString());
                }

            }
        });
    }


    public void saveNote(View v) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, title);
        note.put(KEY_DESCRIPTION, description);
        //db.document("Notebook/MyFirstNote"); bu şekilde de yazılabilir.
        noteRef.set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Note saved successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
            }
        });
    }

    public void updateDescription(View v){
        String description = editTextDescription.getText().toString();
        noteRef.update(KEY_DESCRIPTION,description);

    }

    public void deleteDescription(View v){
        noteRef.update(KEY_DESCRIPTION,FieldValue.delete());//bu key'e sahip veriyi sil
    }

    public void deleteNote(View v){
        noteRef.delete();//işaret edilen path'deki notu sil
    }

    public void loadNote(View v) {
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {//eğer döküman varsa
                            String title = documentSnapshot.getString(KEY_TITLE);
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);

                            Map<String, Object> note = documentSnapshot.getData();
                            textViewData.setText("Title: " + title + "\nDescription: " + description);
                        } else { //döküman yoksa
                            Toast.makeText(MainActivity.this, "Document does not exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });


    }
}