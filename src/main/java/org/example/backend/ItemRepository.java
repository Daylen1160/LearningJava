package org.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;

// This interface automatically writes SQL for you!
public interface ItemRepository extends JpaRepository<Item, Long> {
}