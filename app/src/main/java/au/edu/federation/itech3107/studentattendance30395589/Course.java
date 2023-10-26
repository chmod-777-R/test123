package au.edu.federation.itech3107.studentattendance30395589;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Course {
    private String name;
    private String startDate;

    public Course(String name, String startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    /**
     * parash
     * @return
     */
    public List<String> getTimetable() {
        ArrayList<String> strings = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        try {
            Date parse = dateFormat.parse(startDate);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            strings.add(startDate);
            for (int i = 0; i < 11; i++) {
                instance.add(Calendar.DAY_OF_WEEK, 7);
                strings.add(dateFormat.format(instance.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "getTimetable: " + strings);
        return strings;
    }
}
