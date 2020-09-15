package com.aimsio.view;

import com.aimsio.backend.IProjectActivityService;
import com.aimsio.model.ProjectActivity;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.AbstractHierarchicalDataProvider;
import com.vaadin.data.provider.HierarchicalQuery;
import com.vaadin.server.SerializablePredicate;

import java.util.Objects;
import java.util.stream.Stream;

public class ProjectActivityHierarchicalDataProvider extends AbstractHierarchicalDataProvider<ProjectActivity, SerializablePredicate<ProjectActivity>> {
    private final IProjectActivityService projectActivityService;
    private TreeData<ProjectActivity> treeData=new TreeData<>();

    public ProjectActivityHierarchicalDataProvider(IProjectActivityService projectActivityService) {
        this.projectActivityService = projectActivityService;
    }

    public TreeData<ProjectActivity> getTreeData() {
        return treeData;
    }

    @Override
    public int getChildCount(HierarchicalQuery<ProjectActivity, SerializablePredicate<ProjectActivity>> hierarchicalQuery) {
        if (Objects.isNull(hierarchicalQuery.getParent())) {
            return projectActivityService.getRootProjectActivity().size();
        }
        return projectActivityService.getChildrenCountOf(hierarchicalQuery.getParent());
    }

    @Override
    public Stream<ProjectActivity> fetchChildren(HierarchicalQuery<ProjectActivity, SerializablePredicate<ProjectActivity>> hierarchicalQuery) {
        if (Objects.isNull(hierarchicalQuery.getParent())) {
            return projectActivityService.getRootProjectActivity().stream();
        }
        return projectActivityService.getChildrenOf(hierarchicalQuery.getParent()).stream();
    }

    @Override
    public boolean hasChildren(ProjectActivity projectActivity) {
        return projectActivityService.hasAnyChildren(projectActivity);
    }

    @Override
    public boolean isInMemory() {
        return false;
    }
}
