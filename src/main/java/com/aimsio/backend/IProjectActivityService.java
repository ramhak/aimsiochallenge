package com.aimsio.backend;

import com.aimsio.model.ProjectActivity;

import java.util.List;
import java.util.stream.Stream;

public interface IProjectActivityService {
    List<ProjectActivity> getRootProjectActivity();
    int getChildrenCountOf(ProjectActivity projectActivity);
    boolean hasAnyChildren(ProjectActivity projectActivity);

    List<ProjectActivity> getChildrenOf(ProjectActivity projectActivity);
    void addProjectActivity(ProjectActivity projectActivity);
    void removeProjectActivity(ProjectActivity projectActivity);
}
