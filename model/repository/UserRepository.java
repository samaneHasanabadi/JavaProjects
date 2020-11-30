package model.repository;

import model.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserRepository extends CRUDOperation<User> {

    public void addUser(User user) {
        creat(user);
    }

    public User getUserInfo(User user) {
        User dbUser = read(user.getId());
        if(dbUser != null){
            user.setName(dbUser.getName());
            user.setPostalCode(dbUser.getPostalCode());
            user.setAddress(dbUser.getAddress());
        }
        return user;
    }

    public void SetUserInfo(User user) {
        update(user);
    }

    public User getUserByMobileNumber(String mobileNumber){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery(prepareUserQuery(), User.class);
        query.setParameter("mobileNumber", mobileNumber);
        User user = query.uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    public User getUserByMobileNumberAndBasket(String mobileNumber){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery(prepareUserQueryWithBasket(), User.class);
        query.setParameter("mobileNumber", mobileNumber);
        User user = query.uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    private String prepareUserQuery() {
        return "select user from User user where user.mobileNumber =:mobileNumber";
    }

    private String prepareUserQueryWithBasket() {
        return "select user from User user where " +
                "user.mobileNumber =:mobileNumber and user.basket.isInvoiced = false";
    }
}
