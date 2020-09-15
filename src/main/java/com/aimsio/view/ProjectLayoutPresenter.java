package com.aimsio.view;

import com.aimsio.backend.IProjectActivityService;
import com.aimsio.model.ProjectActivity;
import com.vaadin.data.TreeData;

import java.util.ArrayList;
import java.util.List;

public class ProjectLayoutPresenter implements ProjectLayout.Listener {
    private final ProjectLayout layout;
    private final ProjectActivityHierarchicalDataProvider dataProvider;
    private final IProjectActivityService projectActivityService;


    public ProjectLayoutPresenter(ProjectLayout layout, ProjectActivityHierarchicalDataProvider dataProvider, IProjectActivityService projectActivityService) {
        this.layout = layout;
        this.projectActivityService = projectActivityService;
        this.layout.setListener(this);
        this.dataProvider=dataProvider;
        this.layout.getTree().setDataProvider(this.dataProvider);
    }


    @Override
    public void addActivity(ProjectActivity parentProjectActivity, String title) {
            ProjectActivity projectActivity = new ProjectActivity(parentProjectActivity, title);
            projectActivityService.addProjectActivity(projectActivity);
//            dataProvider.getTreeData().addItem(parentProjectActivity, projectActivity);
            dataProvider.refreshItem(parentProjectActivity);
    }

    @Override
    public void removeActivity(ProjectActivity projectActivity) {
            projectActivityService.removeProjectActivity(projectActivity);
//            dataProvider.getTreeData().removeItem(projectActivity);
            dataProvider.refreshAll();
    }

    @Override
    public String exportProjectActivityData() {
     //   TreeData<ProjectActivity> treeData = dataProvider.getTreeData();
        TreeData<ProjectActivity> treeData = null;
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
