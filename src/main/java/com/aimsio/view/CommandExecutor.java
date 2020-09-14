package com.aimsio.view;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;

public class CommandExecutor implements ICommandExecutor {

    private final EntityManagerFactory entityManagerFactory;

    public CommandExecutor(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void execute(Consumer<EntityManager> command) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            command.accept(em);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }


    }
}
