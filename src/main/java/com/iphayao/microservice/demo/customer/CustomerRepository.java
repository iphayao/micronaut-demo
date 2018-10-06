package com.iphayao.microservice.demo.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(@NotNull Long id);

    Customer save(Customer customer);

    Optional<Customer> update(@NotNull Long id, @NotBlank Customer customer);

    void deleteById(@NotNull Long id);

    List<Customer> findAll();
}
