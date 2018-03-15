package pl.marcinsoja.cms.ocean.core.type;

import lombok.Data;

@Data
public class AttributeHolder {
    private Attribute attribute;
    private int order;
    private boolean required;
}
