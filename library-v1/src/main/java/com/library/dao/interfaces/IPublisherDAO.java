package com.library.dao.interfaces;

import com.library.domain.Publisher;

import java.util.List;

public interface IPublisherDAO {
    void savePublisher(Publisher publisher);

    void mergePublisher(Publisher publisher);

    public List<Publisher> searchAllPublishers();

    public Publisher searchPublisherById(Long id);

    void deletePublisherById(Long id);
}