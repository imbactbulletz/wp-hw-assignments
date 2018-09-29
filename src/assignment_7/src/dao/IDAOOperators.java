package assignment_7.src.dao;

import entities.Operators;

public interface IDAOOperators extends IDAOAbstract<Operators> {
    boolean login(Operators operator);
    boolean registration(Operators operator);

}
