package e_library.model;

import javax.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "eLibrary")
@XmlAccessorType(XmlAccessType.FIELD)
public class ELibrary {

    @XmlElement(name = "educationLevel", required = true)
    private List<EducationLevel> educationLevels;
}