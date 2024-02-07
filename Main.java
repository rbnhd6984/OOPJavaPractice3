package practice3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print(
                        "1 - Игра с английским алфавитом\n" +
                        "2 - Игра с русским алфавитом\n" +
                        "3 - Игра с цифрами\n" +
                        "Выберите игру: ");
        Scanner scanner = new Scanner(System.in);
        int gameChoice = scanner.nextInt();
        scanner.nextLine();

        AbstractGame ag;
        switch (gameChoice) {
            case 1:
                ag = new EnglishAlphabetGame();
                ag.start(4, 3);
                System.out.println(
                        "Вы выбрали игру с английским алфавитом, " +
                        "угадайте комбинацию из " + ag.sizeWord +
                        " букв за " + ag.tryCount + " попытки.");
                break;
            case 2:
                ag = new RussianAlphabetGame();
                ag.start(4, 3);
                System.out.println(
                        "Вы выбрали игру с русским алфавитом, " +
                        "угадайте комбинацию из " + ag.sizeWord +
                        " букв за " + ag.tryCount + " попытки.");
                break;
            case 3:
                ag = new NumberGame();
                ag.start(4, 3);
                System.out.println(
                        "Вы выбрали игру с цифрами, " +
                        "угадайте комбинацию из " + ag.sizeWord +
                        " цифр за " + ag.tryCount + " попытки.");
                break;
            default:
                throw new IllegalArgumentException("Неверный выбор игры");
        }

        while (ag.getGameStatus().equals(GameStatus.START)) {
            System.out.print("Введите значение: ");
            Answer answer = ag.inputValue(scanner.nextLine());
            System.out.println(answer);
            boolean validChoice = false;
            while (!validChoice) {
                System.out.print(
                        "Осталось попыток - " + ag.tryCount + ", что будете делать?\n" +
                        "1 - Посмотреть лог\n" +
                        "2 - Перезапустить игру\n" +
                        "3 - Продолжить текущую игру\n" +
                        "Выбор: ");
                int actionChoice = scanner.nextInt();
                scanner.nextLine();
                switch (actionChoice) {
                    case 1:
                        viewLog();
                        break; case 2: main(args);
                        return; case 3: validChoice = true;
                        break; default: System.out.println("Неверный выбор действия. Попробуйте еще раз.");
                }
            }
        }
        if (ag.getGameStatus().equals(GameStatus.WIN)) {
            System.out.println("Вы победили!");
        } else if (ag.getGameStatus().equals(GameStatus.LOOSE)) {
            System.out.println("Вы проиграли!");
        } else {
            System.out.println("Неопознанный статус игры");
        }
        scanner.close();
    }
    public static void viewLog() {
        try {
            FileReader reader = new FileReader("game.log");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

