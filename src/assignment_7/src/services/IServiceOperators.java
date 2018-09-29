package assignment_7.src.services;

import entities.Operators;

public interface IServiceOperators extends IServiceAbstract<Operators> {
    boolean login(Operators operator);
    boolean registration(Operators operator);
}
