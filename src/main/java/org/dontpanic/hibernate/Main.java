package org.dontpanic.hibernate;

import org.dontpanic.hibernate.entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nMovie Database Menu:");
                System.out.println("1. List all movies");
                System.out.println("2. Search movies by title");
                System.out.println("3. Add new movie");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        listAllMovies();
                        break;
                    case 2:
                        System.out.print("Enter title to search: ");
                        String searchTitle = scanner.nextLine();
                        searchMoviesByTitle(searchTitle);
                        break;
                    case 3:
                        addNewMovie(scanner);
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }
    }

    private static void listAllMovies() {
        EntityManager entityManager = JPAEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Movie> query = entityManager.createQuery("SELECT m FROM Movie m", Movie.class);
            List<Movie> movies = query.getResultList();

            if (movies.isEmpty()) {
                System.out.println("No movies found.");
                return;
            }

            System.out.println("\nAll Movies:");
            movies.forEach(movie -> System.out.printf("ID: %d, Title: %s",
                    movie.getId(), movie.getTitle()));
        } finally {
            entityManager.close();
        }
    }

    private static void searchMoviesByTitle(String title) {
        EntityManager entityManager = JPAEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Movie> query = entityManager.createQuery(
                    "SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(:title)", Movie.class);
            query.setParameter("title", "%" + title + "%");
            List<Movie> movies = query.getResultList();

            if (movies.isEmpty()) {
                System.out.println("No movies found matching: " + title);
                return;
            }

            System.out.println("\nMatching Movies:");
            movies.forEach(movie -> System.out.printf("ID: %d, Title: %s",
                    movie.getId(), movie.getTitle()));
        } finally {
            entityManager.close();
        }
    }

    private static void addNewMovie(Scanner scanner) {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();

        Movie movie = new Movie();
        movie.setTitle(title);

        EntityManager entityManager = JPAEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(movie);
            entityManager.getTransaction().commit();
            System.out.println("Movie added successfully!");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error adding movie: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}