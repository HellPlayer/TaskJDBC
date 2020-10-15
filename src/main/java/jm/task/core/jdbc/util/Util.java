package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                "Dmitriy",
                "admin");
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                Map<String, String> dbSettings = new HashMap<>();
                dbSettings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                dbSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/users");
                dbSettings.put(Environment.USER, "Dmitriy");
                dbSettings.put(Environment.PASS, "admin");

                registryBuilder.applySettings(dbSettings);

                standardServiceRegistry = registryBuilder.build();

                MetadataSources sources = new MetadataSources(standardServiceRegistry);
                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
