package ru.yandex.practicum.gym;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Comparator;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, HashSet<TrainingSession>>>  timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, HashSet<TrainingSession>> sessionsByTime = timetable.get(day);
        if (sessionsByTime == null) {
            sessionsByTime = new TreeMap<>();
            timetable.put(day, sessionsByTime);
        }

        HashSet<TrainingSession> sessions = sessionsByTime.get(time);
        if (sessions == null) {
            sessions = new HashSet<>();
            sessionsByTime.put(time, sessions);
        }

        sessions.add(trainingSession); //сохраняем занятие в расписании
    }

    public TreeMap<TimeOfDay, HashSet<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek); //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public HashSet<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, HashSet<TrainingSession>> day = timetable.get(dayOfWeek);
        return day == null ? null : day.get(timeOfDay); //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public  Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> coaches = new LinkedHashMap<>();
        for (TreeMap<TimeOfDay, HashSet<TrainingSession>> byTime : timetable.values()) {
            for (HashSet<TrainingSession> sessions : byTime.values()) {
                for (TrainingSession session : sessions) {
                    if (coaches.containsKey(session.getCoach())) {
                        coaches.put(session.getCoach(), coaches.get(session.getCoach()) + 1);
                    } else {
                        coaches.put(session.getCoach(), 1);
                    }
                }
            }
        }

        Comparator<Coach> byCountDescending = new Comparator<Coach>() {
            @Override
            public int compare(Coach first, Coach second) {
                int byCount = Integer.compare(coaches.get(second), coaches.get(first));
                if (byCount != 0) {
                    return byCount;
                }
                return first.getSurname().compareTo(second.getSurname());
            }
        };

        Map<Coach, Integer> result = new TreeMap<>(byCountDescending);
        result.putAll(coaches);
        return result;
    }

}
