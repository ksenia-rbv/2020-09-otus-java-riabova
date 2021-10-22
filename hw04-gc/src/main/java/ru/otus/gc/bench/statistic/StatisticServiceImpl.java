package ru.otus.gc.bench.statistic;

import com.sun.management.GarbageCollectionNotificationInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StatisticServiceImpl implements StatisticService {

    public static final String YOUNG_REGEX = ".*((Young)|(Copy)|(Scavenge)|(Warmup)|(ParNew)).*";
    public static final String OLD_REGEX = ".*((Old)|(MarkSweep)|(Allocation Rate)|(Proactive)).*";
    private final List<GarbageCollectionNotificationInfo> infoList = new CopyOnWriteArrayList<>();
    private boolean outOfMemoryHappen = false;
    private long startTime = 0;
    private long endTime = 0;
    private List<GarbageCollectionNotificationInfo> resultInfoList;

    @Override
    public void put(GarbageCollectionNotificationInfo info) {
        infoList.add(info);
    }

    @Override
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public long getRunDurationInSec() {
        return (endTime - startTime) / 1000;
    }

    @Override
    public void outOfMemoryHappen() {
        outOfMemoryHappen = true;
        resultInfoList = List.copyOf(infoList);
    }

    @Override
    public long getYoungGenGcCount() {
        return resultInfoList.stream()
                .filter(i -> i.getGcName().matches(YOUNG_REGEX) || i.getGcCause().matches(YOUNG_REGEX))
                .count();
    }

    @Override
    public long getOldGenGcCount() {
        return resultInfoList.stream()
                .filter(i -> i.getGcName().matches(OLD_REGEX) || i.getGcCause().matches(OLD_REGEX))
                .count();
    }

    @Override
    public long getYoungGenGcDuration() {
        return resultInfoList.stream()
                .filter(i -> i.getGcName().matches(YOUNG_REGEX) || i.getGcCause().matches(YOUNG_REGEX))
                .mapToLong(i -> i.getGcInfo().getDuration())
                .sum();
    }

    @Override
    public long getOldGenGcDuration() {
        return resultInfoList.stream()
                .filter(i -> i.getGcName().matches(OLD_REGEX) || i.getGcCause().matches(OLD_REGEX))
                .mapToLong(i -> i.getGcInfo().getDuration())
                .sum();
    }

    @Override
    public void printStatistic() {
        if (resultInfoList == null) {
            resultInfoList = List.copyOf(infoList);
        }
        System.out.println("> ----------------");
        System.out.println("> Statistic:");
        System.out.println(String.format("> OutOfMemory %s", outOfMemoryHappen ? "happen" : "don't happen"));
        System.out.println(String.format("> Run duration: %s sec", getRunDurationInSec()));
        System.out.println(String.format("> GC count: %s, young: %s, old: %s", getGcCount(), getYoungGenGcCount(), getOldGenGcCount()));
        System.out.println(String.format("> GC duration: %s, young: %s, old: %s", getGcDuration(), getYoungGenGcDuration(), getOldGenGcDuration()));
        System.out.println(String.format("Markdown: |%s|%s|%s|%s|%s|%s|%s|", getRunDurationInSec(), getGcCount(),
                getYoungGenGcCount(), getOldGenGcCount(), getGcDuration(), getYoungGenGcDuration(), getOldGenGcDuration()));
        System.out.println("> ----------------");
    }

}
