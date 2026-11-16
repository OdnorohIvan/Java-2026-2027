package ua.study.calculator;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        try {
            System.out.print("Введіть перше число: ");
            double first = parseNumber(scanner.nextLine());

            System.out.print("Введіть друге число: ");
            double second = parseNumber(scanner.nextLine());

            System.out.print("Введіть операцію (+, -, *, /): ");
            String operation = scanner.nextLine().trim();

            double result = switch (operation) {
                case "+" -> calculator.add(first, second);
                case "-" -> calculator.subtract(first, second);
                case "*" -> calculator.multiply(first, second);
                case "/" -> calculator.divide(first, second);
                default -> throw new InvalidInputException("Оберіть одну з операцій: +, -, *, /.");
            };

            System.out.println("Результат: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Помилка обчислення: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("Помилка введення: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Помилка введення: недостатньо даних для обчислення.");
        } finally {
            System.out.println("Обробку запиту завершено.");
            scanner.close();
        }
    }

    static double parseNumber(String text) throws InvalidInputException {
        try {
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Введіть число, наприклад 5 або 2.5.");
        }
    }
}
