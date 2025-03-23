package org.example.test_orm.service;

import java.time.LocalTime;

// TODo (Самвел) Доделать проревкри по времени
public class CheckDate {

    public boolean checkFinishBeforeStart(LocalTime startVisit, LocalTime finishVisit) {
        return startVisit.isBefore(finishVisit);
    }

    public boolean checkWorkTime(LocalTime startVisit, LocalTime finishVisit) {
        LocalTime startWorkTime = LocalTime.of(8, 59, 0);
        LocalTime finishWorkTime =  LocalTime.of(19, 1, 0);
        boolean resultOfStartVisit = (startVisit.isAfter(startWorkTime) && startVisit.isBefore(finishWorkTime));
        boolean resultOfFinishVisit = (finishVisit.isAfter(startWorkTime) && finishVisit.isBefore(finishWorkTime));
        return resultOfStartVisit && resultOfFinishVisit;
    }
}
