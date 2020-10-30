package service;

import model.entity.Order;
import model.repository.ConnectionRepository;
import model.repository.OrderRepository;

import java.sql.Connection;

public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();
    private Connection connection = ConnectionRepository.connectionRepository.getConnection();

    public void addOrderToDB(Order order){
        orderRepository.addOrder(connection, order);
    }
}
