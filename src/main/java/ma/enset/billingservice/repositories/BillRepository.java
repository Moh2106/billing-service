package ma.enset.billingservice.repositories;

import ma.enset.billingservice.entities.Bill;
import ma.enset.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Collection;

@RepositoryRestController
public interface BillRepository extends JpaRepository<Bill, Long> {
    //Collection<ProductItem> findById(Long id);
}
