package com.library.dao.generics.implementations;

import com.library.dao.generics.interfaces.IGenericDAO;
import com.library.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericDAO<T, ID> implements IGenericDAO<T, ID> {
    //* Conexões
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    protected void closeConnection(Connection connection) throws SQLException {
        DBConnection.closeConnection(connection);
    }

    //* Métodos abstratos que as classes concretas devem implementar
    protected abstract T mapResultSetToEntity(ResultSet rs) throws
            SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement ps,
                                                      T entity) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement ps,
                                                      T entity) throws SQLException;

    protected abstract String getTableName();

    protected abstract String getInsertSql();

    protected abstract String getUpdateSql();

    public T createGeneric(T entity) {
        String insertSql = getInsertSql();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql,
                     Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(preparedStatement, entity);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserção com falha, nenhuma linha foi criada.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    /**
                     * - Assumindo que o ID é um Long e que a entidade tem um setId(Long id);
                     *
                     * - Isso pode precisar ser ajustado dependendo do tipo de ID e da entidade;
                     *
                     * - Para simplificar, vamos retornar a entidade sem o ID gerado aqui,
                     * e deixar a classe concreta lidar com a atribuição do ID se necessário.
                     *  Ou, podemos adicionar um método abstrato para setar o ID na entidade.
                     *
                     * - Por enquanto, vamos apenas retornar a entidade original.
                     *
                     * - Em um cenário real, você provavelmente precisaria de um método para setar o ID.
                     *  Ex: ((Produto)entity).setId(generatedKeys.getLong(1));
                     */
                }
                return entity;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<T> searchGenericById(ID id) {
        String searchByIdSql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchByIdSql)
        ) {
            preparedStatement.setObject(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar entidade por ID: " +
                    e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
    @Override
    public List<T> searchAllGenerics() {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as entidades: " +
                    e.getMessage());
            throw new RuntimeException(e);
        }
        return entities;
    }
    @Override
    public T updateGeneric(T entity) {
        String sql = getUpdateSql();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            prepareStatementForUpdate(ps, entity);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualização com falha, nenhuma linha foi afetada.");
            }
            return entity;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar entidade: " +
                    e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public void deleteGenericById(ID id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar entidade: " +
                    e.getMessage());
            throw new RuntimeException(e);
        }
    }
}