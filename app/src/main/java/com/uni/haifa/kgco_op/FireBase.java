package com.uni.haifa.kgco_op;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        db.collection("People")
                .document(p.getEmail()).set(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully written!",
                                Toast.LENGTH_LONG).show();
                        //todo
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error adding document" + e, Toast.LENGTH_LONG).show();
                        //todo
                    }
                });
    }

    public void createChild(Child c) {
        db.collection("Children")
                .document(Integer.toString(c.getId())).set(c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully written!",
                                Toast.LENGTH_LONG).show();
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
        db.collection("Schedules")
                .document(Integer.toString(s.getId())).set(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully written!",
                                Toast.LENGTH_LONG).show();
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


    public void setParent(Parent p) {
        db.collection("People")
                .document(p.getEmail()).set(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully written!",
                                Toast.LENGTH_LONG).show();
                        //todo
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error adding document" + e, Toast.LENGTH_LONG).show();
                        //todo
                    }
                });
    }

    public void setChild(Child c) {
        db.collection("Children")
                .document(Integer.toString(c.getId())).set(c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully written!",
                                Toast.LENGTH_LONG).show();
                        //TODO
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO
            }
        });
    }

    public void setSchedule(Schedule s){
        db.collection("Schedules")
                .document(Integer.toString(s.getId())).set(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully written!",
                                Toast.LENGTH_LONG).show();
                        //TODO
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO
            }
        });
    }

    public void deleteParent(Parent p) {
        db.collection("Parents")
                .document(p.getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully deleted!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error deleting document" + e,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void deleteChild(Child c) {
        db.collection("Parents")
                .document(Integer.toString(c.getId()))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully deleted!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error deleting document" + e,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void deleteSchedule(Schedule s) {
        db.collection("Parents")
                .document(Integer.toString(s.getId()))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully deleted!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error deleting document" + e,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }


}
