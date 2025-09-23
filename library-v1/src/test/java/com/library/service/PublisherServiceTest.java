package com.library.service;

import com.library.dao.implementations.PublisherDAOImpl;
import com.library.dao.interfaces.IPublisherDAO;
import com.library.domain.Publisher;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PublisherServiceTest {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private IPublisherDAO publisherDAO;
    private PublisherService publisherService;

    @BeforeEach
    void setUp() {
        entityManager = JPAUtil.getEntityManager();
        entityTransaction = entityManager.getTransaction();
        publisherDAO = new PublisherDAOImpl(entityManager);
        publisherService = new PublisherService(publisherDAO);
    }

    @AfterEach
    void close() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @Test
    void CadastroDeEditora() {
        Publisher publisher = new Publisher();
        publisher.setName("Harper Collins");
        publisher.setCountry("England");

        entityTransaction.begin();
        int result = publisherService.createPublisher(publisher);
        entityTransaction.commit();

        assertEquals(1, result);

        Publisher publisherFound = entityManager.find(Publisher.class, publisher.getId());
        assertNotNull(publisherFound);
    }

    @Test
    void EdicaoDeEditora() {
        Publisher publisher = new Publisher();
        publisher.setName("Penguin Random House");
        publisher.setCountry("England/USA");

        entityTransaction.begin();
        //* Adicionando editora
        publisherService.createPublisher(publisher);

        //* Atualizando editora
        publisher.setCountry("USA");
        int result = publisherService.updatePublisher(publisher);
        entityTransaction.commit();

        assertEquals(1, result);
    }

    @Test
    void BuscaDeEditoraPorId() {
        Publisher publisher = new Publisher();
        publisher.setName("Macmillan Publishers");
        publisher.setCountry("England/USA");

        entityTransaction.begin();
        //* Adicionando editora
        int result = publisherService.createPublisher(publisher);
        //* Buscando editora
        Publisher searchPublisher = publisherService.findPublisherById(publisher.getId());
        entityTransaction.commit();

        assertEquals(searchPublisher.getName(), publisher.getName());
        assertEquals(1, result);
    }

    @Test
    void BuscaDeListaDeEditoras() {
        entityTransaction.begin();
        List<Publisher> publishers = publisherService.findAllPublishers();
        entityTransaction.commit();

        assertFalse(publishers.isEmpty());
    }

    @Test
    void removerEditora(){
        Publisher publisher = new Publisher();
        publisher.setName("Editora Teste");
        publisher.setCountry("País Teste");

        entityTransaction.begin();
        //* Criando editora
        int result = publisherService.createPublisher(publisher);

        //* Buscando editora
        Publisher foundPublisher = publisherService.findPublisherById(publisher.getId());

        assertNotNull(foundPublisher);
        //* Removendo editora
        int removePublisher = publisherService.removePublisherById(foundPublisher.getId());
        entityTransaction.commit();

        assertEquals(1, removePublisher);
    }
}