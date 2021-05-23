package com.uni.haifa.kgco_op;

public class Child {
    private int id;
    private int parentId;
    private String name;

    public Child(int id, int parentId, String name){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Child() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                '}';
    }
}
