import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static final List<Character> ALPHABET = Arrays.asList('а', 'б', 'в',
            'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"',
            '\'', ':', '!', '?', ' ');
    private static final String inputPath = "src/resources/input.txt";
    private static final String outputPath = "src/resources/output.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showEncryptionMenu();
        System.out.println();
        showDecryptionMenu();
    }

    public static void showEncryptionMenu() {
        System.out.println("Текст для шифрования должен быть расположен в " + inputPath);

        System.out.println();
        String textToEncrypt = readTextFromFileToString(inputPath);
        System.out.println("Текущий текст для шифрования:");
        System.out.println(textToEncrypt);

        System.out.println();
        System.out.println("Укажите ключ смещения для шифрования:");
        int shiftKey = scanner.nextInt();

        String encryptedText = encryptText(textToEncrypt, shiftKey);
        System.out.println();
        System.out.println("Зашифрованный текст (ключ смещения = " + shiftKey + "):");
        System.out.println(encryptedText);

        writeTextToFileFromString(outputPath, encryptedText);
        System.out.println();
        System.out.println("Зашифрованный текст был записан в " + outputPath);
    }

    public static void showDecryptionMenu() {
        System.out.println("Зашифрованный текст расположен в " + outputPath);

        System.out.println();
        String textToDecrypt = readTextFromFileToString(outputPath);
        System.out.println("Текущий зашифрованный текст:");
        System.out.println(textToDecrypt);

        System.out.println();
        System.out.println("Укажите ключ смещения для расшифрования:");
        int shiftKey = scanner.nextInt();

        String decryptedText = decryptText(textToDecrypt, shiftKey);
        System.out.println();
        System.out.println("Расшифрованный текст (ключ смещения = " + shiftKey + "):");
        System.out.println(decryptedText);

        writeTextToFileFromString(outputPath, decryptedText);
        System.out.println();
        System.out.println("Расшифрованный текст был записан в " + outputPath);
    }

    public static String readTextFromFileToString(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeTextToFileFromString(String filePath, String text) {
        try {
            Files.writeString(Path.of(filePath), text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptText(String text, int shiftKey) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] textAsCharArray = text.toCharArray();
        for (char symbol : textAsCharArray) {
            processSymbol(shiftKey, symbol, stringBuilder);
        }
        return stringBuilder.toString();
    }

    private static void processSymbol(int shiftKey, char originalSymbol, StringBuilder stringBuilder) {
        if (ALPHABET.contains(originalSymbol)) {
            int newIndex = getShiftedIndex(shiftKey, originalSymbol);
            char newSymbol = getEncryptedSymbol(originalSymbol, newIndex);
            stringBuilder.append(newSymbol);
        } else {
            stringBuilder.append(originalSymbol);
        }
    }

    private static char getEncryptedSymbol(char originalSymbol, int newIndex) {
        if (Character.isUpperCase(originalSymbol)) {
            return Character.toUpperCase(ALPHABET.get(newIndex));
        } else {
            return ALPHABET.get(newIndex);
        }
    }

    private static int getShiftedIndex(int shiftKey, char originalSymbol) {
        int originalIndex = ALPHABET.indexOf(originalSymbol);
        int shiftedIndex = (originalIndex + shiftKey) % ALPHABET.size();
        if (shiftedIndex < 0) {
            shiftedIndex += ALPHABET.size();
        }
        return shiftedIndex;
    }

    public static String decryptText(String text, int shift) {
        return encryptText(text, -shift);
    }

}
