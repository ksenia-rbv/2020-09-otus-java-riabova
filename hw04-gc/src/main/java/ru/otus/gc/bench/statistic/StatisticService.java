package ru.otus.gc.bench.statistic;

import com.sun.management.GarbageCollectionNotificationInfo;

public interface StatisticService {

    void put(GarbageCollectionNotificationInfo info);

    void setStartTime(long startTime);

    void setEndTime(long endTime);

    long getRunDurationInSec();

    void outOfMemoryHappen();

    long getYoungGenGcCount();

    long getOldGenGcCount();

    default long getGcCount() {
        return getYoungGenGcCount() + getOldGenGcCount();
    }

    long getYoungGenGcDuration();

    long getOldGenGcDuration();

    default long getGcDuration() {
        return getYoungGenGcDuration() + getOldGenGcDuration();
    }

    void printStatistic();
}
