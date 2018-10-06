package com.iphayao.microservice.demo.customer;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
@Controller("/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Get("/")
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @Get("/{id}")
    public Customer show(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Post("/")
    public HttpResponse<Customer> save(@Body @Valid Customer body) {
        Customer customer = customerRepository.save(body);
        return HttpResponse.created(customer);
    }

    @Put("/{id}")
    public HttpResponse<Customer> update(Long id, @Body @Valid Customer body) {
        Customer customer = customerRepository.update(id, body).get();
        return HttpResponse.ok(customer);
    }

    @Delete("/{id}")
    public HttpResponse delete(Long id) {
        customerRepository.deleteById(id);
        return HttpResponse.ok();
    }
}
