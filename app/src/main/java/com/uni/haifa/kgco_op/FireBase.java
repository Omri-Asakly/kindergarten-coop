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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
        db.collection("People")
                .document(p.getEmail()).set(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, "DocumentSnapshot successfully written!",
//                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error adding document" + e, Toast.LENGTH_LONG).show();
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
        List<String> m = Arrays.asList(s.getMorningKids());
        List<String> e = Arrays.asList(s.getEveningKids());
        Schedule schedule = new Schedule(s.getId(), s.getMorning(), s.getEvening(), s.getDate(), m, e);
        System.out.println("scheduleeeee"    +    schedule);
        db.collection("Schedules")
                .document(Integer.toString(s.getId())).set(schedule)
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

    public List<Parent> getAllParents() {
        List<Parent> parentList = new ArrayList<>();
        Task<QuerySnapshot> parents = db.collection("Parents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //return Array
                        Map<String, Object> user = document.getData();
                        int id = (Integer) user.get("id");
                        String email = (String) user.get("email");
                        String name = (String) user.get("userName");
                        String password = (String) user.get("password");
                        Date date = (Date) user.get("licenseDate");
                        Parent p = new Parent(id, name, email, password, date);
                        parentList.add(p);
                    }
                } else {
                    //todo error message
                }
            }
        });
        return parentList;
    }

    public List<Child> getAllChildren() {
        List<Child> childrenList = new ArrayList<>();

        Task<QuerySnapshot> children = db.collection("Children").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> user = document.getData();
                        int id = (Integer) user.get("id");
                        int parentID = (Integer) user.get("parentId");
                        String name = (String) user.get("name");
                        Child c = new Child(id, parentID, name);
                        childrenList.add(c);
                    }
                } else {
                    //todo error message
                }
            }
        });
        return childrenList;
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> scheduleList = new ArrayList<>();

        Task<QuerySnapshot> schedules = db.collection("Schedules").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> user = document.getData();
                        int id = (Integer) user.get("id");
                        String morning = (String) user.get("morning");
                        String evening = (String) user.get("evening");
                        Date date = (Date) user.get("date");
                        List<String> morningKidsList = (List<String>) user.get("morningKids");
                        String[] morningKids = morningKidsList.toArray(new String[morningKidsList.size()]);
                        List<String> eveningKidsList = (List<String>) user.get("eveningKids");
                        String[] eveningKids = eveningKidsList.toArray(new String[eveningKidsList.size()]);

                        Schedule s = new Schedule(id, morning, evening, date, morningKids, eveningKids);
                        scheduleList.add(s);
                    }
                } else {
                    //todo error message
                }
            }
        });
        return scheduleList;
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

    public void setSchedule(Schedule s) {
        List<String> m = Arrays.asList(s.getMorningKids());
        List<String> e = Arrays.asList(s.getEveningKids());
        Schedule schedule = new Schedule(s.getId(), s.getMorning(), s.getEvening(), s.getDate(), m, e);
        db.collection("Schedules")
                .document(Integer.toString(s.getId())).set(schedule)
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
