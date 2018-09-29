package assignment_7.src.services;

import dao.IDAOOperators;
import entities.Operators;

public class ServiceOperators extends ServiceAbstract<Operators, IDAOOperators> implements IServiceOperators {

    public ServiceOperators(IDAOOperators dao){
        super(dao);
    }
    @Override
    public boolean login(Operators operator) {
        return this.dao.login(operator);
    }

    @Override
    public boolean registration(Operators operator) {
        return this.dao.registration(operator);
    }
}
