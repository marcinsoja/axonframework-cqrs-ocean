package pl.marcinsoja.cms.ocean.api;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@AllArgsConstructor
public class ObjectId {
    @NotNull
    private final UUID id;
}
