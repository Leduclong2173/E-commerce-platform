package com.duclong.ecommerce.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.duclong.ecommerce.domain.Order;
import com.duclong.ecommerce.domain.OrderItem;
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

@Controller
public class ShopAdminController {

    private final UserService userService;
    private final ShopService shopService;
    private final ProductServices productServices;
    private final OrderService orderService;

    public ShopAdminController(
        UserService userService,
        ShopService shopService,
        ProductServices productServices,
        OrderService orderService
    ){
        this.userService = userService;
        this.shopService = shopService;
        this.productServices = productServices;
        this.orderService = orderService;
    }
    
    @GetMapping("/admin/shop")
    public String getShopPage(Model model) {
        List<Shop> shops = this.shopService.getAllShop();
        model.addAttribute("shops", shops);
        return "admin/manageshop/showShop";
    }

    @GetMapping("/admin/shop/search")
    public String getSearchShop(@RequestParam("keyword") String keyword, Model model) {
        List<Shop> shops = this.shopService.searchByNameOrId(keyword);
        model.addAttribute("shops", shops);
        return "admin/manageshop/showShop";
    }

    @GetMapping("/admin/shop/manageproduct/{id}")
    public String getProductInShop(Model model, @PathVariable Long id) {
        Optional<Shop> shopOptional = this.shopService.getShopById(id);
        if(shopOptional.isPresent()){
            Shop shop = shopOptional.get();
            List<Product> products = shop.getProducts();
            model.addAttribute("products", products);
        }
        return "admin/manageshop/showProductInShop";
    }

    @GetMapping("/admin/shop/manageproduct/product/{id}")
    public String getDetailProductInShop(Model model, @PathVariable Long id) {
        Product product = this.productServices.getProductById(id);
        model.addAttribute("product", product);
        return "admin/manageshop/showDetailProductInShop";
    }

    // @GetMapping("/admin/shop/manageproduct/{id}/search")
    // public String getsearchProductInShop(@RequestParam("keyword") String keyword, Model model, @PathVariable Long id) {
    //     Optional<Shop> shopOptional = this.shopService.getShopById(id);
    //     List<Product> products = new ArrayList<>();
    //     if(shopOptional.isPresent()){
    //         Shop shop = shopOptional.get();
    //         model.addAttribute("shop", shop);
    //         if(keyword != null){
    //             products = this.productServices.searchByNameOrId(keyword);
    //         } else {
    //             products = shop.getProducts();
    //         }
    //     }
    //     model.addAttribute("products", products);
    //     model.addAttribute("keyword", keyword);
    //     return "client/shop/product/showProduct";
    // }

    @GetMapping("/admin/shop/manageorder/{id}")
    public String getOrderOfShop(Model model, @PathVariable Long id) {
        Optional<Shop> shopOptional = this.shopService.getShopById(id);
        List<Order> orderOfShop = new ArrayList<Order>();
        if(shopOptional.isPresent()){
            Shop shop = shopOptional.get();
            List<Order> orders = this.orderService.getAllOrder();
            for (Order order : orders) {
                List<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem orderItem : orderItems) {
                    if(orderItem.getProduct().getShop().getShop_id().equals(id)){
                        orderOfShop.add(order);
                        break;
                    }
                }
            }
        model.addAttribute("shop", shop);
        model.addAttribute("orders", orderOfShop);
        }
        
        return "admin/manageshop/showOrderofShop";
    }

    

}
