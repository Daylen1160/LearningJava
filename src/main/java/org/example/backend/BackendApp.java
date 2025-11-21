package org.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class BackendApp {

    private final ItemRepository repo;

    public BackendApp(ItemRepository repo) {
        this.repo = repo;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApp.class, args);
    }

    @PostMapping("/add")
    public Item addItem(@RequestBody Item item) {
        return repo.save(item);
    }

    @GetMapping("/all")
    public List<Item> getAll() {
        return repo.findAll();
    }
}
