package com.uni.haifa.kgco_op;

import java.util.ArrayList;

public class Global{
    private static Global instance = null;

    public ArrayList<Parent> parents;
    public ArrayList<Child> children;
    protected Global(){}

    public static synchronized Global getInstance(){
        if(null == instance){
            instance = new Global();
        }
        return instance;
    }

    public void setChildren(ArrayList<Child> children){ this.children = children; }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public ArrayList<Parent> getParents() {
        return parents;
    }

    public void setParents(ArrayList<Parent> parents) {
        this.parents = parents;
    }
}
