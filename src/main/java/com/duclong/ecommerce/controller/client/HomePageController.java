package com.duclong.ecommerce.controller.client;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.duclong.ecommerce.domain.Cart;
import com.duclong.ecommerce.domain.CartItem;
import com.duclong.ecommerce.domain.Category;
import com.duclong.ecommerce.domain.Order;
import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.Shop;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class HomePageController {

    private final CartService cartService;
    private final ProductServices productServices;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UploadService uploadService;
    private final ShopService shopService;
    private final OrderService orderService;

    public HomePageController(
        ProductServices productServices,
        UserService userService,
        CategoryService categoryService,
        UploadService uploadService,
        ShopService shopService,
        CartService cartService,
        OrderService orderService){
        this.productServices = productServices;
        this.userService = userService;
        this.categoryService = categoryService;
        this.uploadService = uploadService;
        this.shopService = shopService;
        this.cartService = cartService;
        this.orderService = orderService;
    }
    
    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request) {
        // Lấy session
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User currentUser = null;
        if (session != null) {
            currentUser = this.userService.getUserById(id);
        }

        // Lấy danh sách tất cả các shop
        List<Shop> allShops = this.shopService.getAllShop();
        List<Shop> filteredShops = new ArrayList<>();

        // Lọc shop bằng for-each
        if (currentUser == null) {
            // Nếu không có người dùng đăng nhập, hiển thị tất cả shop
            filteredShops.addAll(allShops);
        } else {
            Long currentUserId = currentUser.getUser_id();
            for (Shop shop : allShops) {
                // Chỉ thêm shop nếu không thuộc về người dùng hiện tại
                if (shop.getUser() == null || !shop.getUser().getUser_id().equals(currentUserId)) {
                    filteredShops.add(shop);
                }
            }
        }

        // Thêm danh sách shop đã lọc vào model
        model.addAttribute("shops", filteredShops);
        return "client/homepage/showHomePage";
    }

    @ModelAttribute
    public void addCategoriesToModel(Model model) {
        List<Category> categories = this.categoryService.getAllCategory();
        model.addAttribute("categories", categories);
    }

    @GetMapping("/infor")
    public String getInformationPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        model.addAttribute("inforUser", user);
        return "client/homepage/information/showInformation";
    }

    @PostMapping("/infor/update")
    public String postUpdateInformationUser(
        @Valid @ModelAttribute("inforUser") User user,
        BindingResult updateUserBindingResult,
        @RequestParam("avatarFile") MultipartFile file
        ) {
        
        List<FieldError> errors = updateUserBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
        System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
         User userByUsername = this.userService.getUserByUsername(user.getUsername());
        if (userByUsername != null && userByUsername.getUser_id() != userByUsername.getUser_id()) {
            updateUserBindingResult.rejectValue("email", "error.email", "Email đã tồn tại!");
        }

        User userByEmail = this.userService.getUserByEmail(user.getEmail());
        if (userByEmail != null && userByEmail.getUser_id() != userByEmail.getUser_id()) {
        updateUserBindingResult.rejectValue("email", "error.email", "Email đã tồn tại!");
        }

        if(updateUserBindingResult.hasErrors()){
            return "client/homepage/information/showInformation";
        }

        String avatarName = this.uploadService.handleUploadFile(file, "avatar");

        user.setAvatar(avatarName);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));

        this.userService.handleSaveUser(user);
        return "redirect:/infor";
    }

    @GetMapping("/homepage/product/{id}")
    public String getProductPage(@PathVariable Long id, Model model) {
        Product product = this.productServices.getProductById(id);
        Shop shop = product.getShop();
        List<Product> products = shop.getProducts();
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "client/homepage/product/showProduct";
    }
    
    @GetMapping("/homepage/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<Product> products = this.productServices.searchProducts(keyword);
        List<Shop> shops = this.shopService.searchShops(keyword);
        model.addAttribute("products", products);
        model.addAttribute("shops", shops);
        model.addAttribute("keyword", keyword);
        return "client/homepage/product/searchResult";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String postAddProductToCart(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Long productId = id;
        String username = (String) session.getAttribute("username");

        this.cartService.handleAddProductToCart(username, productId, session);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        currentUser.setUser_id(id);

        // Get cart by user
        Cart cart = this.cartService.fetchByUser(currentUser);

        List<CartItem> cartItems = cart == null ? new ArrayList<CartItem>() : cart.getCartItems();

        // Total price
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cart", cart);

        return "client/cart/showCart";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String postDeleteCart(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long cartItemId = id;
        this.cartService.handleRemoveCartItem(cartItemId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("user_id");
        currentUser.setUser_id(id);

        Cart cart = this.cartService.fetchByUser(currentUser);

        List<CartItem> cartItems = cart == null ? new ArrayList<CartItem>() : cart.getCartItems();
        for (CartItem cartItem : cartItems) {
        long quantity = cartItem.getQuantity();
        long stock = cartItem.getProduct().getStock();
        if (quantity > stock) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Sản phẩm " + cartItem.getProduct().getName() + 
                " chỉ còn " + stock + " trong kho, bạn yêu cầu " + quantity + ".");
            return "redirect:/cart";
        }
    }
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartItem> cartItems = cart == null ? new ArrayList<CartItem>() : cart.getCartItems();
        this.cartService.handleUpdateCartBeforeCheckout(cartItems);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
        HttpServletRequest request,
        @RequestParam("receiverName") String receiverName,
        @RequestParam("receiverAddress") String receiverAddress,
        @RequestParam("receiverPhone") String receiverPhone) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("user_id");
        currentUser.setUser_id(id);
        
        this.orderService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage(Model model) {

        return "client/cart/thanks";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("user_id");
        currentUser.setUser_id(id);

        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);

        return "client/cart/order-history"; 
    }


    @GetMapping("/homepage/category/{id}")
    public String getCategoryProducts(@PathVariable Long id, Model model) {
    Category category = this.categoryService.getCategoryById(id);
    List<Product> products = category.getProducts();
    model.addAttribute("products", products);
    model.addAttribute("category", category);
    return "client/homepage/category/categoryProducts";
}
}
