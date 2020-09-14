package com.aimsio.view;

import javax.persistence.EntityManager;
import java.util.function.Function;

public interface IQueryExecutor {
    <T> T query(Function<EntityManager, T> query);
}
