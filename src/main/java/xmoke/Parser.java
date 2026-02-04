package xmoke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static int parseTaskIndex(String indexText, int taskCount) throws NumberFormatException {
        String trimmed = indexText.trim();
        int oneBasedIndex = Integer.parseInt(trimmed);
        int zeroBasedIndex = oneBasedIndex - 1;

        if (zeroBasedIndex < 0 || zeroBasedIndex >= taskCount) {
            throw new IndexOutOfBoundsException("xmoke.Task number is out of range.");
        }

        return zeroBasedIndex;
    }

    /**
     * Parse date and time from string.
     * Accepts formats:
     * - yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)
     * - yyyy-MM-dd (e.g., 2019-12-02) - defaults to 23:59
     * - d/M/yyyy HHmm (e.g., 2/12/2019 1800)
     * - d/M/yyyy (e.g., 2/12/2019) - defaults to 23:59
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        dateTimeStr = dateTimeStr.trim();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateTimeStr, formatter);
            return date.atTime(23, 59); // Default to end of day
        } catch (DateTimeParseException e) {

        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(dateTimeStr, formatter);
            return date.atTime(23, 59); // Default to end of day
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Unable to parse date/time: " + dateTimeStr, dateTimeStr, 0);
        }
    }

    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        dateStr = dateStr.trim();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Unable to parse date: " + dateStr, dateStr, 0);
        }
    }
}