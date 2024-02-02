package com.example.fwm21;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private CollectionReference usersCollection;

    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        usersCollection = firestore.collection("users");

        usersListView = findViewById(R.id.user_list);





        fetchUsersFromDatabase();
    }

    private void addUserToDatabase(String name, String email) {
        // Generate a unique document ID using push() (auto-generated ID).
        String userId = usersCollection.document().getId();

        // Create a map to represent the user data.
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        // Set the value in the Firestore collection.
        usersCollection.document(userId).set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Admin.this, "User added successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Admin.this, "Failed to add user.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteUserFromDatabase(String userEmail) {
        // Query to find the user by email in Firestore
        usersCollection.whereEqualTo("email", userEmail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    // Delete the user document from Firestore
                    usersCollection.document(documentSnapshot.getId()).delete().addOnCompleteListener(deleteTask -> {
                        if (deleteTask.isSuccessful()) {
                            Toast.makeText(Admin.this, "User deleted successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Admin.this, "Failed to delete user.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(Admin.this, "Error occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUsersFromDatabase() {
        usersCollection.addSnapshotListener(new EventListener<com.google.firebase.firestore.QuerySnapshot>() {


            @Override
            public void onEvent(@androidx.annotation.Nullable com.google.firebase.firestore.QuerySnapshot queryDocumentSnapshots, @androidx.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(Admin.this, "Error occurred while fetching users.", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> usersList = new ArrayList<>();
                assert queryDocumentSnapshots != null;
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Map<String, Object> user = documentSnapshot.getData();
                    if (user != null) {
                        String name = (String) user.get("name");
                        String email = (String) user.get("email");
                        String userInfo = "Name: " + name + ", Email: " + email;
                        usersList.add(userInfo);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Admin.this, android.R.layout.simple_list_item_1, usersList);
                usersListView.setAdapter(adapter);
            }
        });
    }
}


