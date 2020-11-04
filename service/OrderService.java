package service;

import model.Repository.OrderRepository;
import model.entity.Order;
import model.Repository.ConnectionRepository;

import java.sql.Connection;

public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();
    private Connection connection = ConnectionRepository.connectionRepository.getConnection();

    public void addOrderToDB(Order order){
        orderRepository.addOrder(connection, order);
    }
}
