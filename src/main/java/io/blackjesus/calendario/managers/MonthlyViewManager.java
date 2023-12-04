package io.blackjesus.calendario.managers;

import io.blackjesus.calendario.controllers.MonthlyViewController;

import java.time.LocalDate;

public class MonthlyViewManager {
    private static MonthlyViewController monthlyViewController;

    public static void setMonthlyViewController(MonthlyViewController controller) {
        monthlyViewController = controller;
    }

    public static void setView(int year, int month) {
        monthlyViewController.setCurrentYear(year);
        monthlyViewController.setCurrentMonth(month);
    }

    public static void setView(LocalDate date) {
        setView(date.getYear(), date.getMonth().getValue());
    }
}
