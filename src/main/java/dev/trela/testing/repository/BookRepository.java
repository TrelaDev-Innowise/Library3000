package dev.trela.testing.repository;



import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import dev.trela.testing.model.Book;
import dev.trela.testing.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Repository class responsible for managing book data stored in a CSV file.
 * Provides functionality to retrieve, add, update, delete, and search books.
 */
@Repository // Indicates this class is part of the data access layer in Spring
public class BookRepository {

    private static final String CSV_FILE_PATH = "src/main/resources/books.csv";
    private final CsvMapper csvMapper = new CsvMapper();

    // Service used to retrieve localized or custom error messages
    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService){
        this.messageService = messageService;
    }

    // Defines the CSV schema including headers and column types
    private final CsvSchema schema = CsvSchema.builder()
            .addColumn("id", CsvSchema.ColumnType.NUMBER)
            .addColumn("title", CsvSchema.ColumnType.STRING)
            .addColumn("author", CsvSchema.ColumnType.STRING)
            .addColumn("description", CsvSchema.ColumnType.STRING)
            .setUseHeader(true)
            .setColumnSeparator(',')
            .setQuoteChar('"')
            .build();


    public List<Book> getAllBooks() {
        try {
            File file = new File(CSV_FILE_PATH);

            // If the file doesn't exist, create it along with its parent directories
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                boolean created = file.createNewFile();
                if (!created) {
                    throw new IOException("File already exists or couldn't be created: " + file.getAbsolutePath());
                }
            }

            try (MappingIterator<Book> it = csvMapper.readerFor(Book.class).with(schema).readValues(file)) {
                return it.readAll();
            }

        } catch (IOException e) {
            throw new RuntimeException(messageService.getMessage("error.reading.csv"), e);
        }
    }


    public void addBook(Book book) throws IllegalArgumentException {
        List<Book> books = getAllBooks(); // Load existing books
        validateBookFields(book);
        book.setId(generateNewId(books));
        books.add(book);
        saveAllBooks(books);
    }

    private int generateNewId(List<Book> books){
        return books.isEmpty() ? 1 : books.getLast().getId() + 1;
    }


    private void validateBookFields(Book book) throws IllegalArgumentException{
        boolean isAnyFieldEmpty = book.getAuthor().isEmpty()||
                book.getDescription().isEmpty() ||
                book.getTitle().isEmpty();
        if (isAnyFieldEmpty) {
            throw new IllegalArgumentException(messageService.getMessage("error.empty.fields"));
        }
    }

    public void deleteBook(int bookId) {
        List<Book> books = getAllBooks();

        // Use binary search assuming the list is sorted by ID
        int index = Collections.binarySearch(
                books,
                new Book(bookId, null, null, null),
                Comparator.comparingInt(Book::getId)
        );

        if (index >= 0) {
            books.remove(index);     // Remove the book from the list
            saveAllBooks(books);     // Save the updated list
        } else {
            throw new NoSuchElementException(messageService.getMessage("error.no.such.book"));
        }
    }


    public void updateBook(Book updatedBook) {

        validateBookFields(updatedBook);
        List<Book> books = getAllBooks();

        Optional<Book> optionalExistingBook = books.stream()
                .filter(book -> book.getId() == updatedBook.getId())
                .findFirst();

        if (optionalExistingBook.isPresent()) {
            int existingBookIndex = books.indexOf(optionalExistingBook.get());
            books.set(existingBookIndex, updatedBook);  // Replace old book with the updated one
            saveAllBooks(books);                        // Save changes
        } else {
            throw new NoSuchElementException(messageService.getMessage("error.no.such.book"));
        }
    }


    public void saveAllBooks(List<Book> books) {
        try {
            csvMapper.writer(schema).writeValue(new File(CSV_FILE_PATH), books);
        } catch (IOException e) {
            throw new RuntimeException(messageService.getMessage("error.writing.csv"), e);
        }
    }

    public List<Book> searchByKeyword(String keyword) {
        return getAllBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                        book.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
