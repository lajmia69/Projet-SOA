package e_library.model;

import javax.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EducationLevelType")
public class EducationLevel {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "code", required = true)
    private String code;

    @XmlElement(name = "n", required = true)
    private String name;

    @XmlElement(required = true)
    private String duration;

    @XmlElementWrapper(name = "grades")
    @XmlElement(name = "grade")
    private List<Grade> grades;

    @XmlElementWrapper(name = "specializations")
    @XmlElement(name = "specialization")
    private List<Specialization> specializations;

    @XmlElementWrapper(name = "types")
    @XmlElement(name = "type")
    private List<Type> types;

    @XmlElementWrapper(name = "fields")
    @XmlElement(name = "field")
    private List<Field> fields;

    @XmlElementWrapper(name = "books", required = true)
    @XmlElement(name = "book")
    private List<Book> books;

    // Inner classes for nested elements
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Grade {
        @XmlAttribute(name = "number", required = true)
        private int number;
        
        @XmlValue
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Specialization {
        @XmlAttribute(name = "code", required = true)
        private String code;
        
        @XmlValue
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Type {
        @XmlAttribute(name = "code", required = true)
        private String code;
        
        @XmlValue
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Field {
        @XmlAttribute(name = "code", required = true)
        private String code;
        
        @XmlValue
        private String value;
    }
}