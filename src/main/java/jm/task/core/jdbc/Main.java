package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Вика", "Малиенко", (byte) 35);
        userService.saveUser("Руслана", "Коробка", (byte) 18);
        userService.saveUser("Даша", "Колесникова", (byte) 23);
        userService.saveUser("Никита", "Малиенко", (byte) 11);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
