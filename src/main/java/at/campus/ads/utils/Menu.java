package at.campus.ads.utils;

public class Menu {
    public static void showWelcomeMessage() {
        System.out.println();
        System.out.println("Herzlich willkommen.");
    }

    public static int showStartPageMenu() {
        System.out.println("Herzlich willkommen. Bitte loggen ein, Wenn Sie einen Konto haben, sonst legen neuen Konto an:");
        return ConsoleUtils.readNumberFromConsole("\t1. Einloggen \n\t2. Registrierung \n\t0.Ausgang \n");
    }

    public static int showHomeMenu() {
        System.out.println("Liebe Kundin / Lieber Kunde, Sie sind in Ihr Profil.");
        return ConsoleUtils.readNumberFromConsole("\t3. Passwort ändern \n\t4. Konto löschen \n\t5. Ausloggen");
    }

}
