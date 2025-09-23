package com.library.service;

import com.library.dao.interfaces.IPublisherDAO;
import com.library.domain.Publisher;
import com.library.exceptions.EntityNotFoundException;
import com.library.exceptions.InvalidParameterException;

import java.util.List;

public class PublisherService {
    private final IPublisherDAO publisherDAO;

    public PublisherService(IPublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    public Integer createPublisher(Publisher publisher) {
        //? Checagem dos dados enviados
        if (publisher.getName() == null || publisher.getCountry() == null
                || publisher.getName().trim().isEmpty() || publisher.getCountry().trim().isEmpty()) {
            throw new InvalidParameterException("Os campos não podem ser nulos.");
        }

        //! Persistência de dados
        try {
            publisherDAO.savePublisher(publisher);
            return 1;
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Integer updatePublisher(Publisher publisher) {
        //? Checagem dos dados enviados
        if (publisher.getName() == null || publisher.getCountry() == null
                || publisher.getName().trim().isEmpty() || publisher.getCountry().trim().isEmpty()) {
            throw new InvalidParameterException("Os campos não podem ser nulos.");
        }
        try {
            //! Persistência de dados
            publisherDAO.mergePublisher(publisher);
            return 1;
        } catch (EntityNotFoundException | InvalidParameterException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Publisher> findAllPublishers() {
        try {
            return publisherDAO.searchAllPublishers();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Publisher findPublisherById(Long id) {
        try {
            Publisher publisher = publisherDAO.searchPublisherById(id);
            if (publisher == null) {
                throw new EntityNotFoundException("Entidade não encontrada, favor verifique o id.");
            } else {
                return publisher;
            }
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Integer removePublisherById(Long id) {
        try {
            Publisher publisher = publisherDAO.searchPublisherById(id);
            if (publisher == null) {
                throw new EntityNotFoundException("Entidade não encontrada, favor verifique o id.");
            } else {
                publisherDAO.deletePublisherById(id);
                return 1;
            }
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}