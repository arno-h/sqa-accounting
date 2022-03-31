package at.campus02.input;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestInputHelper {

    @Test
    public void testValidDate() throws EOFException {
        // setup
        Scanner scanner = new Scanner("2022-03-31\n");
        PrintStream mockOut = mock(PrintStream.class);
        InputHelper inputHelper = new InputHelper(scanner, mockOut);
        // execute
        Date result = inputHelper.getDate("test");
        // verify
        Date expected = new Date(122, Calendar.MARCH, 31);
        assertEquals(expected, result);
        verify(mockOut, never()).print(anyString());
        verify(mockOut, atLeastOnce()).println(anyString());
    }

    @Test
    public void testValidDateAfterInvalidDate() throws EOFException {
        // setup
        Scanner scanner = new Scanner("31st Mar 2022\n2022-03-31\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream, true);
        InputHelper inputHelper = new InputHelper(scanner, out);
        // execute
        Date result = inputHelper.getDate("test");
        // verify
        Date expected = new Date(122, Calendar.MARCH, 31);
        assertEquals(expected, result);
        String msg = outputStream.toString(StandardCharsets.UTF_8);
        assertTrue(msg.contains("Wrong date format"));
    }

    @Test
    public void testValidDateAfterInvalidDate2() throws EOFException {
        // setup
        Scanner scanner = mock(Scanner.class);  // needs mockito-inline extension to mock "final class"
        when(scanner.next(anyString()))
                .thenThrow(new InputMismatchException())
                .thenReturn("2022-03-31");
        PrintStream out = mock(PrintStream.class);
        InputHelper inputHelper = new InputHelper(scanner, out);
        // execute
        Date result = inputHelper.getDate("test");
        // verify
        Date expected = new Date(122, Calendar.MARCH, 31);
        assertEquals(expected, result);
        verify(out, times(1)).println("Wrong date format.");
        verify(out, times(2)).println("test (empty string to abort) ");
    }
}
