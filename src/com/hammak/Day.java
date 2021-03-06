package com.hammak;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Day {
    private boolean empty;
    private LocalDate date;
    private DayOfWeek name;
    private ArrayList<Pair> pairs;

    Day(LocalDate date, ArrayList<Pair> pairs) {
        this.date = date;
        this.name = date.getDayOfWeek();
        this.pairs = pairs;
        this.empty = emptinessCheck();
    }

    void fillPair(int pairNumber, LocalTime startTime, int lectureHallNumber, String subject, String teacher) {
        pairs.get(pairNumber - 1).setStartTime(startTime);
        pairs.get(pairNumber - 1).setLectureHallNumber(lectureHallNumber);
        pairs.get(pairNumber - 1).setSubject(subject);
        pairs.get(pairNumber - 1).setTeacher(teacher);

    }

    private boolean emptinessCheck() {
        for (Pair pair : pairs) {
            if (!pair.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    boolean isEmpty() {
        return empty;
    }

    public DayOfWeek getName() {
        return name;
    }

    String getStringName() {
        HashMap<DayOfWeek, String> weekDays = new HashMap<>();
        weekDays.put(DayOfWeek.MONDAY, "Понеділок");
        weekDays.put(DayOfWeek.TUESDAY, "Вівторок");
        weekDays.put(DayOfWeek.WEDNESDAY, "Середа");
        weekDays.put(DayOfWeek.THURSDAY, "Четвер");
        weekDays.put(DayOfWeek.FRIDAY, "П'ятниця");
        weekDays.put(DayOfWeek.SATURDAY, "Субота");
        return weekDays.get(name);
    }

    public ArrayList<Pair> getPairs() {
        return pairs;
    }

    LocalDate getDate() {
        return date;
    }

    int pairsAmount() {
        return pairs.size();
    }

    public Pair getPair(int pairNumber) {
        return pairs.get(pairNumber);
    }
}
