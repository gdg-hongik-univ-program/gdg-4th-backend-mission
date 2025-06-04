package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.model.PurchaseHistory;
import gdg.hongik.mission.model.PurchaseItem;
import gdg.hongik.mission.repository.PurchaseHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final PurchaseHistoryRepository historyRepository;

    public StatisticsService(PurchaseHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    /**
     * 특정 소비자의 모든 구매 기록 조회
     */
    public List<PurchaseRecordDto> getPurchaseRecords(String target) {
        return historyRepository.findByName(target).stream()
                .map(history -> {
                    List<ItemCountDto> items = history.getItems().stream()
                            .map(i -> new ItemCountDto(i.getName(), i.getCount()))
                            .toList();
                    return new PurchaseRecordDto(items);
                })
                .toList();
    }

    /**
     * 특정 소비자의 구매 통계 계산
     */
    public List<PurchaseStatisticsDto> getPurchaseStatistics(String target) {
        List<PurchaseHistory> records = historyRepository.findByName(target);

        Map<String, List<Integer>> itemMap = new HashMap<>();

        for (PurchaseHistory record : records) {
            for (PurchaseItem item : record.getItems()) {
                itemMap
                        .computeIfAbsent(item.getName(), k -> new ArrayList<>())
                        .add(item.getCount());
            }
        }

        return itemMap.entrySet().stream()
                .map(entry -> {
                    String name = entry.getKey();
                    List<Integer> counts = entry.getValue();
                    int total = counts.stream().mapToInt(Integer::intValue).sum();
                    double average = counts.isEmpty() ? 0 : (double) total / counts.size();
                    return new PurchaseStatisticsDto(name, total, average);
                })
                .toList();
    }
}
