package com.willdev;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibrarySystem {

    Logger log = LoggerFactory.getLogger(Main.class);

    private ManagementBooks managementBooks;
    private ManagementUsers managementUsers;
    private ManagementLoans managementLoans;

    public LibrarySystem() {
        this.managementBooks = new ManagementBooks();
        this.managementUsers = new ManagementUsers();
        this.managementLoans = new ManagementLoans(managementBooks, managementUsers);
    }

    // Menú de opciones
    public void menu() {
        log.info("Ingreso al menu");
        System.out.println("---------------------------------------------------------");
        System.out.println("---------- Bienvenido al Sistema de Biblioteca ----------");
        System.out.println("---------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1. Agregar un Libro");
            System.out.println("2. Consultar un Libro");
            System.out.println("3. Registrar un Usuario");
            System.out.println("4. Realizar un Prestamo");
            System.out.println("5. Consultar un Prestamo por Usuario");
            System.out.println("6. Regresar un Prestamo");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    log.debug("Registrar Libro");
                    RegisterBook(scanner);
                    break;
                case 2:
                    log.debug("Consultar Libro");
                    SeeBook(scanner);
                    break;
                case 3:
                    log.debug("Registrar Usuario");
                    RegisterUser(scanner);
                    break;
                case 4:
                    log.debug("Realizar Prestamo");
                    MakeLoan(scanner);
                    break;
                case 5:
                    log.debug("Consultar Prestamo por Usuario");
                    ConsultLoansbyUser(scanner);
                    break;
                case 6:
                    log.debug("Regresar Prestamo");
                    ReturnLoan(scanner);
                    break;
                case 7:
                    log.debug("Salio del Menu");
                    System.out.println("Cerrando sistema...");
                    break;
                default:
                    log.debug("Marco una opcion no valida" + opcion);
                    System.out.println("Opción no válida");
            }
        } while (opcion != 7);
    }

    private void RegisterBook(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("--------------------- Registar Libro --------------------");
        
        System.out.print("Indique el Id Del Libro: ");
        String idBook = scanner.next();
        log.info("El id del libro es: " + idBook);

        System.out.print("Indique el Titulo del Libro: ");
        String nameBook = scanner.next();
        log.info("El titulo del libro es: " + nameBook);

        System.out.print("Indique el Autor del Libro: ");
        String ownerBook = scanner.next();
        log.info("El autor del libro es: " + ownerBook);

        try {
            if (managementBooks.findBook(idBook) != null) {
                log.error("El libro con el id " + idBook + " ya existe");
                System.out.println("El libro con el id " + idBook + " ya existe");
                return;
            }
        } catch (Exception e) {

            log.info("Registrando el libro");
            managementBooks.addBook(idBook, nameBook, ownerBook);
            System.out.println("----------- Libro registrado con éxito. ------------");
            System.out.println("----------------------------------------------------");

        }

    }

    private void SeeBook(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("-------------------- Consultar Libro --------------------");
        System.out.print("Indique el Id Del Libro: ");
        String idBook = scanner.next();
        log.info("El id del libro es: " + idBook);

        try {
            log.info("Buscando el libro");
            Books book = managementBooks.findBook(idBook);
            System.out.println("Libro encontrado: " + book);
            System.out.println("----------------------------------------------------");
            log.info("Libro encontrado: " + book);
        } catch (NoSuchElementException e) {
            log.error("El libro no existe");
            System.out.println("El libro no existe");
            System.out.println("------------------------------------------------------");
            return;
        }

    }

    private void RegisterUser(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("-------------------- Registar Usuario -------------------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        System.out.print("Indique el Nombre del Usuario: ");
        String nameUser = scanner.next();
        log.info("El nombre del usuario es: " + nameUser);

        try {
            if (managementUsers.findUser(idUser) != null) {
                log.error("El Usuario con el id " + idUser + " ya existe");
                System.out.println("El Usuario con el id " + idUser + " ya existe");
                return;
            }
        } catch (Exception e) {

            log.info("Registrando el usuario");
            managementUsers.addUser(idUser, nameUser);
            System.out.println("----------- Usuario registrado con éxito. ------------");
            System.out.println("------------------------------------------------------");

        }

    }

    private void MakeLoan(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("------------------- Registar Prestamo -------------------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        System.out.print("Indique el Id Del Libro: ");
        String idBook = scanner.next();
        log.info("El id del libro es: " + idBook);

        try {
            log.info("Registrando el prestamo");
            managementLoans.addLoans(idUser, idBook);
            log.info("Prestamo registrado con exito idUser " + idUser+", idBook "+ idBook);
            System.out.println("----------- Prestamo registrado con éxito. -----------");
            System.out.println("------------------------------------------------------");
        } catch (NoSuchElementException e) {
            System.out.println("------------------------------------------------------");
            System.out.println("El libro no existe");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
            return;
        } catch (UserNotFoundException e) {
            System.out.println("------------------------------------------------------");
            log.error("El usuario no existe");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("------------------------------------------------------");
            log.error("El id Usuario o el Id Libro no pueden estar vacios");
            System.out.println("El id Usuario o el Id Libro no pueden estar vacios");
            System.out.println("------------------------------------------------------");
            return;
        } catch (IllegalStateException e) {
            System.out.println("------------------------------------------------------");
            log.error("El libro ya esta prestado");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
        }

    }

    private void ConsultLoansbyUser(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("----------- Consultar un Prestamo Por Usuario -----------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        try {
            System.out.println("----------------------------------------------------");
            Loans loan = managementLoans.getLoans(idUser);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.println(loan);
            log.info("Prestamo encontrado: " + loan);
            System.out.println("----------------------------------------------------");
        } catch (NoSuchElementException e) {
            System.out.println("El usuario no tiene prestamos");
            log.error("El usuario no tiene prestamos");
            System.out.println("------------------------------------------------------");
            return;
        }

    }


    private void ReturnLoan(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("------------------- Regresar Prestamo -------------------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        System.out.print("Indique el Id Del Libro: ");
        String idBook = scanner.next();
        log.info("El id del libro es: " + idBook);

        try {
            managementLoans.returLoans(idUser, idBook);
            log.info("Regreso registrado con exito idUser " + idUser+", idBook "+ idBook);
            System.out.println("----------- Regreso registrado con éxito. -----------");
            System.out.println("------------------------------------------------------");
        } catch (NoSuchElementException e) {
            log.error("El usuario no tiene prestamos");
            System.out.println("El usuario no tiene prestamos");
            System.out.println("------------------------------------------------------");
            return;
        } 

    }

}
