package xmoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class ParserTest {

    // Positive: valid 1-based index is converted to correct 0-based index.
    @Test
    public void parseTaskIndex_validIndex_returnsZeroBased() {
        int index = Parser.parseTaskIndex("2", 5);
        assertEquals(1, index);
    }

    // Negative: index greater than task count throws IndexOutOfBoundsException.
    @Test
    public void parseTaskIndex_outOfRange_throwsIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> Parser.parseTaskIndex("6", 5));
    }

    // Negative: non-numeric index throws NumberFormatException.
    @Test
    public void parseTaskIndex_notNumber_throwsNumberFormat() {
        assertThrows(NumberFormatException.class, () -> Parser.parseTaskIndex("abc", 5));
    }

    // Positive: valid date-time string in yyyy-MM-dd HHmm format is parsed correctly.
    @Test
    public void parseDateTime_yyyyMMddHHmm_parsesCorrectly() {
        LocalDateTime dt = Parser.parseDateTime("2019-12-02 1800");
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), dt);
    }

    // Negative: invalid date-time string throws DateTimeParseException.
    @Test
    public void parseDateTime_invalid_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, () -> Parser.parseDateTime("not a date"));
    }
}
