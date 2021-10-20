# Сравнение сборщиков мусора

## Таблица сравнения

| GC | VM Options | Время до OutOfMemory, c | Запусков GC | Запусков GC Young |  Запусков GC Old | Длительность GC, мс | Длительность GC Young, мс | Длительность GC Old, мс |
|-|-|-|-|-|-|-|-|-|
|G1|-Xms512m -Xmx512m -XX:+UseG1GC|50|74|50|24|13558|1406|12152|
|Serial Collector|-Xms512m -Xmx512m -XX:+UseSerialGC|52|28|5|23|14572|558|14014|
|Parallel Collector|-Xms512m -Xmx512m -XX:+UseParallelGC|67|51|4|47|34184|412|33772|
|CMS|-Xms512m -Xmx512m -XX:+UseConcMarkSweepGC|68|10|4|6|4586|455|4131|
|ZGC|-Xms512m -Xmx512m -XX:+UnlockExperimentalVMOptions -XX:+UseZGC|26|11|3|8|7407|612|6795|

## Вывод

Для данной задачи лучший результат показал CMS (Concurrent Mark Sweep) сборщик мусора - приложение проработало максимальное
количество времени до OutOfMemory, а сборка мусора заняла минимальное время, по сравнению с другими типами GC.