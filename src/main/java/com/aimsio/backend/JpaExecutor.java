package com.aimsio.backend;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Function;

public class JpaExecutor implements IJpaExecutor {
    private final EntityManagerFactory emf;

    public JpaExecutor(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public <T> T execute(Function<EntityManager, T> query){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T result = query.apply(em);
            em.getTransaction().commit();
            return result;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
