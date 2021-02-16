package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static int turn = 1;
    static Scanner scanner = new Scanner(System.in);
    static int lengthOfSecretCode;
    static int numberOfPossSymbol;
    static String secretCode;
    static String userGuess;
    static char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static void main(String[] args) {
        getLengthOfSecretCode();
        possibleSymbolRange();
        generateSecretCode();
        secretCodeGenerated();
        userAnswer();
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public static void getLengthOfSecretCode() {
        String length = "";
        try {
            System.out.println("Input the length of the secret code:");
            length = scanner.nextLine();
            lengthOfSecretCode = Integer.parseInt(length);
            if (lengthOfSecretCode == 0 || lengthOfSecretCode > 36) {
                System.out.println("Error");
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + length +
                    "\" isn't a valid number.");
            System.exit(0);
        }
    }

    public static void possibleSymbolRange() {
        System.out.println("Input the number of possible symbols in the code:");
        int n = scanner.nextInt();
        if (n > lengthOfSecretCode) {
            if (n <= 36) {
                numberOfPossSymbol = n;
            } else {
                System.out.println("Error: maximum number of possible " +
                        "symbols in the code is 36 (0-9, a-z).");
                System.exit(0);
            }
        } else {
            System.out.println("Error: it's not possible to generate " +
                    "a code with a length of "+ lengthOfSecretCode +
                    " with " + n + " unique symbols.");
            System.exit(0);
        }
    }

    public static void userAnswer() {
        do {
            System.out.println("Turn " + turn++ + ":");
            userGuess = scanner.next();
            System.out.println(gameResult());
        } while (!userGuess.equals(secretCode));
    }

    public static void secretCodeGenerated() {
        StringBuilder text = new StringBuilder("The secret is prepared: ");
        text.append("*".repeat(Math.max(0, lengthOfSecretCode)));
        text.append(" (");;
        if (numberOfPossSymbol == 1) {
            text.append("0");
        } else if (numberOfPossSymbol > 10) {
            text.append("0-9, ");
            if (numberOfPossSymbol == 10) {
                text.append("a");
            } else {
                text.append("a-");
                text.append(chars[numberOfPossSymbol - 1]);
            }
        } else {
            text.append(chars[0])
                    .append("-")
                    .append(chars[numberOfPossSymbol - 1]);
        }
        text.append(").");
        System.out.println(text);
        System.out.println("Okay, let's start a game!");
    }

    public static void generateSecretCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        while (str.length() != lengthOfSecretCode) {
            int randIndex = random.nextInt(numberOfPossSymbol);
            if (!isContains(str, chars[randIndex])) {
                str.append(chars[randIndex]);
            }
        }
        secretCode = str.toString();
    }


    public static String gameResult() {
        int bulls = 0;
        int cows = 0;
        StringBuilder sb = new StringBuilder("Grade: ");
        if (userGuess.equals(secretCode)) {
            sb.append(lengthOfSecretCode).append(" bull");
            if (lengthOfSecretCode > 1) {
                sb.append("s");
            }
            return sb.toString();
        }
        for (int i = 0; i < lengthOfSecretCode; i++) {
            if (secretCode.charAt(i) == userGuess.charAt(i)) {
                bulls++;
            } else {
                if (isCow(userGuess.charAt(i))) {
                    cows++;
                }
            }
        }
        if (cows < 1 && bulls < 1) {
            sb.append("None.");
        } else {
            if (bulls > 0) {
                sb.append(bulls).append(" bull");
                if (bulls > 1) {
                    sb.append("s");
                }
                if (cows > 0) {
                    sb.append(" and ");
                }
            }
            if (cows > 0) {
                sb.append(cows).append(" cow");
                if (cows > 1) {
                    sb.append("s");
                }
            }
        }
        return sb.toString();
    }

    static boolean isBull(int i) {
        return secretCode.charAt(i) == userGuess.charAt(i);
    }

    static boolean isCow(char c) {
        boolean cow = true;
        for (int j = 0; j < secretCode.length(); j++) {
            if (secretCode.charAt(j) == c && isBull(j)) {
                cow = false;
                break;
            }
        }
        return cow;
    }

    public static boolean isContains(StringBuilder s, char c) {
        boolean result = false;
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == c) {
                result = true;
                break;
            }
        }
        return result;
    }
}