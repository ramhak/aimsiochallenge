package com.aimsio.view;

import com.aimsio.model.ProjectActivity;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;

import java.io.ByteArrayInputStream;
import java.util.Set;
import java.util.UUID;

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
            if (listener != null) {
                Set<ProjectActivity> selectedItems = tree.getSelectedItems();
                listener.addActivity(selectedItems.size() == 0 ? null : selectedItems.iterator().next(), name.getValue());
                name.setValue("");
            }
        });


        Button deleteButton = new Button("Delete");
        deleteButton.addClickListener(e -> {
            if (listener != null) {
                Set<ProjectActivity> selectedItems = tree.getSelectedItems();
                listener.removeActivity(selectedItems.size() == 0 ? null : selectedItems.iterator().next());
            }
        });


        Button exportButton = new Button("Export");
        FileDownloader exportFileDownloader = new FileDownloader(
                new StreamResource((StreamResource.StreamSource) () -> null, "")
        );
        exportButton.addClickListener(e -> {
            StreamResource aaa = new StreamResource((StreamResource.StreamSource) () -> new ByteArrayInputStream(UUID.randomUUID().toString().getBytes()), "test.txt");
            exportFileDownloader.setFileDownloadResource(aaa);

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
    }
}
