package ua.study.automaton;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordAutomatonTest {
    static Object[][] testCases() {
        return new Object[][] {
            {"", 'S'},
            {"test", 'S'},
            {"TS", 'S'},
            {"TEE", 'S'},
            {"TESS", 'S'},
            {"T", '1'},
            {"TT", '1'},
            {"TET", '1'},
            {"TE ST", '1'},
            {"TE", '2'},
            {"abcTES", '3'},
            {"TEST", 'F'},
            {"abcTESTabc", 'F'},
            {"TES#TEST", 'F'},
            {"TESTEST", 'F'},
            {"TTEST", 'F'},
            {"TETEST", 'F'}
        };
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void returnsFinalState(String text, char expectedState) {
        assertEquals(expectedState, WordAutomaton.run(text));
    }
}
