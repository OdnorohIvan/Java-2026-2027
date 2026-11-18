package ua.study.automaton;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordAutomatonTest {
    @ParameterizedTest
    @ValueSource(strings = {"", "test", "TS", "TEE", "TESS"})
    void endsInStateS(String text) {
        assertEquals('S', WordAutomaton.run(text));
    }

    @ParameterizedTest
    @ValueSource(strings = {"T", "TT", "TET", "TE ST"})
    void endsInState1(String text) {
        assertEquals('1', WordAutomaton.run(text));
    }

    @ParameterizedTest
    @ValueSource(strings = {"TE"})
    void endsInState2(String text) {
        assertEquals('2', WordAutomaton.run(text));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcTES"})
    void endsInState3(String text) {
        assertEquals('3', WordAutomaton.run(text));
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST", "abcTESTabc", "TES#TEST", "TESTEST", "TTEST", "TETEST"})
    void endsInStateF(String text) {
        assertEquals('F', WordAutomaton.run(text));
    }
}
