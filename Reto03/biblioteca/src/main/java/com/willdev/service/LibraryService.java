package com.willdev.service;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.willdev.Main;
import com.willdev.exception.NoSuchElementException;
import com.willdev.exception.UserNotFoundException;
import com.willdev.model.Book;
import com.willdev.model.Loan;

public class LibraryService {

    Logger log = LoggerFactory.getLogger(Main.class);

    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    public LibraryService() {
        this.bookService = new BookService();
        this.userService = new UserService();
        this.loanService = new LoanService(bookService, userService);
    }

    // Menú de opciones
    public void menuLibrary() {
        log.info("Ingreso al menu de la libreria");
        System.out.println("---------------------------------------------------------");
        System.out.println("---------- Bienvenido al Sistema de Biblioteca ----------");
        System.out.println("---------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1. Menu Usuarios");
            System.out.println("2. Menu Libros");
            System.out.println("3. Menu Prestamos");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    log.debug("Menu Usuarios");
                    menuUser();
                    break;
                case 2:
                    log.debug("Menu Libros");
                    menuBook();
                    break;
                case 3:
                    log.debug("Menu Prestamos");
                    menuLoan();
                    break;
                case 4:
                    log.debug("Salio del Menu");
                    System.out.println("Cerrando sistema...");
                    System.out.println("------------------------------------------------------");

                    break;
                default:
                    log.debug("Marco una opcion no valida" + opcion);
                    System.out.println("Opción no válida");
            }
        } while (opcion != 4);

        scanner.close();
    }

    // Menú de opciones
    public void menuBook() {
        log.info("Ingreso al menu de libros");
        System.out.println("---------------------------------------------------------");
        System.out.println("-------------- Bienvenido al Menu de Libros -------------");
        System.out.println("---------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1. Agregar un Libro");
            System.out.println("2. Consultar un Libro");
            System.out.println("4. Actualizar el titulo de un Libro");
            System.out.println("5. Actualizar el author de un Libro");
            System.out.println("6. Borrar un Libro");
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
                case 4:
                    log.debug("Actulizar Titulo Libro");
                    updateTitleBook(scanner);
                    break;
                case 5:
                    log.debug("Actulizar Author Libro");
                    UpdateOwnerBook(scanner);
                    break;
                case 6:
                    log.debug("Borrar Libro");
                    deleteBook(scanner);
                    break;
                case 7:
                    log.debug("Salio del Menu Libros");
                    System.out.println("Salio del Menu Libros");
                    System.out.println("------------------------------------------------------");
                    break;
                default:
                    log.debug("Marco una opcion no valida" + opcion);
                    System.out.println("Opción no válida");
            }
        } while (opcion != 7);
    }

    // Menú de opciones
    public void menuUser() {
        log.info("Ingreso al menu de usuarios");
        System.out.println("---------------------------------------------------------");
        System.out.println("------------ Bienvenido al Menu de Usuarios -------------");
        System.out.println("---------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1. Agregar un Usuario");
            System.out.println("2. Actualizar el nombre de un Usuario");
            System.out.println("3. Actualizar el correo de un Usuario");
            System.out.println("4. Consultar un Usuario");
            System.out.println("5. Eliminar un Usuario");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    log.debug("Registrar Usuario");
                    RegisterUser(scanner);
                    break;
                case 2:
                    log.debug("Actualizar Nombre de Usuario");
                    updateNameUser(scanner);
                    break;
                case 3:
                    log.debug("Actualizar Correo de Usuario");
                    updateEmailUser(scanner);
                    break;
                case 4:
                    log.debug("Consultar Usuario");
                    getUser(scanner);
                    break;
                case 5:
                    log.debug("Eliminar Usuario");
                    deleteUser(scanner);
                    break;
                case 6:
                    log.debug("Salio del Menu de usuarios");
                    System.out.println("Salio del Menu de usuarios");
                    System.out.println("------------------------------------------------------");
                    break;
                default:
                    log.debug("Marco una opcion no valida" + opcion);
                    System.out.println("Opción no válida");
            }
        } while (opcion != 6);
    }

    // Menú de opciones
    public void menuLoan() {
        log.info("Ingreso al menu de prestamos");
        System.out.println("---------------------------------------------------------");
        System.out.println("------------ Bienvenido al Menu de Pretsamos ------------");
        System.out.println("---------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1. Realiza un Prestamo");
            System.out.println("2. Regresar un Prestamo (Libro)");
            System.out.println("3. Consultar un Prestamo por usuario");
            System.out.println("4. Consultar un Prestamo por Libro");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    log.debug("Realizar un prestamo");
                    MakeLoan(scanner);
                    break;
                case 2:
                    log.debug("Regresar un prestamo");
                    ReturnLoan(scanner);
                    break;
                case 3:
                    log.debug("Consultar un Prestamo por usuario");
                    getLoanByUser(scanner);
                    break;
                case 4:
                    log.debug("Consultar un Prestamo por Libro");
                    getLoanByBook(scanner);
                    break;
                case 5:
                System.out.println("Salio del Menu de Prestamos");
                System.out.println("------------------------------------------------------");
                    break;
                default:
                    log.debug("Marco una opcion no valida" + opcion);
                    System.out.println("Opción no válida");
            }
        } while (opcion != 5);
    }

    private void updateEmailUser(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("------------------ Actualizar Correo Usuario ------------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        System.out.print("Indique el Correo del Usuario: ");
        String emailUser = scanner.next();
        log.info("El correo del usuario es: " + emailUser);

        try {
            log.info("Actualizando el correo del usuario");
            userService.updateUserEmail(idUser, emailUser);
            System.out.println("----------- Correo del usuario actualizado con éxito. ------------");
            System.out.println("----------------------------------------------------");
        } catch (NoSuchElementException e) {
            log.error("El usuario no existe");
            System.out.println("El usuario no existe");
            System.out.println("------------------------------------------------------");
            return;
        }

    }

    private void updateNameUser(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("------------------ Actualizar Nombre Usuario ------------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        System.out.print("Indique el Nombre del Usuario: ");
        String nameUser = scanner.next();
        log.info("El nombre del usuario es: " + nameUser);

        try {
            log.info("Actualizando el nombre del usuario");
            userService.updateUserName(idUser, nameUser);
            System.out.println("----------- Nombre del usuario actualizado con éxito. ------------");
            System.out.println("----------------------------------------------------");
        } catch (NoSuchElementException e) {
            log.error("El usuario no existe");
            System.out.println("El usuario no existe");
            System.out.println("------------------------------------------------------");
            return;
        }

    }

    private void getLoanByBook(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("----------- Consultar un Prestamo Por Libro ------------");
        System.out.print("Indique el Id Del Libro: ");
        String idBook = scanner.next();
        log.info("El id del libro es: " + idBook);

        try {
            System.out.println("----------------------------------------------------");
            Loan loan = loanService.getLoanByBook(idBook);

            System.out.println(loan);
            log.info("Prestamo encontrado: " + loan);
            System.out.println("----------------------------------------------------");
        } catch (NoSuchElementException e) {
            System.out.println("El libro no tiene prestamos");
            log.error("El libro no tiene prestamos");
            System.out.println("------------------------------------------------------");
            return;
        }

    }

    private void getLoanByUser(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("----------- Consultar un Prestamo Por Usuario -----------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        try {
            System.out.println("----------------------------------------------------");
            Loan loan = loanService.getLoanByUser(idUser);

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

    private void deleteUser(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("-------------------- Eliminar Usuario --------------------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        try {
            log.info("Eliminando el usuario");
            userService.deleteUser(idUser);
            System.out.println("----------- Usuario eliminado con éxito. ------------");
            System.out.println("------------------------------------------------------");
        } catch (NoSuchElementException e) {
            log.error("El usuario no existe");
            System.out.println("El usuario no existe");
            System.out.println("------------------------------------------------------");
            return;
        }

    }

    private void getUser(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("-------------------- Consultar Usuario -------------------");
        System.out.print("Indique el Id Del Usuario: ");
        String idUser = scanner.next();
        log.info("El id del usuario es: " + idUser);

        try {
            log.info("Buscando el usuario");
            var user = userService.findUser(idUser);
            System.out.println("Usuario encontrado: " + user);
            System.out.println("----------------------------------------------------");
            log.info("Usuario encontrado: " + user);
        } catch (NoSuchElementException e) {
            log.error("El usuario no existe");
            System.out.println("El usuario no existe");
            System.out.println("------------------------------------------------------");
            return;
        }

    }

    private void deleteBook(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("-------------------- Eliminar Libro ---------------------");
        System.out.print("Indique el Id Del Libro: ");
        String idBook = scanner.next();
        log.info("El id del libro es: " + idBook);

        try {
            log.info("Eliminando el libro");
            bookService.deleteBook(idBook);
            System.out.println("----------- Libro eliminado con éxito. ------------");
            System.out.println("------------------------------------------------------");
        } catch (NoSuchElementException e) {
            log.error("El libro no existe");
            System.out.println("El libro no existe");
            System.out.println("------------------------------------------------------");
            return;
        }

    }

    private void UpdateOwnerBook(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("------------------ Actualizar Autor Libro ----------------");
        System.out.print("Indique el Id Del Libro: ");
        String idBook = scanner.next();
        log.info("El id del libro es: " + idBook);

        System.out.print("Indique el Autor del Libro: ");
        String ownerBook = scanner.next();
        log.info("El autor del libro es: " + ownerBook);

        try {
            log.info("Actualizando el autor del libro");
            bookService.updateBookOwner(idBook, ownerBook);
            System.out.println("----------- Autor del libro actualizado con éxito. ------------");
            System.out.println("----------------------------------------------------");
        } catch (NoSuchElementException e) {
            log.error("El libro no existe");
            System.out.println("El libro no existe");
            System.out.println("------------------------------------------------------");
            return;
        }

    }

    private void updateTitleBook(Scanner scanner) {
        System.out.println("---------------------------------------------------------");
        System.out.println("------------------ Actualizar Titulo Libro ---------------");
        System.out.print("Indique el Id Del Libro: ");
        String idBook = scanner.next();
        log.info("El id del libro es: " + idBook);

        System.out.print("Indique el Titulo del Libro: ");
        String titleBook = scanner.next();
        log.info("El titulo del libro es: " + titleBook);

        try {
            log.info("Actualizando el titulo del libro");
            bookService.updateBookTitle(idBook, titleBook);
            System.out.println("----------- Titulo del libro actualizado con éxito. ------------");
            System.out.println("----------------------------------------------------");
        } catch (NoSuchElementException e) {
            log.error("El libro no existe");
            System.out.println("El libro no existe");
            System.out.println("------------------------------------------------------");
            return;
        }

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
            if (bookService.findBook(idBook) != null) {
                log.error("El libro con el id " + idBook + " ya existe");
                System.out.println("El libro con el id " + idBook + " ya existe");
                return;
            }
        } catch (Exception e) {

            log.info("Registrando el libro");
            bookService.addBook(idBook, nameBook, ownerBook);
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
            Book book = bookService.findBook(idBook);
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

        System.out.print("Indique el Correo del Usuario: ");
        String emailUser = scanner.next();
        log.info("El email del usuario es: " + emailUser);

        try {
            if (userService.findUser(idUser) != null) {
                log.error("El Usuario con el id " + idUser + " ya existe");
                System.out.println("El Usuario con el id " + idUser + " ya existe");
                return;
            }
        } catch (Exception e) {

            log.info("Registrando el usuario");
            userService.addUser(idUser, nameUser, emailUser);
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
            loanService.addLoan(idUser, idBook);
            log.info("Prestamo registrado con exito idUser " + idUser + ", idBook " + idBook);
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
            loanService.returBook(idUser, idBook);
            log.info("Regreso registrado con exito idUser " + idUser + ", idBook " + idBook);
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
