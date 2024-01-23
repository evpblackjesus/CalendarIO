package io.blackjesus.calendario.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeeklyViewController {

    private int currentYear;
    private int currentMonth;
    private int currentWeek;


    /**
     * currentYear változónak setter metódusa, az év nem lehet korábbi 2000-nél és későbbi 20 évvel későbbel
     * @param year
     */
    private void setCurrentYear(int year) {
        if(year < 2000 || year > LocalDate.now().getYear() + 20) {
            return;
        }
        currentYear = year;
    }

    /**
     * currentMonth változónak a setter metódusa, a hónap nem lehet kisebb 1-nél valamint nagyobb 12-nél
     * @param month
     */
    private void setCurrentMonth(int month) {
        if(month < 1) {
            //Előző évre lépés
            setCurrentYear(currentYear - 1);
            setCurrentMonth(12);
            return;
        }
        if(month > 12) {
            //Következő évre lépés
            setCurrentYear(currentYear + 1);
            setCurrentMonth(1);
            return;
        }
        currentMonth = month;
    }
    /**
     * Heti napok visszaadása a megadott év, hónap és hét szám alapján
     * @param year Az év
     * @param month A hónap
     * @param weekNumber A hét száma
     * @return Az adott hét napjai
     */

    public List<Integer> setCurrentWeek(int year, int month, int weekNumber) {
        List<Integer> days = new ArrayList<>();


        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        int weeksInMonth = (int) ChronoUnit.WEEKS.between(firstDayOfMonth, lastDayOfMonth);

        System.out.printf(String.valueOf(weeksInMonth));
        if (weekNumber < 1) {
            setCurrentMonth(currentMonth-1);
            setCurrentWeek(year,month,weekNumber);

        } else if (weekNumber > weeksInMonth) {
            setCurrentMonth(currentMonth+1);
            setCurrentWeek(year,month,1);
        }

        LocalDate firstDayOfWeek = firstDayOfMonth.with(WeekFields.of(Locale.getDefault()).weekOfMonth(), weekNumber)
                .with(DayOfWeek.MONDAY);

        System.out.println("Year: " + year);
        System.out.println("Month: " + month);
        System.out.println("Week Number: " + weekNumber);
        System.out.println("First Day of Week: " + firstDayOfWeek);

        for(int i = 0; i<7; i++){
            days.add(firstDayOfWeek.plusDays(i).getDayOfMonth());
            System.out.printf(firstDayOfWeek.plusDays(i).getDayOfMonth()+", ");
        }


        System.out.printf(String.valueOf(firstDayOfWeek));

        currentWeek = weekNumber;

        return days;

    }



}