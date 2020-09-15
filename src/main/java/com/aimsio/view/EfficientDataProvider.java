package com.aimsio.view;

import com.aimsio.backend.IProjectActivityService;
import com.aimsio.model.ProjectActivity;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.HierarchicalQuery;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.server.SerializablePredicate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EfficientDataProvider extends TreeDataProvider<ProjectActivity> {
    private final IProjectActivityService projectActivityService;

    public EfficientDataProvider(TreeData<ProjectActivity> treeData, IProjectActivityService projectActivityService) {
        super(treeData);
        this.projectActivityService = projectActivityService;
    }

    @Override
    public Stream<ProjectActivity> fetchChildren(HierarchicalQuery<ProjectActivity, SerializablePredicate<ProjectActivity>> query) {
        List<ProjectActivity> children = super.fetchChildren(query).collect(Collectors.toList());
        if (children.isEmpty()) {
            if (Objects.isNull(query.getParent())) {
                children = projectActivityService.getRootProjectActivity();
                if (!children.isEmpty()) {
                    getTreeData().addRootItems(children);
                }
            } else {
                children = projectActivityService.getChildrenOf(query.getParent());
                if (!children.isEmpty()) {
                    getTreeData().addItems(query.getParent(), children);
                }
            }

        }
        return children.stream();
    }

    @Override
    public boolean hasChildren(ProjectActivity item) {
        boolean hasChildren = super.hasChildren(item);
        if (!hasChildren) {
            return projectActivityService.hasAnyChildren(item);
        }
        return true;
    }

    @Override
    public int getChildCount(HierarchicalQuery<ProjectActivity, SerializablePredicate<ProjectActivity>> query) {
        int childCount = super.getChildCount(query);
        if(childCount==0){
            if (Objects.isNull(query.getParent())) {
                return projectActivityService.getRootProjectActivity().size();
            }
            return projectActivityService.getChildrenCountOf(query.getParent());
        }
        return childCount;
    }
}
