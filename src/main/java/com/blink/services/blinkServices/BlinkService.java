package com.blink.services.blinkServices;

import com.blink.Entities.Brows;
import com.blink.Entities.MakeUp;
import com.blink.Entities.Nails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@Component
@Transactional
public class BlinkService implements BlinkServiceInterface {

    @Autowired
    private NailsDAOInterface nailsDAO;
    @Autowired
    private BrowsDAOInterface browsDAO;
    @Autowired
    private MakeUpDAOInterface makeUpDAO;

    @Override
    public void addService(String service, Date date, Time time, long id_client) {
        if (service.equals("Nails") || service.equals("nails")) {
            nailsDAO.addService(date, time, id_client);
        } else if (service.equals("Brows") || service.equals("brows") || service.equals("hair") || service.equals("Hair")) {
            browsDAO.addService(date, time, id_client);
        } else {
            makeUpDAO.addService(date, time, id_client);
        }
    }

    @Override
    public Map<String, Object[]> getClientReservations(long id_client) {
        Map<String, Object[]> map = new HashMap<>();
        map.put("makeUp", makeUpDAO.getMakeUpReservationsByClientId(id_client));
        map.put("brows", browsDAO.getBrowsReservationsByClientId(id_client));
        map.put("nails", nailsDAO.getNailsReservationsByClientId(id_client));
        return map;
    }

    @Override
    public TreeSet<Time> getBusyTimesforService(long id_client, String service, Date date) {
        if (service == null || service.equals("") || date == null)
            return null;

        List<Time> list;
        if (service.equals("Nails") || service.equals("nails")) {
            list = nailsDAO.getBusyTimesforService(date);
        } else if (service.equals("Brows") || service.equals("brows") || service.equals("hair") || service.equals("Hair")) {
            list = browsDAO.getBusyTimesforService(date);
        } else {
            list = makeUpDAO.getBusyTimesforService(date);
        }

        if (id_client != 0) {
            list.addAll(nailsDAO.getClientTimeForDayByID(id_client, date));
            list.addAll(browsDAO.getClientTimeForDayByID(id_client, date));
            list.addAll(makeUpDAO.getClientTimeForDayByID(id_client, date));
        }
        TreeSet<Time> timeTreeSet = new TreeSet<>(list);
        return timeTreeSet;
    }

    @Override
    public void removeReservation(String service, Date date, Time time, long id_client) {
        if (service != null && !service.equals("") && date != null && time != null) {
            if (service.equals("Nails") || service.equals("nails")) {
                nailsDAO.removeReservation(date, time, id_client);
            } else if (service.equals("Brows") || service.equals("brows") || service.equals("hair") || service.equals("Hair")) {
                browsDAO.removeReservation(date, time, id_client);
            } else {
                makeUpDAO.removeReservation(date, time, id_client);
            }
        }
    }

    @Override
    public void updateService(long id_client, String service, Date old_date, Time old_time, Date new_date, Time new_time) {
        if (service != null && !service.equals("") && old_date != null && old_time != null &&
                new_date != null && new_time != null) {
            if (service.equals("Nails") || service.equals("nails")) {
                nailsDAO.updateService(id_client, old_date, old_time, new_date, new_time);
            } else if (service.equals("Brows") || service.equals("brows") || service.equals("hair") || service.equals("Hair")) {
                browsDAO.updateService(id_client, old_date, old_time, new_date, new_time);
            } else {
                makeUpDAO.updateService(id_client, old_date, old_time, new_date, new_time);
            }
        }
    }

    @Override
    public void cleanDataBase() {
        nailsDAO.cleanNailsTable();
        browsDAO.cleanBrowsTable();
        makeUpDAO.cleanMakeUpTable();
    }
}
