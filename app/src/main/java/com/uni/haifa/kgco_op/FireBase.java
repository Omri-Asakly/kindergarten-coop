package com.uni.haifa.kgco_op;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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

    public boolean createParent(Parent p) {
        final boolean[] flag = {true};
        db.collection("People")
                .document(p.getEmail()).set(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0] =true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0]=false;

                    }
                });
        if(flag[0]){
            return true;
        }
        return false;
    }

    public boolean createChild(Child c) {
        final boolean[] flag = {true};
        db.collection("child")
                .document(Integer.toString(c.getId())).set(c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0]=true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                flag[0]=false;
            }
        });
        if(flag[0]){
            return true;
        }
        return false;
    }

    public boolean createSchedule(Schedule s) {
        final boolean[] flag = {true};
        List<String> m = s.getMorningKids();
        List<String> e = s.getEveningKids();
        Schedule schedule = new Schedule(s.getId(), s.getMorning(), s.getEvening(), s.getDate(), m, e);
        db.collection("Schedules")
                .document(Integer.toString(s.getId())).set(schedule)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0]=true;

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                flag[0]=false;
            }
        });
        if(flag[0]){
            return true;
        }
        return false;
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

        Task<QuerySnapshot> children = db.collection("child").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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


    public boolean setParent(Parent p) {
        final boolean[] flag = {true};
        db.collection("People")
                .document(p.getEmail()).set(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0]=true;

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0]=false;
                    }
                });
        if(flag[0]){
            return true;
        }
        return false;
    }

    public void setChild(Child c) {
        db.collection("child")
                .document(Integer.toString(c.getId())).set(c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

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
        List<String> m = s.getMorningKids();
        List<String> e = s.getEveningKids();
        Schedule schedule = new Schedule(s.getId(), s.getMorning(), s.getEvening(), s.getDate(), m, e);
        db.collection("Schedules")
                .document(Integer.toString(s.getId())).set(schedule)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //TODO
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO
            }
        });
    }

    public boolean deleteParent(Parent p) {
        final boolean[] flag = {true};
        db.collection("Parents")
                .document(p.getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0]=true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0]=false;
                    }
                });
        if(flag[0]){
            return true;
        }
        return false;
    }

    public boolean deleteChild(Child c) {
        final boolean[] flag = {true};
        db.collection("child")
                .document(Integer.toString(c.getId()))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0]=true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0]=false;
                    }
                });
        if(flag[0]){
            return true;
        }
        return false;
    }

    public boolean deleteSchedule(Schedule s) {
        final boolean[] flag = {true};
        db.collection("Schedule")
                .document(Integer.toString(s.getId()))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0]=true;

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0]=false;

                    }
                });
        if(flag[0]){
            return true;
        }
        return false;
    }


}
