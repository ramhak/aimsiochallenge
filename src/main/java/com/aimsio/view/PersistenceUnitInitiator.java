package com.aimsio.view;

import com.aimsio.model.ProjectActivity;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.tool.schema.Action;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

public class PersistenceUnitInitiator {
    private final EntityManagerFactory entityManagerFactory;

    public PersistenceUnitInitiator() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(JPA_JDBC_DRIVER, "org.h2.Driver");
        map.put(JPA_JDBC_URL, "jdbc:h2:~/db/aimsio");
        map.put(DIALECT, H2Dialect.class);
        map.put(HBM2DDL_AUTO, Action.NONE);
        map.put(SHOW_SQL, true);
        map.put(QUERY_STARTUP_CHECKING, false);
        map.put(GENERATE_STATISTICS, false);
        map.put(USE_REFLECTION_OPTIMIZER, false);
        map.put(USE_SECOND_LEVEL_CACHE, false);
        map.put(USE_QUERY_CACHE, false);
        map.put(USE_STRUCTURED_CACHE, false);
        map.put(STATEMENT_BATCH_SIZE, 20);
        entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(archiverPersistenceUnitInfo(), map);

    }
    private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return "ApplicationPersistenceUnit";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<java.net.URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.singletonList(ProjectActivity.class.getName());
              //  return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
