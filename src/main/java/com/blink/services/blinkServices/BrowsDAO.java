package com.blink.services.blinkServices;

import com.blink.Entities.Brows;
import com.blink.Entities.MakeUp;
import com.blink.Entities.Nails;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BrowsDAO implements BrowsDAOInterface{

    @PersistenceContext
    private EntityManager entityManager;

    //Adding new nails service
    public void addService(Brows brows) {
        entityManager.persist(brows);
    }

    public Object[] getNailsReservationsByClientId(long id_client) {
        Query query = entityManager.createNativeQuery("select * from Brows where id_client = " + id_client + "", Brows.class);
        List<Brows> list = query.getResultList();
        Object[] array = new Object[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Map<String, String> temporaryMap = new HashMap<>();
            Brows temporaryBrows = list.get(i);
            temporaryMap.put("date", temporaryBrows.getDate().toString());
            temporaryMap.put("time", temporaryBrows.getTime().toString());
            array[i] = temporaryMap;
        }

        return array;
    }
}