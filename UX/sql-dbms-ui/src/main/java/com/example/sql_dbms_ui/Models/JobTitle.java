package com.example.sql_dbms_ui.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class JobTitle {

    @Id
    @Column(name = "job_title_id")
    private Long jobTitleId;

    @Column(name = "job_title")
    private String title;

    // Getters and setters
    public long getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(long jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}