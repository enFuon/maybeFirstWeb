package enfu.firstweb.service;

import enfu.firstweb.entity.Cart;
import enfu.firstweb.entity.CartItem;
import enfu.firstweb.entity.Product;
import enfu.firstweb.entity.User;
import enfu.firstweb.repository.CartItemRepository;
import enfu.firstweb.repository.CartRepository;
import enfu.firstweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public void addProductToCart(User user, Product product) {
        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }

        CartItem cartItem = new CartItem(product, 1);
        cart.addItem(cartItem);

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void deleteAllCarts() {
        cartRepository.deleteAll();
    }

    @Override
    public void deleteAllCartItems() {
        cartItemRepository.deleteAll();
    }
}
