package pl.thisismyway.updateobjectinlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class BookOverallData {
    private Long idOfBook;
    private String title;
    private String authour;
    private BigDecimal basePrice;
    private Integer discountRate;
}
