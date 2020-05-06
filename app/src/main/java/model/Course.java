package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String myCourseID;
    private String myCourseShortDesc;
    private String myCourseLongDesc;
    private String myCoursePrereqs;

    public static final String ID = "id";
    public static final String SHORT_DESC = "shortdesc";
    public static final String LONG_DESC = "longdesc";
    public static final String PRE_REQS = "prereqs";

    public Course(String myCourseID, String myCourseShortDesc, String myCourseLongDesc, String myCoursePrereqs) {
        this.myCourseID = myCourseID;
        this.myCourseShortDesc = myCourseShortDesc;
        this.myCourseLongDesc = myCourseLongDesc;
        this.myCoursePrereqs = myCoursePrereqs;
    }

    public String getCourseID() {
        return this.myCourseID;
    }

    public void setCourseID(String newCourseID) {
        this.myCourseID = newCourseID;
    }

    public String getCourseShortDesc() {
        return this.myCourseShortDesc;
    }

    public void setCourseShortDesc(String newCourseShortDesc) {
        this.myCourseShortDesc = newCourseShortDesc;
    }

    public String getCourseLongDesc() {
        return this.myCourseLongDesc;
    }

    public void setMyCourseLongDesc(String newCourseLongDesc) {
        this.myCourseLongDesc = newCourseLongDesc;
    }

    public String getCoursePrereqs() {
        return this.myCoursePrereqs;
    }

    public void setCoursePrereqs(String newCoursePrereqs) {
        this.myCoursePrereqs = newCoursePrereqs;
    }

    public static List<Course> parseCourseJson(String courseJson) throws JSONException {
        List<Course> courseList = new ArrayList<Course>();
        if (courseJson != null) {
            JSONArray arr = new JSONArray(courseJson);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Course course = new Course(obj.getString(Course.ID), obj.getString(Course.SHORT_DESC), obj.getString(Course.LONG_DESC), obj.getString(Course.PRE_REQS));
                courseList.add(course);
            }
        }
        return courseList;
    }

}
