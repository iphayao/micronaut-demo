package com.iphayao.microservice.demo.customer;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton
public class CustomerRepositoryImpl implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationConfiguration applicationConfiguration;

    public CustomerRepositoryImpl(@CurrentSession EntityManager entityManager,
                                  ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Customer.class, id));
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    @Override
    @Transactional
    public Optional<Customer> update(@NotNull Long id, @NotBlank Customer customer) {
        String sqlString = "UPDATE Customer c SET firstName = :firstname, lastName = :lastname, age = :age, email = :email WHERE id = :id";
        int effected = entityManager.createQuery(sqlString)
                .setParameter("firstname", customer.getFirstName())
                .setParameter("lastname", customer.getLastName())
                .setParameter("age", customer.getAge())
                .setParameter("email", customer.getEmail())
                .setParameter("id", id)
                .executeUpdate();
        if(effected == 1) {
            return findById(id);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(c -> entityManager.remove(c));
    }

    @Override
    @Transactional
    public List<Customer> findAll() {
        String sqlString = "SELECT c FROM Customer as c";
        TypedQuery<Customer> query = entityManager.createQuery(sqlString, Customer.class);
        return query.getResultList();
    }

}
