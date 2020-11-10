package service;

import model.repository.OrderRepository;
import model.entity.OrderClass;

public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();

    public void addOrderToDB(OrderClass order){
        orderRepository.addOrder(order);
    }
}
