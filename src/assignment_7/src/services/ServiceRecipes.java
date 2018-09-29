package assignment_7.src.services;

import dao.IDAORecipes;
import entities.Recipes;

import java.util.List;

public class ServiceRecipes extends ServiceAbstract<Recipes, IDAORecipes> implements IServiceRecipes {

    public ServiceRecipes(IDAORecipes dao){
        super(dao);
    }

    @Override
    public List<Recipes> searchByName(String name) {
        return this.dao.searchByName(name);
    }

    @Override
    public List<Recipes> getPage(int page, int pageSize, String searchCriteria) {
       return this.dao.getPage(page,pageSize, searchCriteria);
    }
}
