package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Range;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class RangeService {
    private List<Range> range;

    public RangeService() {
        range = new ArrayList<>();
        range.add(new Range(1,3));
        range.add(new Range(4,6));
        range.add(new Range(7,9));
        range.add(new Range(10,12));
        range.add(new Range(13,15));
    }
}
