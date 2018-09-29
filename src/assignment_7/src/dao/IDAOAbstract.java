package assignment_7.src.dao;

import entities.BasicEntity;

import java.util.List;

public interface IDAOAbstract <T extends BasicEntity> {
    boolean add(T object);
    List<T> getAll();
}
