package com.aimsio.view;

import com.aimsio.backend.IProjectActivityService;
import com.aimsio.model.ProjectActivity;
import com.vaadin.data.TreeData;

import java.util.ArrayList;
import java.util.List;

public class ProjectLayoutPresenter implements ProjectLayout.Listener {
    private final ProjectLayout layout;
    private final IProjectActivityService projectActivityService;
    private final EfficientDataProvider efficientDataProvider;

    public ProjectLayoutPresenter(ProjectLayout layout, IProjectActivityService projectActivityService, EfficientDataProvider efficientDataProvider) {
        this.layout = layout;
        this.projectActivityService = projectActivityService;
        this.efficientDataProvider = efficientDataProvider;
        this.layout.setListener(this);
        this.layout.getTree().setDataProvider(this.efficientDataProvider);
    }

    @Override
    public void addActivity(ProjectActivity parentProjectActivity, String title) {
            ProjectActivity projectActivity = new ProjectActivity(parentProjectActivity, title);
            projectActivityService.addProjectActivity(projectActivity);
            efficientDataProvider.getTreeData().addItem(parentProjectActivity, projectActivity);
            efficientDataProvider.refreshAll();
    }

    @Override
    public void removeActivity(ProjectActivity projectActivity) {
            projectActivityService.removeProjectActivity(projectActivity);
            efficientDataProvider.getTreeData().removeItem(projectActivity);
            efficientDataProvider.refreshAll();
    }

    @Override
    public String exportProjectActivityData() {
        TreeData<ProjectActivity> treeData = efficientDataProvider.getTreeData();
        if (treeData.getRootItems().isEmpty()) {
            return "";
        }
        ArrayList<String> accumulator = new ArrayList<>();
        for (ProjectActivity item : treeData.getRootItems()) {
            traverseAndIndent(item, "", accumulator, treeData);
        }
        return String.join("\n", accumulator);
    }

    private void traverseAndIndent(ProjectActivity item, String indent, List<String> acc, TreeData<ProjectActivity> treeData) {
        acc.add(indent + item.getTitle());
        List<ProjectActivity> children = treeData.getChildren(item);
        if (!children.isEmpty()) {
            for (ProjectActivity child : children) {
                traverseAndIndent(child, indent + "\t", acc, treeData);
            }
        }
    }
}
