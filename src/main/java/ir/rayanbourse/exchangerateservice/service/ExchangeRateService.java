package ir.rayanbourse.exchangerateservice.service;

import ir.rayanbourse.exchangerateservice.dto.CurrencyDto;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    Double getCurrencyRate(String source, String target);

    List<CurrencyDto> getCurrencyList();

    BigDecimal convertAmount(BigDecimal amount, String source, String target);

    String retrieveChartUrl(String source, String target);

}
