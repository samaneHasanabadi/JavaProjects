package model.repository;

import model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getById(int id);
    User findByMobileNumber(String mobileNumber);
    User findByMobileNumberAndBasket_IsInvoiced(String mobileNumber, Boolean isInvoiced);
}
