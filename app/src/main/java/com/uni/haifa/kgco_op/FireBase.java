package com.uni.haifa.kgco_op;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FireBase {
    private Context context;
    private static FireBase instance = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static FireBase getInstance() {
        if (instance == null) {
            instance = new FireBase();
        }
        return instance;
    }

    public void createParent(Parent p) {
        Map<String, Object> person = new HashMap<>();
        person.put("userName", p.getUserName());
        person.put("email", p.getEmail());
        person.put("password", p.getPassword());
        person.put("licenseDate", p.getLicenseDate());
        Task<DocumentReference> documentReferenceTask = db.collection("Parents").add(person).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //TODO
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO
            }
        });
    }

    public void createChild(Child c) {
        Map<String, Object> child = new HashMap<>();
        child.put("Name", c.getName());
        child.put("parentID", c.getParentId());

        Task<DocumentReference> documentReferenceTask = db.collection("Children").add(child).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //TODO
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO
            }
        });
    }

    public void createSchedule(Schedule s) {
        Map<String, Object> schedule = new HashMap<>();
        schedule.put("morning", s.getMorning());
        schedule.put("evening", s.getEvening());
        schedule.put("date", s.getDate());
        schedule.put("morningKids", s.getEveningKids());
        schedule.put("eveningKids", s.getEveningKids());
        Task<DocumentReference> documentReferenceTask = db.collection("Schedules").add(schedule).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //TODO
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO
            }
        });
    }

    public void getAllParents() {
        Task<QuerySnapshot> parents = db.collection("Parents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //return Array
                        //Map<String, Object> user = document.getData();
                        //String name = (String) person.get("email);
                        document.getData();
                    }
                } else {
                    //todo error message
                }
            }
        });
    }

    public void getAllChildren() {
        Task<QuerySnapshot> parents = db.collection("Children").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //return Array
                        document.getData();
                    }
                } else {
                    //todo error message
                }
            }
        });
    }

    public void getAllSchedules() {
        Task<QuerySnapshot> parents = db.collection("Schedules").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //return Array
                        document.getData();
                    }
                } else {
                    //todo error message
                }
            }
        });
    }

    public void getParent(Parent p) {
        Task<QuerySnapshot> users = db.collection("Parents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(context, document.getId() + " => "
                                        + document.getData(), Toast.LENGTH_LONG).show();
                                Map<String, Object> user = document.getData();
                                String name = (String) user.get("first");
                                Number b = (Number) user.get("born");
                                System.out.println(b);
                            }
                        } else {
                            Toast.makeText(context, "Error getting documents."
                                    + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
