package pl.marcinsoja.cms.ocean.core.type;

import lombok.Data;

import java.util.UUID;

@Data
public class Attribute {
    private UUID id;
    private String name;
    private String title;
    private String description;
    private AttributeType type;
}
