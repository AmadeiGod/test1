package doc.ex.test.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DocTest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String contentType;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;
    public DocTest(){

    }

}
