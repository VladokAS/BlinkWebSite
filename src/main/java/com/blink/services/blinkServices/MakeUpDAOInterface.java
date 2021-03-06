package com.blink.services.blinkServices;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface MakeUpDAOInterface {
     void addService(Date date, Time time, long id_client);
     Object[] getMakeUpReservationsByClientId(long id_client);
     List<Time> getBusyTimesforService(Date date);
     void removeReservation(Date date, Time time, long id_client);
     void updateService(long id_client, Date old_date, Time old_time, Date new_date, Time new_time);
     List<Time> getClientTimeForDayByID(long id_client, Date date);
    void cleanMakeUpTable();
}
