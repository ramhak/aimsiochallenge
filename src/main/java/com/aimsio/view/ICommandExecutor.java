package com.aimsio.view;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import java.util.function.Consumer;

import static org.hibernate.cfg.AvailableSettings.*;

public interface ICommandExecutor {

    void execute(Consumer<EntityManager> command);
}
