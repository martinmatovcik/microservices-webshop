package com.mm.learninginventoryservice;

import com.mm.learninginventoryservice.model.Inventory;
import com.mm.learninginventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class LearningInventoryServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LearningInventoryServiceApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
    return args -> {
      Inventory inventory = new Inventory("iphone_15", 100);
      Inventory inventory1 = new Inventory("iphone_15_red", 0);

      inventoryRepository.save(inventory);
      inventoryRepository.save(inventory1);
    };
  }
}
