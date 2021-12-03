package me.reszkojr.workshopjavafxjdbc.model.services;

import me.reszkojr.workshopjavafxjdbc.model.dao.DaoFactory;
import me.reszkojr.workshopjavafxjdbc.model.dao.SellerDao;
import me.reszkojr.workshopjavafxjdbc.model.entities.Seller;

import java.util.List;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Seller obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Seller obj) {
        dao.deleteById(obj.getId());
    }
}
