package e_library.service;

import e_library.model.Book;
import e_library.model.ELibrary;
import e_library.model.EducationLevel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import javax.xml.xpath.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XMLParserService {
    
    private static final String XML_FILE = "/elibrary.xml";
    private Document document;
    private XPath xpath;
    private ELibrary eLibrary;

    public XMLParserService() {
        try {
            loadXMLDocument();
            loadELibraryModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadXMLDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = getClass().getResourceAsStream(XML_FILE);
        if (is == null) {
            throw new RuntimeException("Could not find " + XML_FILE);
        }
        document = builder.parse(is);
        
        XPathFactory xPathFactory = XPathFactory.newInstance();
        xpath = xPathFactory.newXPath();
    }

    private void loadELibraryModel() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ELibrary.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InputStream is = getClass().getResourceAsStream(XML_FILE);
        if (is == null) {
            throw new RuntimeException("Could not find " + XML_FILE);
        }
        eLibrary = (ELibrary) unmarshaller.unmarshal(is);
    }

    public ELibrary getELibrary() {
        return eLibrary;
    }

    public List<EducationLevel> getAllEducationLevels() {
        return eLibrary != null ? eLibrary.getEducationLevels() : new ArrayList<EducationLevel>();
    }

    public EducationLevel getEducationLevelByCode(String code) {
        if (eLibrary == null || eLibrary.getEducationLevels() == null) {
            return null;
        }
        for (EducationLevel level : eLibrary.getEducationLevels()) {
            if (level.getCode().equals(code)) {
                return level;
            }
        }
        return null;
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        if (eLibrary != null && eLibrary.getEducationLevels() != null) {
            for (EducationLevel level : eLibrary.getEducationLevels()) {
                if (level.getBooks() != null) {
                    allBooks.addAll(level.getBooks());
                }
            }
        }
        return allBooks;
    }

    public Book getBookById(String bookId) {
        for (Book book : getAllBooks()) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getBooksByEducationLevel(String code) {
        EducationLevel level = getEducationLevelByCode(code);
        return level != null && level.getBooks() != null ? level.getBooks() : new ArrayList<Book>();
    }

    public List<Book> getBooksByLanguage(final String language) {
        return getAllBooks().stream()
                .filter(new java.util.function.Predicate<Book>() {
                    @Override
                    public boolean test(Book book) {
                        return book.getLanguage() != null && 
                               book.getLanguage().equalsIgnoreCase(language);
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByField(final String field) {
        return getAllBooks().stream()
                .filter(new java.util.function.Predicate<Book>() {
                    @Override
                    public boolean test(Book book) {
                        return field != null && field.equals(book.getField());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Book> getBooksBySpecialization(final String specialization) {
        return getAllBooks().stream()
                .filter(new java.util.function.Predicate<Book>() {
                    @Override
                    public boolean test(Book book) {
                        return specialization != null && 
                               specialization.equals(book.getSpecialization());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(final String author) {
        return getAllBooks().stream()
                .filter(new java.util.function.Predicate<Book>() {
                    @Override
                    public boolean test(Book book) {
                        return book.getAuthor() != null && 
                               book.getAuthor().toLowerCase().contains(author.toLowerCase());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Book> getBooksBySubject(final String subject) {
        return getAllBooks().stream()
                .filter(new java.util.function.Predicate<Book>() {
                    @Override
                    public boolean test(Book book) {
                        return book.getSubject() != null && 
                               book.getSubject().toLowerCase().contains(subject.toLowerCase());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByYear(final int year) {
        return getAllBooks().stream()
                .filter(new java.util.function.Predicate<Book>() {
                    @Override
                    public boolean test(Book book) {
                        return book.getPublicationYear() == year;
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByFormat(final String format) {
        return getAllBooks().stream()
                .filter(new java.util.function.Predicate<Book>() {
                    @Override
                    public boolean test(Book book) {
                        return book.getFormat() != null && 
                               book.getFormat().equalsIgnoreCase(format);
                    }
                })
                .collect(Collectors.toList());
    }

    public String executeXPathQuery(String xpathQuery) {
        try {
            if (document == null) {
                return "Error: XML document not loaded";
            }
            XPathExpression expression = xpath.compile(xpathQuery);
            Object result = expression.evaluate(document, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                sb.append(getNodeContent(node)).append("\n");
            }
            return sb.toString().trim();
        } catch (XPathExpressionException e) {
            return "Error executing XPath query: " + e.getMessage();
        }
    }

    private String getNodeContent(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return node.getNodeName() + ": " + node.getTextContent().trim();
        } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            return node.getNodeName() + "=" + node.getNodeValue();
        }
        return node.getTextContent().trim();
    }

    public int countTotalBooks() {
        return getAllBooks().size();
    }

    public int countBooksByEducationLevel(String code) {
        List<Book> books = getBooksByEducationLevel(code);
        return books != null ? books.size() : 0;
    }
}