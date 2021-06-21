package com.uni.haifa.kgco_op;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private static DataBaseManager instance = null;
    private Context context = null;
    private DataBase db = null;
    private Parent selectedParent = null;
    private Child selectedChild = null;
    private Schedule selectedSchedule = null;


    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    public static void releaseInstance() {
        if (instance != null) {
            instance.clean();
            instance = null;
        }
    }

    private void clean() {

    }


    public Context getContext() {
        return context;

    }

    public void openDataBase(Context context) {
        this.context = context;
        if (context != null) {
            db = new DataBase(context);
            db.open();
        }
    }
    public void closeDataBase() {
        if(db!=null){
            db.close();
        }
    }

    public boolean createParent(Parent p) {
        if (db != null) {
            db.createParent(p);
            p.setId(db.getAllParents().size()+1);
        }
        if(FireBase.getInstance().createParent(p))
            return true;
        return false;
    }

    public boolean createChild(Child c) {
        if (db != null) {
            c.setId(db.getAllChildren().size()+1);
            db.createChild(c);
        }
        if(FireBase.getInstance().createChild(c))
            return true;
        return false;
    }

    public boolean createSchedule(Schedule s) {
        if (db != null) {
            s.setId(db.getAllSchedules().size()+1);
            db.createSchedule(s);
        }
        if(FireBase.getInstance().createSchedule(s))
            return true;
        return false;
    }

    public Parent readParent(int id) {
        Parent result = null;
        if (db != null) {
            result = db.readParents(id);
        }
        return result;
    }

    public Schedule readSchedule(int id) {
        Schedule result = null;
        if (db != null) {
            result = db.readSchedules(id);
        }
        return result;
    }

    public Child readChild(int id) {
        Child result = null;
        if (db != null) {
            result = db.readChildren(id);
        }
        return result;
    }

    public List<Parent> getAllParents() {
        List<Parent> result = new ArrayList<Parent>();
        if (db != null) {
            result = db.getAllParents();
        }
        return result;
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> result = new ArrayList<Schedule>();
        if (db != null) {
            result = db.getAllSchedules();
        }
        return result;
    }

    public List<Child> getAllChildren() {
        List<Child> result = new ArrayList<Child>();
        if (db != null) {
            result = db.getAllChildren();
        }
        return result;
    }

    public boolean updateParent(Parent p) {
        if (db != null && p != null) {
            db.updateParent(p);
        }
        if(FireBase.getInstance().setParent(p))
            return true;
        return false;
    }

    public void updateSchedule(Schedule s) {
        if (db != null && s != null) {
            db.updateSchedule(s);
        }
        FireBase.getInstance().setSchedule(s);
    }

    public void updateChild(Child c) {
        if (db != null && c != null) {
            db.updateChild(c);
        }
        FireBase.getInstance().setChild(c);
    }

    public boolean deleteParent(Parent p) {
        if (db != null) {
            db.deleteParent(p);
        }
        if(FireBase.getInstance().deleteParent(p))
            return true;
        return false;
    }

    public boolean deleteSchedule(Schedule s) {
        if (db != null) {
            db.deleteSchedule(s);
        }
        if(FireBase.getInstance().deleteSchedule(s))
            return true;
        return false;
    }

    public boolean deleteChild(Child c) {
        if (db != null) {
            db.deleteChild(c);
        }
        if(FireBase.getInstance().deleteChild(c))
            return true;
        return false;
    }

    public List<Child> getAllChildren(Child c) {
        List<Child> result = new ArrayList<Child>();
        if (db != null && c != null) {
            result = db.getAllChildren(c);
        }
        return result;
    }

    public void removeAllParents() {
        if(db!=null){
            db.deleteAllParents();
        }
    }

    public void removeAllChildren() {
        if(db!=null){
            db.deleteAllChildren();
        }
    }

    public void removeAllSchedules() {
        if(db!=null){
            db.deleteAllSchedules();
        }
    }

    public Schedule getSelectedSchedule() {
        return selectedSchedule;
    }

    public void setSelectedSchedule(Schedule selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    public Parent getSelectedParent() {
        return selectedParent;
    }

    public void setSelectedChild(Child selectedChild) {
        this.selectedChild = selectedChild;
    }

    public Child getSelectedChild() {
        return selectedChild;
    }

    public void setSelectedParent(Parent selectedParent) {
        this.selectedParent = selectedParent;
    }

    public void deleteSelectedChild(){
        deleteChild(selectedChild);
    }
}
