package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Range;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RangeDTO {
    private Range range;
    private int quantity;

}
