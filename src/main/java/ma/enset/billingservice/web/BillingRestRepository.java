package ma.enset.billingservice.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.enset.billingservice.entities.Bill;
import ma.enset.billingservice.feign.CustomerRestClient;
import ma.enset.billingservice.feign.ProductRestClient;
import ma.enset.billingservice.model.Customer;
import ma.enset.billingservice.model.Product;
import ma.enset.billingservice.repositories.BillRepository;
import ma.enset.billingservice.repositories.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@NoArgsConstructor
public class BillingRestRepository {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CustomerRestClient customerRestClient;
    @Autowired
    private ProductRestClient productRestClient;

    public BillingRestRepository(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping(path = "/fullBill")
    public List<Bill> fullBill(){
        return billRepository.findAll();
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill findById(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(id);
        bill.setCustomer(customer);

        bill.getProductItems().forEach(productItem -> {
            Product product = productRestClient.getProductById(productItem.getProductID());
            productItem.setProduct(product);
        });
        return bill;
    }
}
