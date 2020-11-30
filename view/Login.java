package view;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ManagerService;

import java.util.Scanner;

public class Login {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please Enter your username");
        String username = scanner.next();
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("configuration/applicationContext.xml");
        ManagerService managerService = classPathXmlApplicationContext.getBean(ManagerService.class);
        if(managerService.checkUsername(username)){
            ManagerInteraction managerInteraction = classPathXmlApplicationContext.getBean(ManagerInteraction.class);
            managerInteraction.managerWorks();
        }else {
            UserInteraction userInteraction = classPathXmlApplicationContext.getBean(UserInteraction.class);
            userInteraction.userWorks(username);
        }
    }

}
