// JobTitle.java (Updated to match DB schema)
package com.example.sql_dbms_ui.Models;

import jakarta.persistence.*;

@Entity
public class JobTitle {

    @Id
    @Column(name = "job_title_id")
    private int jobTitleId;

    @Column(name = "job_title")
    private String title;

    // Getters and setters
    public int getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(int jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}