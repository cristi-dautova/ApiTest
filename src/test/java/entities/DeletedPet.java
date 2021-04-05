package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString()
@AllArgsConstructor
public class DeletedPet {
    public int code;
    public String type;
    public String message;

    public DeletedPet() {
    }
}
