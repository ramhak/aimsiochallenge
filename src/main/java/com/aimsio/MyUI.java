package com.aimsio;

import javax.persistence.EntityManagerFactory;
import javax.servlet.annotation.WebServlet;

import com.aimsio.backend.JpaExecutor;
import com.aimsio.backend.PersistenceUnitInitiator;
import com.aimsio.backend.ProjectActivityService;
import com.aimsio.view.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.TreeData;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final ProjectLayout layout = new ProjectLayout();
        EntityManagerFactory entityManagerFactory = new PersistenceUnitInitiator().getEntityManagerFactory();
        ProjectActivityService projectActivityService = new ProjectActivityService(
                new JpaExecutor(entityManagerFactory)
        );
        new ProjectLayoutPresenter(
                layout, projectActivityService, new EfficientDataProvider(new TreeData<>(),projectActivityService));
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
