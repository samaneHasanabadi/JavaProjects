package service;

import model.dao.OrderDao;
import model.entity.Order;
import model.dao.ConnectionDao;

import java.sql.Connection;

public class OrderService {
    private OrderDao orderRepository = new OrderDao();
    private Connection connection = ConnectionDao.connectionRepository.getConnection();

    public void addOrderToDB(Order order){
        orderRepository.addOrder(connection, order);
    }
}
