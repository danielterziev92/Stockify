package com.stockify.catalog.shared.context;

import com.stockify.catalog.shared.vo.CompanyId;
import com.stockify.catalog.shared.vo.UserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.lang.ScopedValue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestContext {

    public static final ScopedValue<CompanyId> COMPANY_ID = ScopedValue.newInstance();
    public static final ScopedValue<UserId> USER_ID = ScopedValue.newInstance();

    public static @NonNull CompanyId getCompanyId() {
        if (!COMPANY_ID.isBound()) {
            throw new IllegalStateException("CompanyId is not bound to current scope");
        }
        return COMPANY_ID.get();
    }

    public static @NonNull UserId getUserId() {
        if (!USER_ID.isBound()) {
            throw new IllegalStateException("UserId is not bound to current scope");
        }
        return USER_ID.get();
    }
}
