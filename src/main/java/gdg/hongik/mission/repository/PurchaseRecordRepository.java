package gdg.hongik.mission.repository;

import gdg.hongik.mission.purchase.PurchaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Long> {
    List<PurchaseRecord> findByConsumer(String consumer);
}
