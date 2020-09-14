package com.aimsio.view;

import com.aimsio.model.ProjectActivity;
import com.vaadin.ui.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ProjectLayout extends VerticalLayout {

    private final Tree<ProjectActivity> tree;
    private Listener listener;

    public ProjectLayout() {
        super();
        final TextField name = new TextField();
        name.setCaption("Project Activity:");

        HorizontalLayout buttonsLayout = new HorizontalLayout();

        tree = new Tree<>();
        tree.setCaption("Project Activities");
        tree.setWidth("300px");
        tree.setHeight("300px");
        tree.addStyleName("with-border");

        Button addButton = new Button("Add");
        addButton.addClickListener(e -> {
                listener.addActivity(tree.asSingleSelect().getOptionalValue().orElse(null), name.getValue());
                name.setValue("");
        });

        Button deleteButton = new Button("Delete");
        deleteButton.addClickListener(e -> listener
                .removeActivity(tree.asSingleSelect().getOptionalValue().orElse(null)));

        Button exportButton = new Button("Export");
        OnDemandFileDownloader exportFileDownloader = new OnDemandFileDownloader(new OnDemandFileDownloader.OnDemandStreamResource() {
            @Override
            public String getFilename() {
                return "test.txt";
            }

            @Override
            public InputStream getStream() {
                return new ByteArrayInputStream(listener.exportData().getBytes());
            }
        });
        exportFileDownloader.extend(exportButton);

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        buttonsLayout.addComponents(addButton, deleteButton, exportButton);

        this.addComponents(name, buttonsLayout, tree);


    }

    public Tree<ProjectActivity> getTree() {
        return tree;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void addActivity(ProjectActivity projectActivity, String title);

        void removeActivity(ProjectActivity projectActivity);

        String exportData();

    }
}
