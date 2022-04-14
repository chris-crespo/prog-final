package utils.test;

import org.junit.Test;
import static org.junit.Assert.*;

import utils.Result;

public class ResultTest {
    private void pass() {
        assertTrue(true);
    }

    @Test
    public void ofOkTest() {
        Result.of(() -> 1)
            .ifOk(x -> assertEquals((int)x, 1))
            .ifError(err -> fail());
    }

    @Test
    public void ofErrorTest() {
        Result.of(() -> { throw new Exception(); })
            .ifOk(x -> fail())
            .ifError(err -> pass());
    }
}
