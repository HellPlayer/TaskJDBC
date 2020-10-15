package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

            session.createSQLQuery(sql);

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
            session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS usr";

            session.createSQLQuery(sql);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.persist(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Object user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) Util.getSessionFactory().openSession().createCriteria(User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "DELETE FROM usr";
        session.createQuery(hql).executeUpdate();
        transaction.commit();
        session.close();
    }
}
