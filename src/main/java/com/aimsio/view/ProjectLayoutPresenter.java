package com.aimsio.view;

import com.aimsio.model.ProjectActivity;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;

import java.util.ArrayList;
import java.util.List;

public class ProjectLayoutPresenter implements ProjectLayout.Listener {
    private final ProjectLayout layout;
    private final TreeDataProvider<ProjectActivity> inMemoryDataProvider;
    private final ICommandExecutor commandExecutor;


    public ProjectLayoutPresenter(ProjectLayout layout, ICommandExecutor commandExecutor) {
        this.layout = layout;
        this.commandExecutor = commandExecutor;
        this.layout.setListener(this);
        inMemoryDataProvider = new TreeDataProvider<>(new TreeData<>());
        this.layout.getTree().setDataProvider(inMemoryDataProvider);
    }


    @Override
    public void addActivity(ProjectActivity parentProjectActivity, String title) {
        commandExecutor.execute((em)->{
            ProjectActivity projectActivity = new ProjectActivity(parentProjectActivity, title);
            em.persist(projectActivity);
            inMemoryDataProvider.getTreeData().addItem(parentProjectActivity, projectActivity);
            inMemoryDataProvider.refreshAll();
        });
    }

    @Override
    public void removeActivity(ProjectActivity projectActivity) {
        commandExecutor.execute(em->{
            em.remove(projectActivity);
            inMemoryDataProvider.getTreeData().removeItem(projectActivity);
            inMemoryDataProvider.refreshAll();
        });

    }

    @Override
    public String exportData() {
        TreeData<ProjectActivity> treeData = inMemoryDataProvider.getTreeData();
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
