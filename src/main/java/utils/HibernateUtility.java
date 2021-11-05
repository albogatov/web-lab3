package utils;

import data.Result;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
    public static SessionFactory getSessionFactory() {
        SessionFactory sf  = null;
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Result.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sf = configuration.buildSessionFactory(builder.build());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sf;
    }
}
