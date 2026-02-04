package xmoke;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parseTaskIndex_validIndex_returnsZeroBased() {
        int index = Parser.parseTaskIndex("2", 5);
        assertEquals(1, index);
    }

    @Test
    public void parseTaskIndex_outOfRange_throwsIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> Parser.parseTaskIndex("6", 5));
    }

    @Test
    public void parseTaskIndex_notNumber_throwsNumberFormat() {
        assertThrows(NumberFormatException.class, () -> Parser.parseTaskIndex("abc", 5));
    }

    @Test
    public void parseDateTime_yyyyMMddHHmm_parsesCorrectly() {
        LocalDateTime dt = Parser.parseDateTime("2019-12-02 1800");
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), dt);
    }

    @Test
    public void parseDateTime_invalid_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, () -> Parser.parseDateTime("not a date"));
    }
}
