package com.app.courseplan.model;

public class Course {
    private int id;
    private String courseName;
    private String startDate;
    private String endDate;
    private String description;
    private String courseUrl;

    public Course() {
    }

    public Course(int id, String courseName, String startDate, String endDate,
            String description) {
        this.id = id;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.courseUrl = courseUrl;
    }

    //Another way to create a course without knowing the id it should be
    public Course(String courseName, String startDate, String endDate,
                  String description, String courseUrl) {
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.courseUrl = courseUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }
}
