package com.spring;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spring.entity.TV;
import com.spring.service.TVService;

@SpringBootApplication
public class TVStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TVStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(TVService service) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\nМеню:");
                System.out.println("1. Добавить телевизор");
                System.out.println("2. Вывести все телевизоры");
                System.out.println("3. Редактировать телевизор по ID");
                System.out.println("4. Удалить телевизор по ID");
                System.out.println("5. Поиск телевизора с ценой выше указанной");
                System.out.println("6. Выход");

                int choice = getIntInput(scanner, "Выберите опцию: ");

                switch (choice) {
                    case 1:
                        addTV(scanner, service);
                        break;
                    case 2:
                        service.getAllTV().forEach(System.out::println);
                        break;
                    case 3:
                        editTV(scanner, service);
                        break;
                    case 4:
                        deleteTV(scanner, service);
                        break;
                    case 5:
                        searchTV(scanner, service);
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Неверный выбор");
                }
            }
        };
    }

    private static void addTV(Scanner scanner, TVService service) {
        System.out.print("Производитель: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Тип экрана: ");
        String screenType = scanner.nextLine();
        Double price = getDoubleInput(scanner, "Цена: ");
        Integer quantity = getIntInput(scanner, "Количество: ");

        TV TV = new TV(manufacturer, model, screenType, price, quantity);
        service.addTV(TV);
        System.out.println("Телевизор добавлен");
    }

    private static void editTV(Scanner scanner, TVService service) {
        Long id = getLongInput(scanner, "ID телевизора: ");
        if (service.getTVById(id).isEmpty()) {
            System.out.println("Телевизор не найден");
            return;
        }

        System.out.print("Производитель: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Тип экрана: ");
        String screenType = scanner.nextLine();
        System.out.print("Цена: ");
        Double price = scanner.nextDouble();
        System.out.print("Количество: ");
        Integer quantity = scanner.nextInt();
        TV updated = new TV(manufacturer, model, screenType, price, quantity);
        service.updateTV(id, updated);
        System.out.println("Телевизор обновлен");
    }

    private static void deleteTV(Scanner scanner, TVService service) {
        Long id = getLongInput(scanner, "ID телевизора: ");
        service.deleteTV(id);
        System.out.println("Телевизор удален");
    }

    private static void searchTV(Scanner scanner, TVService service) {
        Double price = getDoubleInput(scanner, "Цена выше: ");
        service.searchByPriceGreaterThan(price).forEach(System.out::println);
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    private static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    private static long getLongInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }
}