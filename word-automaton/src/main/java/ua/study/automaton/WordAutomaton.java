package ua.study.automaton;

public class WordAutomaton {
    public static char run(String text) {
        char state = 'S';

        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);

            switch (state) {
                case 'S':
                    if (symbol == 'T') {
                        state = '1';
                    }
                    break;
                case '1':
                    if (symbol == 'E') {
                        state = '2';
                    } else if (symbol != 'T') {
                        state = 'S';
                    }
                    break;
                case '2':
                    if (symbol == 'S') {
                        state = '3';
                    } else if (symbol == 'T') {
                        state = '1';
                    } else {
                        state = 'S';
                    }
                    break;
                case '3':
                    if (symbol == 'T') {
                        state = 'F';
                    } else {
                        state = 'S';
                    }
                    break;
                case 'F':
                    break;
            }
        }

        return state;
    }
}
