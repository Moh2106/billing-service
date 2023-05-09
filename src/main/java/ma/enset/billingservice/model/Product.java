package ma.enset.billingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
