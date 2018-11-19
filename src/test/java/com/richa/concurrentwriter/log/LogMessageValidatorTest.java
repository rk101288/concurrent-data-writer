package com.richa.concurrentwriter.log;

import com.richa.concurrentwriter.log.LogMessageValidator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class LogMessageValidatorTest {
    private LogMessageValidator validator = new LogMessageValidator();

    @Test
    public void nonNumberMessageShouldFail() {
        List<String> messages = createTestList("dhjshdfhdsdf");
        assertFalse(validator.isValidLogMessage(messages));
    }

    @Test
    public void multipleMessagesShouldFail() {
        List<String> messages = createTestList("123456789", "sdsd");
        assertFalse(validator.isValidLogMessage(messages));
    }

    @Test
    public void messageLengthLessThanNineShouldFail() {
        List<String> messages = createTestList("sdsd");
        assertFalse(validator.isValidLogMessage(messages));
    }

    @Test
    public void emptyMessageShouldFail() {
        List<String> messages = createTestList("");
        assertFalse(validator.isValidLogMessage(messages));
    }

    @Test
    public void nullShouldFail() {
        assertFalse(validator.isValidLogMessage(null));
    }

    @Test
    public void terminateShouldNotFail() {
        List<String> messages = createTestList("terminate");
        assertTrue(validator.isValidLogMessage(messages));
    }

    @Test
    public void terminateUpperCaseShouldFail() {
        List<String> messages = createTestList("Terminate");
        assertFalse(validator.isValidLogMessage(null));
    }


    private List<String> createTestList(String... messages) {
        return new ArrayList<>(Arrays.asList(messages));
    }
}
