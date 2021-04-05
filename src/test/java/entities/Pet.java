package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString()
@AllArgsConstructor
public class Pet {
    private String id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private Status status;

    public Pet() {
    }
}
