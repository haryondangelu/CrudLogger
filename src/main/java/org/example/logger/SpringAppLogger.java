package org.example.logger;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SpringAppLogger {

    private static final String FILE_PATH = "app.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void info(String message) { log("INFO", message); }
    public void debug(String message) { log("DEBUG", message); }
    public void erro(String message) { log("ERRO", message); }

    private synchronized void log(String level, String message) {
        String logEntry = String.format("[%s] [%s] (SpringBean) %s", LocalDateTime.now().format(FORMATTER), level, message);
        System.out.println(logEntry);
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(logEntry);
        } catch (IOException e) {
            System.err.println("Erro ao escrever log no arquivo: " + e.getMessage());
        }
    }
}