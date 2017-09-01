package ru.maximov.sergey;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

/**
 * На сервере с ОС Linux имеется каталог с n-количеством файлов и неизвестным уровнем иерархии. В любом из этих файлов может встречаться JDBC-коннект к БД.
 * Написать скрипт, заменяющий во всех этих коннектах имеющийся host_name на ‘hosttestserver.mydomain.ru’
 */
public class ReplaceJDBC {
    public static void replaceHostName(File dir, String regex, String replacement) {
        int count = 0;      // счетчик файлов
        String s = null;
        List<File> files = (List<File>) FileUtils.listFiles(dir, null, true);
        for (File file : files) {
            // получаем для каждого вложенного файла данные
            try {
                // Список файлов в каталоге и вложенных каталогах
                // Класс FileUtils гарантирует получение файлов во вложенных каталогах
                s = FileUtils.readFileToString(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // для примера возьмем MySQL
            // формат коннекта к БД имеет вид "jdbc:mysql://hostname/databaseName"
            // задача сменить известный "hostname"  во всех файлах на "hosttestserver.mydomain.ru"
            // для примера возмем hostname "localhost"
            // заменяем данные
            // единственный момент. ничего не сказано про кодировку. поэтому по умолчанию

            if (s != null) {
                if (s.contains(regex)) {

                    String temp = s.replaceFirst(regex, replacement);
                    try {
                        // пересохраняем файл
                        FileUtils.writeStringToFile(file, temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }
        System.out.println("Изменено " + count + " файл(ов) из " + files.size());
    }


    public static void main(String[] args) throws IOException {
        // Исходные данные для замены
        String regex = "localhost";
        String replacement = "hosttestserver.mydomain.ru";

        // Определяем каталог (например корневой)
//      File dir = new File("C://"); // для windows
        File dir = new File("/"); // для Linux
        replaceHostName(dir, regex, replacement);
    }
}
