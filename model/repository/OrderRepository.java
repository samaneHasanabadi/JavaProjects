package model.repository;

import model.entity.OrderClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserInteraction;

public class OrderRepository extends CRUDOperation<OrderClass>{
	Logger logger = LoggerFactory.getLogger(UserInteraction.class);
	public void addOrder(OrderClass order) {
		creat(order);
	}

}
