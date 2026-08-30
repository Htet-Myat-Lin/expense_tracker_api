package com.example.expense_tracker.seeder;

import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.enums.TransactionType;
import com.example.expense_tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategorySeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        List<Category> categories = List.of(
                Category.builder().name("Food").type(TransactionType.EXPENSE).build(),
                Category.builder().name("Transport").type(TransactionType.EXPENSE).build(),
                Category.builder().name("Entertainment").type(TransactionType.EXPENSE).build(),
                Category.builder().name("Health").type(TransactionType.EXPENSE).build(),
                Category.builder().name("Shopping").type(TransactionType.EXPENSE).build(),
                Category.builder().name("Utilities").type(TransactionType.EXPENSE).build(),

                Category.builder().name("Salary").type(TransactionType.INCOME).build(),
                Category.builder().name("Freelance").type(TransactionType.INCOME).build(),
                Category.builder().name("Investment").type(TransactionType.INCOME).build(),
                Category.builder().name("Pocket Money").type(TransactionType.INCOME).build(),

                Category.builder().name("Others").build()
        );

        for (Category category : categories) {
            if (!categoryRepository.existsByName(category.getName())) {
                categoryRepository.save(category);
            }
        }
    }
}
