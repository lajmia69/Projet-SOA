package e_library.model;

import javax.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookType", propOrder = {
    "title", "subject", "author", "isbn", "publicationYear", "format", "language"
})
public class Book {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "grade")
    private Integer grade;

    @XmlAttribute(name = "specialization")
    private String specialization;

    @XmlAttribute(name = "field")
    private String field;

    @XmlAttribute(name = "type")
    private String type;

    @XmlElement(required = true)
    private String title;

    @XmlElement(required = true)
    private String subject;

    @XmlElement(required = true)
    private String author;

    @XmlElement(required = true)
    private String isbn;

    @XmlElement(required = true)
    private int publicationYear;

    @XmlElement(required = true)
    private String format;

    @XmlElement(required = true)
    private String language;
}