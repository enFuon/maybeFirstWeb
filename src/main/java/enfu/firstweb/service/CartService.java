package enfu.firstweb.service;

import enfu.firstweb.entity.Cart;
import enfu.firstweb.entity.Product;
import enfu.firstweb.entity.User;

public interface CartService {
    Cart getCartByUser(User user);
    void addProductToCart(User user, Product product);

    void deleteAllCarts();
    void deleteAllCartItems();
}
