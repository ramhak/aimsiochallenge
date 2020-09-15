package com.aimsio.backend;

import com.aimsio.model.ProjectActivity;

import java.util.List;

public class ProjectActivityService implements IProjectActivityService {
    private final IJpaExecutor jpaExecutor;

    public ProjectActivityService(IJpaExecutor jpaExecutor) {
        this.jpaExecutor = jpaExecutor;
    }

    @Override
    public List<ProjectActivity> getRootProjectActivity() {
        return jpaExecutor.execute(em -> em.createQuery("select pa from ProjectActivity  pa where pa.parent is null", ProjectActivity.class)
                .getResultList());
    }

    @Override
    public int getChildrenCountOf(ProjectActivity projectActivity) {
        return jpaExecutor.execute(em -> em.createQuery("select count(pa) from ProjectActivity  pa where pa.parent=:parent", Long.class)
                .setParameter("parent", projectActivity)
                .getSingleResult().intValue());
    }

    @Override
    public boolean hasAnyChildren(ProjectActivity projectActivity) {
        return getChildrenCountOf(projectActivity) > 0;
    }

    @Override
    public List<ProjectActivity> getChildrenOf(ProjectActivity projectActivity) {
        return jpaExecutor.execute(em -> em.createQuery("select pa from ProjectActivity  pa where pa.parent=:parent", ProjectActivity.class)
                .setParameter("parent", projectActivity)
                .getResultList());
    }

    @Override
    public void addProjectActivity(ProjectActivity projectActivity) {
        jpaExecutor.execute(em -> {
            em.persist(projectActivity);
            return null;
        });
    }

    @Override
    public void removeProjectActivity(ProjectActivity projectActivity) {
        jpaExecutor.execute(em -> {
            em.createQuery("delete from ProjectActivity  where id=:id")
                    .setParameter("id",projectActivity.getId())
                    .executeUpdate();
            return null;
        });
    }
}
