package com.uni.haifa.kgco_op;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "KindergartenDB";

    //parent table
    private static final String TABLE_PARENT_NAME = "parents";
    private static final String PARENT_COLUMN_ID = "id";
    private static final String PARENT_COLUMN_USERNAME = "username";
    private static final String PARENT_COLUMN_EMAIL = "email";
    private static final String PARENT_COLUMN_PASSWORD = "password";
    private static final String PARENT_COLUMN_DATE = "date";
    private static final String[] TABLE_PARENT_COLUMNS = {PARENT_COLUMN_ID, PARENT_COLUMN_USERNAME, PARENT_COLUMN_EMAIL, PARENT_COLUMN_PASSWORD, PARENT_COLUMN_DATE};


    //child table
    private static final String TABLE_CHILD_NAME = "children";
    private static final String CHILD_COLUMN_ID = "id";
    private static final String CHILD_COLUMN_PARENT_ID = "parentID";
    private static final String CHILD_COLUMN_NAME = "name";
    private static final String[] TABLE_CHILD_COLUMNS = {CHILD_COLUMN_ID, CHILD_COLUMN_PARENT_ID, CHILD_COLUMN_NAME};

    private SQLiteDatabase db = null;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_PARENT_TABLE = "create table if not exists " + TABLE_PARENT_NAME + " ( "
                    + PARENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PARENT_COLUMN_USERNAME + " TEXT, "
                    + PARENT_COLUMN_EMAIL + " TEXT, "
                    + PARENT_COLUMN_PASSWORD + " TEXT, "
                    + PARENT_COLUMN_DATE + " DATE)";
            db.execSQL(CREATE_PARENT_TABLE);

            String CREATE_CHILD_TABLE = "create table if not exists " + TABLE_CHILD_NAME + " ( "
                    + CHILD_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CHILD_COLUMN_PARENT_ID + " INTEGER,"
                    + CHILD_COLUMN_NAME + " TEXT)";
            db.execSQL(CREATE_CHILD_TABLE);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // drop item table if already exists
            //db.execSQL("DROP TABLE IF EXISTS parent");
            //db.execSQL("DROP TABLE IF EXISTS child");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        //onCreate(db);
    }

    public void createParent(Parent parent) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(PARENT_COLUMN_USERNAME, parent.getUserName());
            values.put(PARENT_COLUMN_EMAIL, parent.getEmail());
            values.put(PARENT_COLUMN_PASSWORD, parent.getPassword());
            //time converted to INTEGER
            values.put(PARENT_COLUMN_DATE, parent.getLicenseDate().getTime());
            // insert item
            db.insert(TABLE_PARENT_NAME, null, values);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void createChild(Child c) {
        try {
            ContentValues values = new ContentValues();
            values.put(CHILD_COLUMN_NAME, c.getName());
            values.put(CHILD_COLUMN_PARENT_ID, c.getParentId());
            db.insert(TABLE_CHILD_NAME, null, values);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Parent readParents(int id) {
        Parent parent = null;
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_PARENT_NAME, // a. table
                            TABLE_PARENT_COLUMNS, PARENT_COLUMN_ID + " = ?",
                            new String[]{String.valueOf(id)}, null, null,
                            null, null);
            if (cursor != null)
                cursor.moveToFirst();
            parent = new Parent();
            parent.setId(cursor.getInt(0));
            parent.setUserName(cursor.getString(1));
            parent.setEmail(cursor.getString(2));
            parent.setPassword(cursor.getString(3));
            SimpleDateFormat originalFormat = new SimpleDateFormat("ddMMyyyy");
            Date date = originalFormat.parse(cursor.getString(4));
            parent.setLicenseDate(date);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return parent;
    }

    public Child readChildren(int id){
        Child child = null;
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_CHILD_NAME, // a. table
                            TABLE_CHILD_COLUMNS, CHILD_COLUMN_ID + " = ?",
                            new String[]{String.valueOf(id)}, null, null,
                            null, null);
            if (cursor != null)
                cursor.moveToFirst();
            child = new Child();
            child.setId(cursor.getInt(0));
            child.setParentId(cursor.getInt(1));
            child.setName(cursor.getString(2));
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return child;
    }

    public List<Parent> getAllParents() {
        List<Parent> result = new ArrayList<Parent>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PARENT_NAME, TABLE_PARENT_COLUMNS, null, null,
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Parent item = cursorToParent(cursor);
                result.add(item);
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public List<Child> getAllChildren() {
        List<Child> result = new ArrayList<Child>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_CHILD_NAME, TABLE_CHILD_COLUMNS, null, null,
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Child item = cursorToChild(cursor);
                result.add(item);
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    private Parent cursorToParent(Cursor cursor) {
        Parent result = new Parent();
        try {
            result.setId(cursor.getInt(0));
            result.setUserName(cursor.getString(1));
            result.setEmail(cursor.getString(2));
            result.setPassword(cursor.getString(3));
            SimpleDateFormat originalFormat = new SimpleDateFormat("ddMMyyyy");
            Date date = originalFormat.parse(cursor.getString(4));
            result.setLicenseDate(date);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    private Child cursorToChild(Cursor cursor) {
        Child result = new Child();
        try {
            result.setId(cursor.getInt(0));
            result.setParentId(cursor.getInt(1));
            result.setName(cursor.getString(2));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    public int updateParent(Parent parent) {
        int count = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(PARENT_COLUMN_USERNAME, parent.getUserName());
            values.put(PARENT_COLUMN_EMAIL, parent.getEmail());
            values.put(PARENT_COLUMN_PASSWORD, parent.getPassword());
            //time converted to INTEGER
            values.put(PARENT_COLUMN_DATE, parent.getLicenseDate().getTime());
            // update
            count = db.update(TABLE_PARENT_NAME, values, PARENT_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(parent.getId())});
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return count;
    }

    public int updateChild(Child child) {
        int count = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(CHILD_COLUMN_PARENT_ID, child.getParentId());
            values.put(CHILD_COLUMN_NAME, child.getName());
            // update
            count = db.update(TABLE_PARENT_NAME, values, PARENT_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(child.getId())});
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return count;
    }

    public void deleteParent(Parent parent) {
        try {
            db.delete(TABLE_PARENT_NAME, PARENT_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(parent.getId())});
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void deleteChild(Child child) {
        try {
            db.delete(TABLE_CHILD_NAME, CHILD_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(child.getId())});
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private boolean isTableExist(String name, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + name + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public List<Parent> getAllParents(Parent folder) {
        List<Parent> result = new ArrayList<Parent>();
        Cursor cursor = null;
        try {
            int floderId = folder.getId();
            cursor = db.query(TABLE_PARENT_NAME, TABLE_PARENT_COLUMNS, PARENT_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(floderId)}, null, null,
                    null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Parent item = cursorToParent(cursor);
                    result.add(item);
                    cursor.moveToNext();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        return result;
    }

    public List<Child> getAllChildren(Child child) {
        List<Child> result = new ArrayList<Child>();
        Cursor cursor = null;
        try {
            int childId = child.getId();
            cursor = db.query(TABLE_CHILD_NAME, TABLE_CHILD_COLUMNS, CHILD_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(childId)}, null, null,
                    null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Child item = cursorToChild(cursor);
                    result.add(item);
                    cursor.moveToNext();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        return result;
    }

    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
