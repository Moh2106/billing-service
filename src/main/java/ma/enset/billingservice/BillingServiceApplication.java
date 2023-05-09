package ma.enset.billingservice;

import ma.enset.billingservice.entities.Bill;
import ma.enset.billingservice.entities.ProductItem;
import ma.enset.billingservice.feign.CustomerRestClient;
import ma.enset.billingservice.feign.ProductRestClient;
import ma.enset.billingservice.model.Customer;
import ma.enset.billingservice.model.Product;
import ma.enset.billingservice.repositories.BillRepository;
import ma.enset.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerRestClient,
            ProductRestClient productRestClient){
        return args -> {
            Customer customer = customerRestClient.getCustomerById(1L);
            Bill bill = new Bill(null, new Date(), null, customer.getId(), customer);
            billRepository.save(bill);

            PagedModel<Product> productItems = productRestClient.listProduct();

            productItems.forEach(product -> {
                ProductItem productItem = new ProductItem();
                productItem.setBill(bill);
                productItem.setId(null);
                productItem.setPrice(product.getPrice());
                productItem.setQuantity(product.getQuantity());
                productItem.setProductID(product.getId());

                productItemRepository.save(productItem);
            });


        };
    }

}
