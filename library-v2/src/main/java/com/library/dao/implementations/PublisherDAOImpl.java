package com.library.dao.implementations;

import com.library.dao.interfaces.IPublisherDAO;
import com.library.domain.Book;
import com.library.domain.Publisher;
import com.library.exceptions.InvalidParameterException;
import jakarta.persistence.*;

import java.util.List;

public class PublisherDAOImpl implements IPublisherDAO {
    private final EntityManager entityManager;

    public PublisherDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void savePublisher(Publisher publisher) {
        entityManager.persist(publisher);
    }

    @Override
    public void mergePublisher(Publisher publisher) {
        entityManager.merge(publisher);
    }

    @Override
    public List<Publisher> searchAllPublishers() {
        String sql_search = "SELECT p FROM Publisher p";
        TypedQuery<Publisher> query = entityManager.createQuery(sql_search, Publisher.class);
        return query.getResultList();
    }

    @Override
    public Publisher searchPublisherById(Long id) {
        return entityManager.find(Publisher.class, id);
    }

    @Override
    public void deletePublisherById(Long id) {
        Publisher publisher = this.searchPublisherById(id);
        entityManager.remove(publisher);
    }
}