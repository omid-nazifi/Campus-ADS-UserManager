package at.campus.ads.common.utils;

import java.util.Scanner;

public class ConsoleUtils {

    public static String readLineFromConsole(String entry) {
        Scanner input = new Scanner(System.in);
        System.out.print(entry);
        return input.nextLine();
    }

    public static int readNumberFromConsole(String entry) {
        Scanner input = new Scanner(System.in);
        System.out.print(entry);
        return input.nextInt();
    }

    public static boolean confirmUserInput(String text) {
        String input = ConsoleUtils.readLineFromConsole(text + " ( y / other key )");
        return input.equals("y");
    }
}
