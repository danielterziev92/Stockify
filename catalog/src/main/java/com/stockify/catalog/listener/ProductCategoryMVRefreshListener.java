package com.stockify.catalog.listener;

import com.stockify.catalog.event.ProductCategoryRefreshEvent;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductCategoryMVRefreshListener {

    private final EntityManager entityManager;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onRefresh(ProductCategoryRefreshEvent event) {
        Thread.ofVirtual()
                .name("mv-refresh-", 0)
                .start(this::refreshMV);
    }

    private void refreshMV() {
        entityManager.createNativeQuery(
                "REFRESH MATERIALIZED VIEW CONCURRENTLY category_mv"
        ).executeUpdate();
    }
}
