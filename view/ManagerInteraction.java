package view;

import service.ManagerService;

import java.util.Scanner;

public class ManagerInteraction {
    private ManagerService managerService;

    public ManagerInteraction(ManagerService managerService){
        this.managerService = managerService;
    }

    public void managerWorks(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.user reports\n2.restaurant report\n3.exit");
        int a = scanner.nextInt();
        while (a!=3){
            switch (a) {
                case (1):
                    managerService.reportUsersWithMonthRegisterationAndSumOfOrderPrice();
                    System.out.println("1.user reports\n2.restaurant report\n3.exit");
                    a = scanner.nextInt();
                    break;
                case (2):
                    managerService.reportRestaurantDeliveryIncomeAndFoodSoldNumber();
                    System.out.println("1.user reports\n2.restaurant report\n3.exit");
                    a = scanner.nextInt();
                    break;
                default:
                    System.out.println("please enter the right input");
                    System.out.println("1.user reports\n2.restaurant report\n3.exit");
                    a = scanner.nextInt();
                    break;
            }
        }
    }
}
