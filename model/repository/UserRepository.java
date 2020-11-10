package model.repository;

import model.dto.UserDto;
import model.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserInteraction;

import java.util.List;

public class UserRepository extends CRUDOperation<User> {
    Logger logger = LoggerFactory.getLogger(UserInteraction.class);

    public void addUser(User user) {
        creat(user);
    }

    public User getUserInfo(User user) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Query query = session.createQuery(" from User user where user.id =: id");
        query.setParameter("id", user.getId());
        if(query.list() != null){
        List<User> list = query.list();
             user.setName(list.get(0).getName());
             user.setAddress(list.get(0).getAddress());
             user.setPostalCode(list.get(0).getPostalCode());
        }
        session.close();
        return user;
    }

    public void SetUserInfo(User user) {
        update(user);
    }
}
