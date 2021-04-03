package com.books.books.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPagesController {

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("keywords", "list of books");
        return "list";
    }
}