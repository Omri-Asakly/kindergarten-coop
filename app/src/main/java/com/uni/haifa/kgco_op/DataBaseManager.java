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

    public void createParent(Parent p) {
        if (db != null) {
            db.createParent(p);
        }
    }

    public void createChild(Child c) {
        if (db != null) {
            db.createChild(c);
        }
    }

    public Parent readParent(int id) {
        Parent result = null;
        if (db != null) {
            result = db.readParents(id);
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

    public List<Child> getAllChildren() {
        List<Child> result = new ArrayList<Child>();
        if (db != null) {
            result = db.getAllChildren();
        }
        return result;
    }

    public void updateParent(Parent p) {
        if (db != null && p != null) {
            db.updateParent(p);
        }
    }

    public void updateChild(Child c) {
        if (db != null && c != null) {
            db.updateChild(c);
        }
    }

    public void deleteParent(Parent p) {
        if (db != null) {
            db.deleteParent(p);
        }
    }

    public void deleteChild(Child c) {
        if (db != null) {
            db.deleteChild(c);
        }
    }

    public List<Child> getFolderItems(Child c) {
        List<Child> result = new ArrayList<Child>();
        if (db != null && c != null) {
            result = db.getAllChildren(c);
        }
        return result;
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