package com.aimsio.model;

import javax.persistence.*;
@Entity
public class ProjectActivity {
    public ProjectActivity() {
    }

    String title;
    @Transient
    float hours;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private ProjectActivity parent;

    public ProjectActivity(ProjectActivity parentNode, String title) {
        this.parent = parentNode;
        this.title = title;
    }

    public ProjectActivity getParentNode() {
        return parent;
    }

    public void setParentNode(ProjectActivity parentNode) {
        this.parent = parentNode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return title;
    }

    public Integer getId() {
        return id;
    }
}

