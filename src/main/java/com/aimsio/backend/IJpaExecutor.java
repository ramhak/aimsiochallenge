package com.aimsio.backend;

import javax.persistence.EntityManager;
import java.util.function.Function;

public interface IJpaExecutor {
    <T> T execute(Function<EntityManager, T> query);
}
