package view;

import service.ManagerService;

import java.util.Scanner;

public class Login {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please Enter your username");
        String username = scanner.next();
        ManagerService managerService = new ManagerService();
        if(managerService.checkUsername(username)){
            ManagerInteraction managerInteraction = new ManagerInteraction();
            managerInteraction.managerWorks();
        }else {
            UserInteraction userInteraction = new UserInteraction();
            userInteraction.userWorks(username);
        }
    }

}
