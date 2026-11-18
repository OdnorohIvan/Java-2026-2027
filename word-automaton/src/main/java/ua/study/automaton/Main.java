package ua.study.automaton;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String text = scanner.nextLine();
            System.out.println(WordAutomaton.run(text));
        }
    }
}
