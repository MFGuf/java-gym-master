package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testGetCountByCoachesSortedByCountDescending() {
        Timetable timetable = new Timetable();

        Coach vasilyev = new Coach("Васильев", "Николай", "Сергеевич");
        Coach petrov = new Coach("Петров", "Иван", "Иванович");
        Coach sidorov = new Coach("Сидоров", "Пётр", "Петрович");

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        //Васильев - 3 занятия
        timetable.addNewTrainingSession(new TrainingSession(group, vasilyev, DayOfWeek.MONDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, vasilyev, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, vasilyev, DayOfWeek.WEDNESDAY, new TimeOfDay(11, 0)));

        //Петров - 7 занятий
        timetable.addNewTrainingSession(new TrainingSession(group, petrov, DayOfWeek.MONDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, petrov, DayOfWeek.TUESDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, petrov, DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, petrov, DayOfWeek.THURSDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, petrov, DayOfWeek.FRIDAY, new TimeOfDay(16, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, petrov, DayOfWeek.SATURDAY, new TimeOfDay(17, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, petrov, DayOfWeek.SUNDAY, new TimeOfDay(18, 0)));

        //Сидоров - 5 занятий
        timetable.addNewTrainingSession(new TrainingSession(group, sidorov, DayOfWeek.MONDAY, new TimeOfDay(19, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, sidorov, DayOfWeek.TUESDAY, new TimeOfDay(20, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, sidorov, DayOfWeek.WEDNESDAY, new TimeOfDay(21, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, sidorov, DayOfWeek.THURSDAY, new TimeOfDay(22, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, sidorov, DayOfWeek.FRIDAY, new TimeOfDay(23, 0)));

        Map<Coach, Integer> counts = timetable.getCountByCoaches();

        //Проверить, что вернулись все трое тренеров
        assertEquals(3, counts.size());

        //Проверить, что счётчики верны
        assertEquals(7, counts.get(petrov));
        assertEquals(5, counts.get(sidorov));
        assertEquals(3, counts.get(vasilyev));

        //Проверить, что тренеры упорядочены по убыванию количества занятий
        Iterator<Coach> coaches = counts.keySet().iterator();
        assertEquals(petrov, coaches.next());
        assertEquals(sidorov, coaches.next());
        assertEquals(vasilyev, coaches.next());
        assertFalse(coaches.hasNext());
    }

    @Test
    void testGetCountByCoachesWithEqualCounts() {
        Timetable timetable = new Timetable();

        Coach ivanov = new Coach("Иванов", "Антон", "Антонович");
        Coach kuznetsov = new Coach("Кузнецов", "Борис", "Борисович");
        Coach orlov = new Coach("Орлов", "Виктор", "Викторович");

        Group group = new Group("Акробатика для взрослых", Age.ADULT, 90);

        //У каждого тренера ровно по два занятия
        timetable.addNewTrainingSession(new TrainingSession(group, ivanov, DayOfWeek.MONDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, ivanov, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, kuznetsov, DayOfWeek.MONDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, kuznetsov, DayOfWeek.TUESDAY, new TimeOfDay(12, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, orlov, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, orlov, DayOfWeek.TUESDAY, new TimeOfDay(14, 0)));

        Map<Coach, Integer> counts = timetable.getCountByCoaches();

        //Проверить, что при равном количестве занятий ни один тренер не потерялся
        assertEquals(3, counts.size());

        //Проверить, что у всех тренеров по два занятия
        assertEquals(2, counts.get(ivanov));
        assertEquals(2, counts.get(kuznetsov));
        assertEquals(2, counts.get(orlov));

        //Проверить, что при равном количестве тренеры идут по алфавиту
        Iterator<Coach> coaches = counts.keySet().iterator();
        assertEquals(ivanov, coaches.next());
        assertEquals(kuznetsov, coaches.next());
        assertEquals(orlov, coaches.next());
    }

    @Test
    void testGetCountByCoachesWithoutTrainingSessions() {
        Timetable timetable = new Timetable();

        //Проверить, что для пустого расписания возвращается пустая карта, а не ошибка
        Map<Coach, Integer> counts = timetable.getCountByCoaches();

        assertTrue(counts.isEmpty());

        //Проверить, что после добавления одного занятия считается ровно один тренер
        Coach vasilyev = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        timetable.addNewTrainingSession(new TrainingSession(group, vasilyev, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));

        Map<Coach, Integer> countsAfterOne = timetable.getCountByCoaches();

        assertEquals(1, countsAfterOne.size());
        assertEquals(1, countsAfterOne.get(vasilyev));
    }

}