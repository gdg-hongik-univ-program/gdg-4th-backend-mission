package gdg.hongik.mission.domain.purchase.service;

import gdg.hongik.mission.domain.item.entity.Item;
import gdg.hongik.mission.domain.item.repository.ItemRepository;
import gdg.hongik.mission.domain.purchase.dto.response.PurchaseHistoryResponse;
import gdg.hongik.mission.domain.purchase.dto.response.PurchaseStatisticsResponse;
import gdg.hongik.mission.domain.purchase.entity.PurchaseHistory;
import gdg.hongik.mission.domain.purchase.repository.PurchaseHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 구매 기록 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final ItemRepository itemRepository;

    /**
     * 특정 소비자의 구매 기록을 조회합니다
     * 
     * @param userName 조회할 소비자명
     * @return 구매 기록
     */
    public PurchaseHistoryResponse getPurchaseHistory(String userName) {
        List<PurchaseHistory> histories = purchaseHistoryRepository.findByUserNameOrderByPurchaseTimeDesc(userName);
        
        // 모든 상품 목록 조회
        List<Item> allItems = itemRepository.findAll();
        
        List<PurchaseHistoryResponse.PurchaseRecord> records = histories.stream()
                .map(history -> {
                    // 각 구매 기록에 대해 모든 상품의 구매 수량을 표시 (구매하지 않은 상품은 0)
                    List<PurchaseHistoryResponse.PurchaseItemData> items = allItems.stream()
                            .map(item -> {
                                int count = history.getItems().stream()
                                        .filter(historyItem -> historyItem.getItemName().equals(item.getName()))
                                        .mapToInt(historyItem -> historyItem.getCount())
                                        .sum();
                                return new PurchaseHistoryResponse.PurchaseItemData(item.getName(), count);
                            })
                            .collect(Collectors.toList());
                    
                    return new PurchaseHistoryResponse.PurchaseRecord(items);
                })
                .collect(Collectors.toList());
        
        return new PurchaseHistoryResponse(records);
    }

    /**
     * 특정 소비자의 구매 통계를 조회합니다
     * 
     * @param userName 조회할 소비자명
     * @return 구매 통계
     */
    public PurchaseStatisticsResponse getPurchaseStatistics(String userName) {
        List<PurchaseHistory> histories = purchaseHistoryRepository.findByUserNameOrderByPurchaseTimeDesc(userName);
        List<Item> allItems = itemRepository.findAll();
        
        List<PurchaseStatisticsResponse.ItemStatistics> statistics = allItems.stream()
                .map(item -> {
                    // 해당 상품의 총 구매 수량 계산
                    int totalCount = histories.stream()
                            .flatMap(history -> history.getItems().stream())
                            .filter(historyItem -> historyItem.getItemName().equals(item.getName()))
                            .mapToInt(historyItem -> historyItem.getCount())
                            .sum();
                    
                    // 해당 상품을 구매한 횟수 계산
                    long purchaseCount = histories.stream()
                            .filter(history -> history.getItems().stream()
                                    .anyMatch(historyItem -> historyItem.getItemName().equals(item.getName()) && historyItem.getCount() > 0))
                            .count();
                    
                    // 평균 계산
                    double average = purchaseCount > 0 ? (double) totalCount / purchaseCount : 0.0;
                    
                    return new PurchaseStatisticsResponse.ItemStatistics(
                            item.getName(),
                            totalCount,
                            average
                    );
                })
                .collect(Collectors.toList());
        
        return new PurchaseStatisticsResponse(statistics);
    }
} 