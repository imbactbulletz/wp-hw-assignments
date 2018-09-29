package assignment_7.src.dao;

import entities.Recipes;

import java.util.List;

public interface IDAORecipes extends IDAOAbstract<Recipes> {
    List<Recipes> searchByName(String name);
    List<Recipes> getPage(int page, int pageSize, String searchCriteria);

}
