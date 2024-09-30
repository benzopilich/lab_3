import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        // Чтение данных из файла
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String lexemesLine = reader.readLine();
        String delimitersLine = reader.readLine();
        reader.close();

        // Разбиение строки на лексемы
        String[] delimiters = delimitersLine.split("");
        StringTokenizer tokenizer = new StringTokenizer(lexemesLine, delimitersLine);
        List<String> lexemes = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            lexemes.add(tokenizer.nextToken());
        }

        // Обработка лексем
        List<String> nonNumericLexemes = new ArrayList<>();
        List<String> dateLexemes = new ArrayList<>();
        for (String lexeme : lexemes) {
            if (!isNumeric(lexeme)) {
                nonNumericLexemes.add(lexeme);
                if (isDate(lexeme)) {
                    dateLexemes.add(lexeme);
                }
            }
        }
        dateLexemes.sort((o1, o2) -> {
            String[] yeat1 = o1.split(" ");
            String[] yeat2 = o2.split(" ");
            if (yeat1[2].equals(yeat2[2])) {
                if (yeat1[1].equals(yeat2[1])) {
                    if (yeat1[0].equals(yeat2[0])) {
                        return 0;
                    }
                }
                return 1;

            }
            return -1;
        });
        // Добавление случайного числа после лексемы на русском
        Random random = new Random();
        for (int i = 0; i < nonNumericLexemes.size(); i++) {
            if (isRussian(nonNumericLexemes.get(i))) {
                nonNumericLexemes.set(i, nonNumericLexemes.get(i) + random.nextInt(100));
            }
        }

        // Удаление подстрок
        lexemes.removeIf(lexeme -> lexeme.length() == 1 && !Character.isLetterOrDigit(lexeme.charAt(0)));

        // Запись результатов в файл
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        for (String lexeme : lexemes) {
            writer.write(lexeme + " ");
        }
        writer.newLine();
        for (String lexeme : nonNumericLexemes) {
            writer.write(lexeme + " ");
        }
        writer.newLine();
        for (String lexeme : dateLexemes) {
            writer.write(lexeme + " ");
        }

        writer.close();
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd yy");
            sdf.setLenient(false);
            sdf.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isRussian(String str) {
        return str.chars().anyMatch(ch -> Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.CYRILLIC);
    }
}
