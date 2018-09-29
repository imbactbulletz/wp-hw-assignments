package assignment_7.src.services;

import entities.Recipes;

import java.util.List;

public interface IServiceRecipes extends IServiceAbstract<Recipes> {
    List<Recipes> searchByName(String name);
    List<Recipes> getPage(int page, int pageSize, String searchCriteria);

}
