package e_library.model;

import javax.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EducationLevelType")
public class EducationLevel {

    @XmlAttribute(name = "id", required = true)
    @JsonProperty("id")
    private String id;

    @XmlAttribute(name = "code", required = true)
    @JsonProperty("code")
    private String code;

    @XmlElement(name = "n", required = true)
    @JsonProperty("name")
    private String name;

    @XmlElement(required = true)
    @JsonProperty("duration")
    private String duration;

    @XmlElementWrapper(name = "grades")
    @XmlElement(name = "grade")
    @JsonProperty("grades")
    private List<Grade> grades;

    @XmlElementWrapper(name = "specializations")
    @XmlElement(name = "specialization")
    @JsonProperty("specializations")
    private List<Specialization> specializations;

    @XmlElementWrapper(name = "types")
    @XmlElement(name = "type")
    @JsonProperty("types")
    private List<Type> types;

    @XmlElementWrapper(name = "fields")
    @XmlElement(name = "field")
    @JsonProperty("fields")
    private List<Field> fields;

    @XmlElementWrapper(name = "books", required = true)
    @XmlElement(name = "book")
    @JsonProperty("books")
    private List<Book> books;

    // Inner classes for nested elements
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Grade {
        @XmlAttribute(name = "number", required = true)
        @JsonProperty("number")
        private int number;
        
        @XmlValue
        @JsonProperty("value")
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Specialization {
        @XmlAttribute(name = "code", required = true)
        @JsonProperty("code")
        private String code;
        
        @XmlValue
        @JsonProperty("value")
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Type {
        @XmlAttribute(name = "code", required = true)
        @JsonProperty("code")
        private String code;
        
        @XmlValue
        @JsonProperty("value")
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Field {
        @XmlAttribute(name = "code", required = true)
        @JsonProperty("code")
        private String code;
        
        @XmlValue
        @JsonProperty("value")
        private String value;
    }
}