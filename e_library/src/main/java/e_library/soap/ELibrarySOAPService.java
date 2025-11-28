package e_library.soap;

import e_library.model.Book;
import e_library.model.EducationLevel;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "ELibraryService", targetNamespace = "http://soap.e_library/")
public interface ELibrarySOAPService {

    @WebMethod
    List<EducationLevel> getAllEducationLevels();

    @WebMethod
    EducationLevel getEducationLevelByCode(@WebParam(name = "code") String code);

    @WebMethod
    List<Book> getAllBooks();

    @WebMethod
    Book getBookById(@WebParam(name = "bookId") String bookId);

    @WebMethod
    List<Book> getBooksByEducationLevel(@WebParam(name = "code") String code);

    @WebMethod
    List<Book> getBooksByLanguage(@WebParam(name = "language") String language);

    @WebMethod
    List<Book> getBooksByField(@WebParam(name = "field") String field);

    @WebMethod
    List<Book> getBooksBySpecialization(@WebParam(name = "specialization") String specialization);

    @WebMethod
    List<Book> getBooksByAuthor(@WebParam(name = "author") String author);

    @WebMethod
    List<Book> getBooksBySubject(@WebParam(name = "subject") String subject);

    @WebMethod
    List<Book> getBooksByYear(@WebParam(name = "year") int year);

    @WebMethod
    List<Book> getBooksByFormat(@WebParam(name = "format") String format);

    @WebMethod
    String executeXPathQuery(@WebParam(name = "xpathQuery") String xpathQuery);

    @WebMethod
    int countTotalBooks();

    @WebMethod
    int countBooksByEducationLevel(@WebParam(name = "code") String code);
}