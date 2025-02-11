package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.entity.Product;
import com.app.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping
	public String home() {
		return "home";
	}

	// Show the form to add a new product
	@GetMapping("/new")
	public String showProductForm(Model model) {
		model.addAttribute("product", new Product());
		return "product-form";
	}

	// Save the new product and create sub-products
	@PostMapping("/new")
	public String addProduct(@ModelAttribute Product product) {
		productService.createProductWithSubProducts(product);
		return "redirect:/list"; // Redirect to the list after saving
	}

	// Show the form to edit an existing product
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		Product product = productService.getProduct(id);
		model.addAttribute("product", product); // Load the product to be edited
		return "product-form"; // Render the product-form for editing
	}

	// Update the existing product
	@PostMapping("/edit/{id}")
	public String updateProduct(@PathVariable("id") Long id, @ModelAttribute Product product) {
	    productService.updateProduct(id, product);
	    return "redirect:/list"; // Redirect to updated product list
	}


	// Delete a product
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
		return "redirect:/list"; // Redirect to the product list after deleting
	}

	// Show a paginated list of products
	@GetMapping("/list")
	public String getAllProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Model model) {
		
		Page<Product> productPage = productService.getPaginatedProducts(page, size);
		model.addAttribute("products", productPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", productPage.getTotalPages());

		return "product-list"; // Render the product list page
	}
	
}
