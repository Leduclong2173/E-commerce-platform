package com.duclong.ecommerce.controller.admin;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.duclong.ecommerce.domain.Category;
import com.duclong.ecommerce.domain.Shop;
import com.duclong.ecommerce.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    
    @GetMapping("/admin/category")
    public String getCategoryPage(Model model) {
        List<Category> categories = this.categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "admin/managecategory/showCategory";
    }

    @GetMapping("/admin/category/search")
    public String getSearchCategory(@RequestParam("keyword") String keyword, Model model) {
        List<Category> categories = this.categoryService.searchByNameOrId(keyword);
        model.addAttribute("categories", categories);
        return "admin/managecategory/showCategory";
    }

    @GetMapping("/admin/category/create")
    public String getCreateCategoryPage(Model model) {
        model.addAttribute("newCategory", new Category());
        return "admin/managecategory/createCategory";
    }

    @PostMapping("/admin/category/create")
    public String postCreateCategory(@Valid @ModelAttribute("newCategory") Category category,
    BindingResult newCategoryBindingResult,
    RedirectAttributes redirectAttributes) {
        
         List<FieldError> errors = newCategoryBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
        System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
        if (this.categoryService.checkNameExist(category.getName())) {
        newCategoryBindingResult.rejectValue("name", "error.name", "Danh mục này đã tồn tại!");
        }
        if(newCategoryBindingResult.hasErrors()){
            return "admin/managecategory/createCategory";
        }


        try {
            this.categoryService.handleSaveCategory(category);
            redirectAttributes.addFlashAttribute("message", "Thêm danh mục mới thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm danh mục mới: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/admin/category";
    }
    
    @GetMapping("/admin/category/delete/{id}")
    public String getDeleteCategoryPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("deleteCategory", new Category());
        return "admin/managecategory/deleteCategory";
    }
    
    @PostMapping("/admin/category/delete")
    public String postDeleteCategory(@ModelAttribute("deleteCategory") Category category, RedirectAttributes redirectAttributes) {
        try {
            this.categoryService.deleteCategoryById(category.getCategory_id());
            redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa danh mục: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/category/update/{id}")
    public String getUpdateCategoryPage(Model model, @PathVariable long id) {
        Category updateCategory = this.categoryService.getCategoryById(id);
        model.addAttribute("updateCategory", updateCategory);
        return "admin/managecategory/updateCategory";
    }

    @PostMapping("/admin/category/update")
    public String postUpdateCategory(@Valid @ModelAttribute("updateCategory") Category category,
    BindingResult updateCategoryBindingResult,
    RedirectAttributes redirectAttributes) {
        
         List<FieldError> errors = updateCategoryBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
        System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
        Category categoryByName = this.categoryService.getCategoryByName(category.getName());
        if (categoryByName != null && !categoryByName.getCategory_id().equals(category.getCategory_id())) {
            updateCategoryBindingResult.rejectValue("name", "error.name", "Danh mục này đã tồn tại!");
        }
        if(updateCategoryBindingResult.hasErrors()){
            return "admin/managecategory/updateCategory";
        }

        try {
            this.categoryService.handleSaveCategory(category);
            redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi cập nhật danh mục mới: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/admin/category";
    }
}
