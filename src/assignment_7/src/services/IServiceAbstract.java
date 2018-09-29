package assignment_7.src.services;

import entities.BasicEntity;

import java.util.List;

public interface IServiceAbstract <T extends BasicEntity> {
    boolean add(T object);
    List<T> getAll();
}
