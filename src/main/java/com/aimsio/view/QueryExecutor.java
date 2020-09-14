package com.aimsio.view;

import javax.persistence.EntityManager;
import java.util.function.BiFunction;
import java.util.function.Function;

public class QueryExecutor implements IQueryExecutor {
    @Override
    public <T> T query(Function<EntityManager, T> query){

        return (T)null;
    }
}
