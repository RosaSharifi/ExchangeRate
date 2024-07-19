package ir.rayanbourse.exchangerateservice.service;

import ir.rayanbourse.exchangerateservice.dto.ApiCurrencyDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestPropertySource(properties = {
        "exchange.api.baseCurrency=EUR"
})
class ExchangeRateServiceImplTest {

    @Mock
    private ApiHandler apiHandler;
    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    private Map<String, ApiCurrencyDto> createRestApiMockData() {
        Map<String, ApiCurrencyDto> map = new HashMap<>();
        map.put("USD", new ApiCurrencyDto("USD", "US dollar", 1.0855,
                "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-usd.en.html"));
        map.put("JPY", new ApiCurrencyDto("JPY", "Japanese yen", 175.39,
                "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-jpy.en.html"));
        map.put("GBP", new ApiCurrencyDto("GBP", "GBP", 0.84305,
                "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-gbp.en.html"));
        map.put("CAD", new ApiCurrencyDto("CAD", "Canadian dollar", 1.4796,
                "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-cad.en.html"));
        return map;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(exchangeRateService, "baseCurrency", "EUR");
        when(apiHandler.getCurrencyMap()).thenReturn(createRestApiMockData());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCurrencyRate() {
        Double result_EUR_USD = exchangeRateService.getCurrencyRate("EUR", "USD");
        Double result_EUR_JPY = exchangeRateService.getCurrencyRate("EUR", "JPY");
        Double result_EUR_GBP = exchangeRateService.getCurrencyRate("EUR", "GBP");
        Double result_EUR_CAD = exchangeRateService.getCurrencyRate("EUR", "CAD");
        Double result_USD_EUR = exchangeRateService.getCurrencyRate("USD", "EUR");
        Double result_JPY_EUR = exchangeRateService.getCurrencyRate("JPY", "EUR");
        Double result_GBP_EUR = exchangeRateService.getCurrencyRate("GBP", "EUR");
        Double result_CAD_EUR = exchangeRateService.getCurrencyRate("CAD", "EUR");
        Double result_USD_JPY = exchangeRateService.getCurrencyRate("USD", "JPY");
        Double result_USD_GBP = exchangeRateService.getCurrencyRate("USD", "GBP");
        Double result_USD_CAD = exchangeRateService.getCurrencyRate("USD", "CAD");
        Double result_JPY_CAD = exchangeRateService.getCurrencyRate("JPY", "CAD");
        Double result_EUR_EUR = exchangeRateService.getCurrencyRate("EUR", "EUR");
        Double result_USD_USD = exchangeRateService.getCurrencyRate("USD", "USD");

        assertEquals(1.0855, result_EUR_USD, "EUR_USD is wrong!");
        assertEquals(175.39, result_EUR_JPY, "EUR_JPY is wrong!");
        assertEquals(0.84305, result_EUR_GBP, "EUR_GBP is wrong!");
        assertEquals(1.4796, result_EUR_CAD, "EUR_CAD is wrong!");
        assertEquals(0.9212, result_USD_EUR, "USD_EUR is wrong!");
        assertEquals(0.0057, result_JPY_EUR, "JPY_EUR is wrong!");
        assertEquals(1.1862, result_GBP_EUR, "GBP_EUR is wrong!");
        assertEquals(0.6759, result_CAD_EUR, "CAD_EUR is wrong!");
        assertEquals(161.5753, result_USD_JPY, "USD_JPY is wrong!");
        assertEquals(0.7766, result_USD_GBP, "USD_GBP is wrong!");
        assertEquals(1.3631, result_USD_CAD, "USD_CAD is wrong!");
        assertEquals(0.0084, result_JPY_CAD, "JPY_CAD is wrong!");
        assertEquals(1.0, result_EUR_EUR, "EUR_EUR is wrong!");
        assertEquals(1.0, result_USD_USD, "USD_USD is wrong!");

        verify(apiHandler, times(16)).getCurrencyMap();
    }

    @Test
    void getCurrencyList() {
    }

    @Test
    void convertAmount() {
    }

    @Test
    void retrieveChartUrl() {
    }
}