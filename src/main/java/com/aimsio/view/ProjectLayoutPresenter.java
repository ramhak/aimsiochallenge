package com.aimsio.view;

import com.aimsio.model.ProjectActivity;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectLayoutPresenter implements ProjectLayout.Listener {
    private final ProjectLayout layout;
    private final TreeDataProvider<ProjectActivity> inMemoryDataProvider;


    public ProjectLayoutPresenter(ProjectLayout layout) {
        this.layout = layout;
        this.layout.setListener(this);
        inMemoryDataProvider = new TreeDataProvider<>(new TreeData<>());
        this.layout.getTree().setDataProvider(inMemoryDataProvider);
    }


    @Override
    public void addActivity(ProjectActivity projectActivity, String title) {
        inMemoryDataProvider.getTreeData().addItem(projectActivity, new ProjectActivity(projectActivity, title));
        inMemoryDataProvider.refreshAll();
    }

    @Override
    public void removeActivity(ProjectActivity projectActivity) {
        inMemoryDataProvider.getTreeData().removeItem(projectActivity);
        inMemoryDataProvider.refreshAll();
    }

    @Override
    public String exportData() {
        TreeData<ProjectActivity> treeData = inMemoryDataProvider.getTreeData();
        if (treeData.getRootItems().isEmpty()) {
            return "";
        }
        ArrayList<String> acc = new ArrayList<>();
        for (ProjectActivity item : treeData.getRootItems()) {
            traverseAndIndent(item, "", acc, treeData);
        }
        return String.join("\n", acc);
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
