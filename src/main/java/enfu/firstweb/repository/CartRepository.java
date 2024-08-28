package enfu.firstweb.repository;

import enfu.firstweb.entity.Cart;
import enfu.firstweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
