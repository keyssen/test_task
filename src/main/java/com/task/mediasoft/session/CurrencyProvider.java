package com.task.mediasoft.session;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Класс, предоставляющий информацию о текущей валюте пользователя в рамках сеанса.
 * Этот компонент является Spring Bean и используется для хранения текущей валюты пользователя.
 * Скоуп бина установлен в SESSION, что означает, что каждый пользователь будет иметь свой собственный экземпляр данного компонента в рамках его сеанса.
 * Для обеспечения корректной работы в многопоточной среде, проксируется целевой класс.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class CurrencyProvider {

    /**
     * Текущая валюта пользователя. По умолчанию установлена в RUB (российский рубль).
     */
    private CurrencyEnum currency = CurrencyEnum.RUB;
}