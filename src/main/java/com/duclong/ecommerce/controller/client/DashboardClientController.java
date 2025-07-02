package com.duclong.ecommerce.controller.client;


import java.util.List;

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

import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.ProductServices;
import com.duclong.ecommerce.service.UploadService;
import com.duclong.ecommerce.service.UserService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class DashboardClientController {

    // private final UserService userService;
    // private final UploadService uploadService;
    // private final ProductServices productServices;

    // public DashboardClientController (
    //     UserService userService,
    //     UploadService uploadService,
    //     ProductServices productServices
    // ){
    //     this.userService = userService;
    //     this.uploadService = uploadService;
    //     this.productServices = productServices;
    // }

    // @GetMapping("/client")
    // public String getDashboardPage(Model model, HttpServletRequest request ) {
    //     return "client/dashboard/showDashboard";
    // }

    // @GetMapping("/client/information")
    // public String getIformationUserPage(Model model, HttpServletRequest request) {
    //     HttpSession session = request.getSession(false);
    //     Long id = (Long) session.getAttribute("user_id");
    //     User user = this.userService.getUserById(id);
    //     model.addAttribute("inforUser", user);
    //     return "client/dashboard/information/showInformation";
    // }

    // @PostMapping("/client/information/update")
    // public String postUpdateInformationUser(
    //     @Valid @ModelAttribute("inforUser") User user,
    //     BindingResult updateUserBindingResult,
    //     @RequestParam("avatarFile") MultipartFile file
    //     ) {
        
    //     List<FieldError> errors = updateUserBindingResult.getFieldErrors();
    //     for (FieldError error : errors ) {
    //     System.out.println (error.getField() + " - " + error.getDefaultMessage());
    //     }

    //     // Validate
    //      User userByUsername = this.userService.getUserByUsername(user.getUsername());
    //     if (userByUsername != null && userByUsername.getUser_id() != userByUsername.getUser_id()) {
    //         updateUserBindingResult.rejectValue("email", "error.email", "Email đã tồn tại!");
    //     }

    //     User userByEmail = this.userService.getUserByEmail(user.getEmail());
    //     if (userByEmail != null && userByEmail.getUser_id() != userByEmail.getUser_id()) {
    //     updateUserBindingResult.rejectValue("email", "error.email", "Email đã tồn tại!");
    //     }

    //     if(updateUserBindingResult.hasErrors()){
    //         return "client/dashboard/information/showInformation";
    //     }

    //     String avatarName = this.uploadService.handleUploadFile(file, "avatar");

    //     user.setAvatar(avatarName);
    //     user.setRole(this.userService.getRoleByName(user.getRole().getName()));

    //     this.userService.handleSaveUser(user);
    //     return "redirect:/client/information";
    // }

    // @GetMapping("/client/product/create")
    // public String getCreateProductPage(Model model) {
    //     model.addAttribute("newProduct", new Product());
    //     return "client/dashboard/product/createProduct";
    // }

    // @PostMapping("/client/product/create")
    // public String postCreateProduct(
    //         @ModelAttribute("newProduct") @Valid Product product,
    //         BindingResult newProductBindingResult,
    //         @RequestParam("productFile") MultipartFile file,
    //         HttpServletRequest request
    //         ) {
    //     // Validate
    //     if (newProductBindingResult.hasErrors()) {
    //         return "client/dashboard/product/createProduct";
    //     }
    //     // Upload image
    //     String image = this.uploadService.handleUploadFile(file, "product");
    //     product.setImage(image);

    //     HttpSession session = request.getSession(false);
    //     Long id = (Long) session.getAttribute("user_id");
    //     User user = this.userService.getUserById(id);
    //     product.setUser(user);
    //     this.productServices.handleSaveProduct(product);

    //     return "redirect:/client/product";
    // }
    
    // @GetMapping("/client/product")
    // public String getProductPage(Model model, HttpServletRequest request) {
    //     HttpSession session = request.getSession(false);
    //     Long id = (Long) session.getAttribute("user_id");
    //     User user = this.userService.getUserById(id);
    //     List<Product> products = this.productServices.getAllProduct(user);
    //     model.addAttribute("products", products);
    //     return "client/dashboard/product/showProduct";
    // }

    // @GetMapping("/client/product/search")
    // public String getsearchProduct(@RequestParam("keyword") String keyword, Model model) {
    //     List<Product> products = this.productServices.searchByNameOrId(keyword);
    //     model.addAttribute("products", products);
    //     return "client/dashboard/product/showProduct";
    // }

    // @GetMapping("/client/product/{id}")
    // public String getDetailProductPage(Model model, @PathVariable long id) {
    //     Product product = this.productServices.getProductById(id);
    //     model.addAttribute("product", product);
    //     model.addAttribute("id", id);
    //     return "client/dashboard/product/detailProduct";
    // }

    // @GetMapping("/client/product/delete/{id}")
    // public String getDeleteProductPage(Model model, @PathVariable long id) {
    //     model.addAttribute("id", id);
    //     model.addAttribute("newProduct", new Product());
    //     return "client/dashboard/product/deleteProduct";
    // }
    
    // @PostMapping("/client/product/delete")
    // public String postDeleteProduct(@ModelAttribute("newProduct") Product product) {
    //     this.productServices.deleteproductById(product.getProduct_id());
    //     return "redirect:/client/product";
    // }

    // @GetMapping("/client/product/update/{id}")
    // public String getUpdateProductPage(Model model, @PathVariable long id) {
    //     Product updateProduct = this.productServices.getProductById(id);
    //     model.addAttribute("updateProduct", updateProduct);
    //     return "client/dashboard/product/updateProduct";
    // }

    // @PostMapping("/client/product/update")
    // public String postUpdateProduct(
    //         @ModelAttribute("updateProduct") @Valid Product product,
    //         BindingResult updateProductBindingResult,
    //         @RequestParam("productFile") MultipartFile file,
    //         HttpServletRequest request
    //         ) {
    //     // Validate
    //     if (updateProductBindingResult.hasErrors()) {
    //         return "client/dashboard/product/createProduct";
    //     }
    //     // Upload image
    //     String image = this.uploadService.handleUploadFile(file, "product");
    //     product.setImage(image);

    //     HttpSession session = request.getSession(false);
    //     Long id = (Long) session.getAttribute("user_id");
    //     User user = this.userService.getUserById(id);
    //     product.setUser(user);
    //     this.productServices.handleSaveProduct(product);

    //     return "redirect:/client/product";
    // }
}
