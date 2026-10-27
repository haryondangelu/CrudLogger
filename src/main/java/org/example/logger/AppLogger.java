package org.example.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppLogger {

    private static final String FILE_PATH = "app.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Construtor privado previne instanciação externa
    private AppLogger() {}

    // Classe interna estática responsável por carregar a instância única
    private static class Holder {
        private static final AppLogger INSTANCE = new AppLogger();
    }

    public static AppLogger getInstance() {
        return Holder.INSTANCE;
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void debug(String message) {
        log("DEBUG", message);
    }

    public void erro(String message) {
        log("ERRO", message);
    }

    private synchronized void log(String level, String message) {
        String logEntry = String.format("[%s] [%s] %s", LocalDateTime.now().format(FORMATTER), level, message);

        // Exibe no console
        System.out.println(logEntry);

        // Escreve no arquivo app.log
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(logEntry);
        } catch (IOException e) {
            System.err.println("Erro ao escrever log no arquivo: " + e.getMessage());
        }
    }
}