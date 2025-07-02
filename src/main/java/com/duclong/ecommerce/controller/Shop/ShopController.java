package com.duclong.ecommerce.controller.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.duclong.ecommerce.domain.Category;
import com.duclong.ecommerce.domain.Order;
import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.Shop;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.CategoryService;
import com.duclong.ecommerce.service.OrderService;
import com.duclong.ecommerce.service.ProductServices;
import com.duclong.ecommerce.service.ShopService;
import com.duclong.ecommerce.service.UploadService;
import com.duclong.ecommerce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ShopController {

    private final ShopService shopService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UploadService uploadService;
    private final ProductServices productServices;
    private final OrderService orderService;

    public ShopController(
        ShopService shopService, 
        UserService userService,
        CategoryService categoryService,
        UploadService uploadService,
        ProductServices productServices,
        OrderService orderService){
        this.shopService = shopService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.uploadService = uploadService;
        this.productServices = productServices;
        this.orderService = orderService;
    }
    
    @GetMapping("/shop/register")
    public String getCreateShop(Model model) {
        model.addAttribute("newShop", new Shop());
        return "client/homepage/register/registerShop";
    }

    @PostMapping("/shop/register")
    public String postCreateCategory(@Valid @ModelAttribute("newShop") Shop shop,
    BindingResult newShopBindingResult,
    RedirectAttributes redirectAttributes,
    HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        
        List<FieldError> errors = newShopBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
        System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
        if (this.shopService.checkNameExist(shop.getName())) {
        newShopBindingResult.rejectValue("name", "error.name", "Tên cửa hàng đã tồn tại!");
        }
        if(newShopBindingResult.hasErrors()){
            return "client/homepage/register/registerShop";
        }
        try {
            shop.setUser(user);
            this.userService.updateUserRole(id);
            this.shopService.handleSaveShop(shop);
            redirectAttributes.addFlashAttribute("message", "Đăng kí thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Đăng ký thất bại: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        session.setAttribute("role_id", user.getRole().getRole_id());
        return "redirect:/shop";
    }

    @GetMapping("/shop")
    public String getShopPage(Model model, HttpServletRequest request ) {
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        Shop shop = user.getShop();
        // Lấy danh sách sản phẩm của shop
        List<Product> products = this.shopService.getAllProduct(shop);
        double totalRevenue = 0.0;
        
        // Tính tổng doanh thu từ các đơn hàng liên quan đến sản phẩm của shop
        for (Product product : products) {
            List<Order> orders = this.orderService.getOrdersByProduct(product);
            for (Order order : orders) {
                totalRevenue += order.getTotalPrice();
            }
        }
        
        double netRevenue = totalRevenue * 0.8; // Doanh thu thực nhận (80%)
        
        model.addAttribute("shop", shop);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("netRevenue", netRevenue);
        return "client/shop/showShop";
    }

    @GetMapping("/shop/inforshop")
    public String getInformationShopPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        Shop shop = user.getShop();
        model.addAttribute("inforShop", shop);
        return "client/shop/information/showInformation";
    }

    @PostMapping("/shop/inforshop/update")
    public String postUpdateShop(@Valid @ModelAttribute("inforShop") Shop shop,
    BindingResult updateShopBindingResult,
    RedirectAttributes redirectAttributes,
    HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        
         List<FieldError> errors = updateShopBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
        System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
        Shop shopByName = this.shopService.getShopByName(shop.getName());
        if (shopByName != null && !shopByName.getShop_id().equals(shop.getShop_id())) {
            updateShopBindingResult.rejectValue("name", "error.name", "Tên cửa hàng này đã tồn tại!");
        }
        if(updateShopBindingResult.hasErrors()){
            return "admin/managecategory/updateCategory";
        }

        try {
            shop.setUser(user);
            this.shopService.handleSaveShop(shop);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi cập nhật thông tin mới: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/shop/inforshop";
    }

    @GetMapping("/shop/product")
    public String getProductPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        Shop shop = user.getShop();
        List<Product> products = this.shopService.getAllProduct(shop);
        model.addAttribute("products", products);
        return "client/shop/product/showProduct";
    }

    @GetMapping("/shop/product/create")
    public String getCreateProductPage(Model model) {
        List<Category> categories = this.categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("newProduct", new Product());
        return "client/shop/product/createProduct";
    }

    @PostMapping("/shop/product/create")
    public String postCreateProduct(
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("productFile") MultipartFile file,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
            ) {
        // Validate
        if (newProductBindingResult.hasErrors()) {
            return "client/shop/product/createProduct";
        }
        // Upload image
        String image = this.uploadService.handleUploadFile(file, "product");
        product.setImage(image);

        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        Shop shop = user.getShop();
        product.setShop(shop);
        product.setCategory(this.categoryService.getCategoryByName(product.getCategory().getName()));
        
        try {
            this.productServices.handleSaveProduct(product);
            redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm sản phẩm: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/shop/product";
    }

    @GetMapping("/shop/product/search")
    public String getsearchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = this.productServices.searchByNameOrId(keyword);
        model.addAttribute("products", products);
        return "client/shop/product/showProduct";
    }

    @GetMapping("/shop/product/{id}")
    public String getDetailProductPage(Model model, @PathVariable long id) {
        Product product = this.productServices.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "client/shop/product/detailProduct";
    }

    @GetMapping("/shop/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "client/shop/product/deleteProduct";
    }
    
    @PostMapping("/shop/product/delete")
    public String postDeleteProduct(@ModelAttribute("newProduct") Product product) {
        this.productServices.deleteproductById(product.getProduct_id());
        return "redirect:/shop/product";
    }

    @GetMapping("/shop/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        List<Category> categories = this.categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        Product updateProduct = this.productServices.getProductById(id);
        model.addAttribute("updateProduct", updateProduct);
        return "client/shop/product/updateProduct";
    }

    @PostMapping("/shop/product/update")
    public String postUpdateProduct(
            @ModelAttribute("updateProduct") @Valid Product product,
            BindingResult updateProductBindingResult,
            @RequestParam("productFile") MultipartFile file,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
            ) {
        // Validate
        if (updateProductBindingResult.hasErrors()) {
            return "client/shop/product/updateProduct";
        }
        // Upload image
        String image = this.uploadService.handleUploadFile(file, "product");
        product.setImage(image);

        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        Shop shop = user.getShop();
        product.setShop(shop);
        product.setCategory(this.categoryService.getCategoryByName(product.getCategory().getName()));

        try {
            this.productServices.handleSaveProduct(product);
            redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi cập nhật thông tin sản phẩm: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/shop/product";
    }

    @GetMapping("/shop/order")
    public String getDashboard(Model model) {
        List<Order> orders = this.orderService.getAllOrder();
        model.addAttribute("orders", orders);
        return "client/shop/order/showOrder";
    }

    @GetMapping("/shop/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        Order order = this.orderService.fetchOrderById(id).get();
        model.addAttribute("order", order);
        model.addAttribute("id", id);
        model.addAttribute("orderItems", order.getOrderItems());
        return "client/shop/order/detailOrder";
    }

    // @GetMapping("/shop/order/delete/{id}")
    // public String getDeleteOrderPage(Model model, @PathVariable long id) {
    //     model.addAttribute("id", id);
    //     model.addAttribute("deleteOrder", new Order());
    //     return "client/shop/order/deleteOrder";
    // }

    // @PostMapping("/shop/order/delete")
    // public String postDeleteOrder(@ModelAttribute("deleteOrder") Order order) {
    //     this.orderService.deleteOrderById(order.getOrder_id());
    //     return "redirect:/shop/order";
    // }

    @GetMapping("/shop/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable long id) {
        Optional<Order> currentOrder = this.orderService.fetchOrderById(id);
        model.addAttribute("newOrder", currentOrder.get());
        return "client/shop/order/updateOrder";
    }

    @PostMapping("/shop/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order, RedirectAttributes redirectAttributes) {  
        try {
            this.orderService.updateOrder(order);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin đơn hàng thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi cập nhật thông tin đơn hàng: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/shop/order";
    }

}

