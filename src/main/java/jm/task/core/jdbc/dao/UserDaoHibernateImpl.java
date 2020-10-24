package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS usr (id BIGINT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(45) NOT NULL," +
                    "  lastname VARCHAR(45) NOT NULL," +
                    "  age TINYINT NOT NULL," +
                    "  PRIMARY KEY (id))";

            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS usr";

            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            User user = new User(name, lastName, age);

            session.save(user);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Object user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Session session = Util.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            Class<User> userClass = User.class;
            CriteriaQuery<User> query = builder.createQuery(userClass);
            Root<User> root = query.from(userClass);
            query.select(root);
            Query<User> q = session.createQuery(query);
            List<User> list = q.getResultList();
            return list;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            String sql = "TRUNCATE TABLE usr";

            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
