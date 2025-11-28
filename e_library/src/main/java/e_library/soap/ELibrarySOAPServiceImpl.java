package e_library.soap;

import e_library.model.Book;
import e_library.model.EducationLevel;
import e_library.service.XMLParserService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@WebService(
    serviceName = "ELibraryService",
    portName = "ELibraryPort",
    targetNamespace = "http://soap.e_library/",
    endpointInterface = "e_library.soap.ELibrarySOAPService"
)
public class ELibrarySOAPServiceImpl implements ELibrarySOAPService {

    @Autowired
    private XMLParserService xmlParserService;

    @Override
    public List<EducationLevel> getAllEducationLevels() {
        return xmlParserService.getAllEducationLevels();
    }

    @Override
    public EducationLevel getEducationLevelByCode(String code) {
        return xmlParserService.getEducationLevelByCode(code);
    }

    @Override
    public List<Book> getAllBooks() {
        return xmlParserService.getAllBooks();
    }

    @Override
    public Book getBookById(String bookId) {
        return xmlParserService.getBookById(bookId);
    }

    @Override
    public List<Book> getBooksByEducationLevel(String code) {
        return xmlParserService.getBooksByEducationLevel(code);
    }

    @Override
    public List<Book> getBooksByLanguage(String language) {
        return xmlParserService.getBooksByLanguage(language);
    }

    @Override
    public List<Book> getBooksByField(String field) {
        return xmlParserService.getBooksByField(field);
    }

    @Override
    public List<Book> getBooksBySpecialization(String specialization) {
        return xmlParserService.getBooksBySpecialization(specialization);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return xmlParserService.getBooksByAuthor(author);
    }

    @Override
    public List<Book> getBooksBySubject(String subject) {
        return xmlParserService.getBooksBySubject(subject);
    }

    @Override
    public List<Book> getBooksByYear(int year) {
        return xmlParserService.getBooksByYear(year);
    }

    @Override
    public List<Book> getBooksByFormat(String format) {
        return xmlParserService.getBooksByFormat(format);
    }

    @Override
    public String executeXPathQuery(String xpathQuery) {
        return xmlParserService.executeXPathQuery(xpathQuery);
    }

    @Override
    public int countTotalBooks() {
        return xmlParserService.countTotalBooks();
    }

    @Override
    public int countBooksByEducationLevel(String code) {
        return xmlParserService.countBooksByEducationLevel(code);
    }
}