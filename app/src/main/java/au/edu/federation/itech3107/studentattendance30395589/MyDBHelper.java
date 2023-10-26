package au.edu.federation.itech3107.studentattendance30395589;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(@Nullable Context context) {
        super(context, "database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists user(username text primary key,password text)");
        db.execSQL("create table if not exists course(name text primary key,startDate text)");
        db.execSQL("create table if not exists student(id text primary key,name text,courseName text)");
        db.execSQL("create table if not exists attendance(id integer primary key autoincrement,studentName text,courseName text,date text)");


        db.execSQL("insert into course(name,startDate) values(?,?)", new Object[]{"Android", "2023/10/23"});
        db.execSQL("insert into course(name,startDate) values(?,?)", new Object[]{"Web", "2023/10/23"});
        db.execSQL("insert into course(name,startDate) values(?,?)", new Object[]{"Java", "2023/10/23"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addCourse(String name, String startDate) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("startDate", startDate);
        return writableDatabase.insert("course", null, values) > 0;
    }

    public boolean getAttendance(String studentName, String courseName, String date) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.rawQuery("select * from attendance where studentName = ? and courseName =? and date = ?", new String[]{studentName, courseName, date}).getCount() > 0;
    }

    public void addAttendance(String studentName, String courseName, String date, boolean attendance) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (getAttendance(studentName, courseName, date)) {
            writableDatabase.delete("attendance", "studentName = ? and courseName =? and date = ?", new String[]{studentName, courseName, date});
        } else {
            ContentValues values = new ContentValues();
            values.put("studentName", studentName);
            values.put("courseName", courseName);
            values.put("date", date);
            writableDatabase.insert("attendance", null, values);
        }
    }


    @SuppressLint("Range")
    public List<Student> getStudentList(String courseName) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select  *from student where courseName = ?", new String[]{courseName});
        ArrayList<Student> strings = new ArrayList<>();
        while (cursor.moveToNext()) {
            strings.add(new Student(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("id"))));
        }
        return strings;
    }

    public void deleteCourse(String name) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("course", "name = ?", new String[]{name});
    }


    @SuppressLint("Range")
    public List<Course> getCourse() {
        ArrayList<Course> courses = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from course", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String startDate = cursor.getString(cursor.getColumnIndex("startDate"));
            courses.add(new Course(name, startDate));
        }
        return courses;
    }

    @SuppressLint("Range")
    public Course getCourse(String courseName) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from course where  name = ?", new String[]{courseName});
        if (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String startDate = cursor.getString(cursor.getColumnIndex("startDate"));
            return new Course(name, startDate);
        }
        return null;
    }

    public boolean login(String username, String password) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from user where username = ? and password = ?", new String[]{username, password});
        if (cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean register(String username, String password) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from user where username = ?", new String[]{username});
        if (cursor.moveToNext()) {
            return false;
        } else {
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            return writableDatabase.insert("user", null, values) > 0;
        }
    }

    public boolean addStudent(String studentName, String id, String courseName) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", studentName);
        values.put("courseName", courseName);
        return writableDatabase.insert("student", null, values) > 0;
    }
}
