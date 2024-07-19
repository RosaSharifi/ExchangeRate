package ir.rayanbourse.exchangerateservice.service;

import ir.rayanbourse.exchangerateservice.dto.ApiCurrencyDto;
import ir.rayanbourse.exchangerateservice.dto.CurrencyDto;
import ir.rayanbourse.exchangerateservice.exception.InvalidChartException;
import ir.rayanbourse.exchangerateservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ApiHandler apiHandler;
    private final ConcurrentHashMap<String, Long> apiCallCounter = new ConcurrentHashMap<>();
    @Value("${exchange.api.baseCurrency}")
    private String baseCurrency;

    public ExchangeRateServiceImpl(ApiHandler apiHandler) {
        this.apiHandler = apiHandler;
    }

    private ApiCurrencyDto getCurrency(String currencyCode) {
        ApiCurrencyDto currency = apiHandler.getCurrencyMap().get(currencyCode);
        if (currency == null) {
            throw new ResourceNotFoundException(String.format("(%s) currency code not found!", currencyCode));
        }
        return currency;
    }

    private Double getExchangeRate(String currencyCode) {
        return getCurrency(currencyCode).getExchangeRate();
    }

    @Override
    public Double getCurrencyRate(String source, String target) {
        Double rate;
        if (source.equals(target)) {
            rate = 1D;
        } else if (source.equals(baseCurrency)) {
            rate = getExchangeRate(target);
        } else if (target.equals(baseCurrency)) {
            Double sourceRate = getExchangeRate(source);
            rate = BigDecimal.ONE.divide(
                            BigDecimal.valueOf(sourceRate), 4, RoundingMode.HALF_UP)
                    .stripTrailingZeros().doubleValue();
        } else {
            Double sourceRate = getExchangeRate(source);
            Double targetRate = getExchangeRate(target);
            rate = BigDecimal.valueOf(targetRate).divide(
                    BigDecimal.valueOf(sourceRate), 4, RoundingMode.HALF_UP).doubleValue();
        }
        apiCallCounter.merge(target, 1L, Long::sum);
        return rate;
    }

    @Override
    public List<CurrencyDto> getCurrencyList() {
        Map<String, ApiCurrencyDto> map = apiHandler.getCurrencyMap();
        return map.entrySet().stream()
                .map(entry ->
                        new CurrencyDto(entry.getValue().getCode(),
                                entry.getValue().getName(),
                                apiCallCounter.getOrDefault(entry.getKey(), 0L))
                )
                .sorted(Comparator.comparing(CurrencyDto::getNumberOfRequests).reversed()
                        .thenComparing(CurrencyDto::getCode)
                ).collect(Collectors.toList());
    }

    @Override
    public BigDecimal convertAmount(BigDecimal amount, String source, String target) {
        Double rate = getCurrencyRate(source, target);
        return amount.multiply(BigDecimal.valueOf(rate));
    }

    @Override
    public String retrieveChartUrl(String source, String target) {
        if (source.equals(target)) {
            throw new InvalidChartException(String.format("there is no chart for same (%s) currency code!", source));
        }
        if (!source.equals(baseCurrency)) {
            throw new InvalidChartException(String.format("there is no chart for (%s) currency code as the base!", source));
        }
        return getCurrency(target).getChartUrl();
    }
}
