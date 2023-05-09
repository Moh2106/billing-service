package ma.enset.billingservice.feign;

import ma.enset.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CUSTOMERS-SERVICE")
public interface CustomerRestClient {

    @GetMapping(path = "/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id);

    @GetMapping(path = "/customers")
    PagedModel<Customer> getListCustomer();
}
