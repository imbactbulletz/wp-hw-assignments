package assignment_7.src.services;

import dao.IDAOAbstract;
import entities.BasicEntity;

import java.util.List;

public abstract class ServiceAbstract<T extends BasicEntity, DAO extends IDAOAbstract<T>> implements IServiceAbstract<T> {
    protected DAO dao;

    public ServiceAbstract(DAO dao){
        this.dao = dao;
    }

    @Override
    public boolean add(T object) {
        return this.dao.add(object);
    }

    @Override
    public List<T> getAll() {
        return this.dao.getAll();
    }
}

