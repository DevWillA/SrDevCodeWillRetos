package com.willdev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        
        Logger log = LoggerFactory.getLogger(Main.class);

        log.info("Iniciando la aplicacion");
        LibraryService librery = new LibraryService();
        librery.menu();

        log.info("Finalizando la aplicacion");
        System.out.println("Gracias por usar nuestro servicio");

    }
}