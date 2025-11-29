package e_library.rest;

import e_library.model.Book;
import e_library.model.ELibrary;
import e_library.model.EducationLevel;
import e_library.service.XMLParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/elibrary")  // Changed from "/api/elibrary" to "/elibrary"
@CrossOrigin(origins = "*")
public class ELibraryRESTController {

    @Autowired
    private XMLParserService xmlParserService;

    // Get complete e-library
    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ELibrary> getELibrary() {
        return ResponseEntity.ok(xmlParserService.getELibrary());
    }

    // Get all education levels
    @GetMapping(value = "/education-levels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EducationLevel>> getAllEducationLevels() {
        return ResponseEntity.ok(xmlParserService.getAllEducationLevels());
    }

    // Get education level by code
    @GetMapping(value = "/education-levels/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EducationLevel> getEducationLevelByCode(@PathVariable String code) {
        EducationLevel level = xmlParserService.getEducationLevelByCode(code);
        if (level != null) {
            return ResponseEntity.ok(level);
        }
        return ResponseEntity.notFound().build();
    }

    // Get all books
    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(xmlParserService.getAllBooks());
    }

    // Get book by ID
    @GetMapping(value = "/books/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookById(@PathVariable String bookId) {
        Book book = xmlParserService.getBookById(bookId);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    // Get books by education level
    @GetMapping(value = "/books/education-level/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooksByEducationLevel(@PathVariable String code) {
        return ResponseEntity.ok(xmlParserService.getBooksByEducationLevel(code));
    }

    // Get books by language
    @GetMapping(value = "/books/language/{language}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooksByLanguage(@PathVariable String language) {
        return ResponseEntity.ok(xmlParserService.getBooksByLanguage(language));
    }

    // Get books by field
    @GetMapping(value = "/books/field/{field}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooksByField(@PathVariable String field) {
        return ResponseEntity.ok(xmlParserService.getBooksByField(field));
    }

    // Get books by specialization
    @GetMapping(value = "/books/specialization/{specialization}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooksBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(xmlParserService.getBooksBySpecialization(specialization));
    }

    // Get books by author
    @GetMapping(value = "/books/author/{author}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(xmlParserService.getBooksByAuthor(author));
    }

    // Get books by subject
    @GetMapping(value = "/books/subject/{subject}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooksBySubject(@PathVariable String subject) {
        return ResponseEntity.ok(xmlParserService.getBooksBySubject(subject));
    }

    // Get books by year
    @GetMapping(value = "/books/year/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooksByYear(@PathVariable int year) {
        return ResponseEntity.ok(xmlParserService.getBooksByYear(year));
    }

    // Get books by format
    @GetMapping(value = "/books/format/{format}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooksByFormat(@PathVariable String format) {
        return ResponseEntity.ok(xmlParserService.getBooksByFormat(format));
    }

    // Execute XPath query
    @PostMapping(value = "/xpath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> executeXPathQuery(@RequestBody Map<String, String> request) {
        String xpathQuery = request.get("query");
        if (xpathQuery == null || xpathQuery.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "XPath query is required");
            return ResponseEntity.badRequest().body(error);
        }
        
        String result = xmlParserService.executeXPathQuery(xpathQuery);
        Map<String, String> response = new HashMap<>();
        response.put("query", xpathQuery);
        response.put("result", result);
        return ResponseEntity.ok(response);
    }

    // Get statistics
    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBooks", xmlParserService.countTotalBooks());
        stats.put("totalEducationLevels", xmlParserService.getAllEducationLevels().size());
        
        Map<String, Integer> booksByLevel = new HashMap<>();
        for (EducationLevel level : xmlParserService.getAllEducationLevels()) {
            booksByLevel.put(level.getCode(), xmlParserService.countBooksByEducationLevel(level.getCode()));
        }
        stats.put("booksByEducationLevel", booksByLevel);
        
        return ResponseEntity.ok(stats);
    }

    // Search books by multiple criteria
    @GetMapping(value = "/books/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) final String language,
            @RequestParam(required = false) final String field,
            @RequestParam(required = false) final String format,
            @RequestParam(required = false) final Integer year) {
        
        List<Book> books = xmlParserService.getAllBooks();
        
        if (language != null) {
            List<Book> filtered = new ArrayList<>();
            for (Book b : books) {
                if (b.getLanguage().equalsIgnoreCase(language)) {
                    filtered.add(b);
                }
            }
            books = filtered;
        }
        if (field != null) {
            List<Book> filtered = new ArrayList<>();
            for (Book b : books) {
                if (field.equals(b.getField())) {
                    filtered.add(b);
                }
            }
            books = filtered;
        }
        if (format != null) {
            List<Book> filtered = new ArrayList<>();
            for (Book b : books) {
                if (b.getFormat().equalsIgnoreCase(format)) {
                    filtered.add(b);
                }
            }
            books = filtered;
        }
        if (year != null) {
            List<Book> filtered = new ArrayList<>();
            for (Book b : books) {
                if (b.getPublicationYear() == year) {
                    filtered.add(b);
                }
            }
            books = filtered;
        }
        
        return ResponseEntity.ok(books);
    }

    // Health check
    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "E-Library REST API");
        health.put("version", "1.0");
        return ResponseEntity.ok(health);
    }
}