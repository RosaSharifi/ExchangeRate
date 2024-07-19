package ir.rayanbourse.exchangerateservice.service;

import ir.rayanbourse.exchangerateservice.dto.ApiCurrencyDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ECBApiHandlerImpl implements ApiHandler {

    private volatile HashMap<String, ApiCurrencyDto> cachedCurrencyMap;
    private final RestTemplate restTemplate;

    @Value("${exchange.api.baseUrl}")
    private String htmlBaseUrl;

    @Value("${exchange.api.resource}")
    private String htmlResource;

    @Value("${exchange.api.cache.enable}")
    private boolean cacheEnabled;

    @Value("${exchange.api.cacheExpiry.seconds}")
    private Integer cacheExpiryTime;
    private LocalDateTime lastUpdate;

    public ECBApiHandlerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, ApiCurrencyDto> getCurrencyMap() {
        if (cacheEnabled) {
            return getCachedMap();
        } else {
            return createCurrencyMap();
        }
    }

    private HashMap<String, ApiCurrencyDto> getCachedMap() {
        if (cachedCurrencyMap == null) {
            synchronized (ECBApiHandlerImpl.class) {
                if (cachedCurrencyMap == null) {
                    cachedCurrencyMap = createCurrencyMap();
                    lastUpdate = LocalDateTime.now();
                }
            }
        }
        if (Duration.between(lastUpdate, LocalDateTime.now()).getSeconds() > cacheExpiryTime) {
            synchronized (ECBApiHandlerImpl.class) {
                if (Duration.between(lastUpdate, LocalDateTime.now()).getSeconds() > cacheExpiryTime) {
                    cachedCurrencyMap = createCurrencyMap();
                    lastUpdate = LocalDateTime.now();
                }
            }
        }
        return cachedCurrencyMap;
    }

    private HashMap<String, ApiCurrencyDto> createCurrencyMap() {
        HashMap<String, ApiCurrencyDto> currencyMap = new HashMap<>();
        String html = getHtmlResource();
        Document document = Jsoup.parse(html);
        Elements mainSection = document.select("main");
        Elements tables = mainSection.select("table");
        for (Element table : tables) {
            Elements rows = table.select("tr");
            for (Element row : rows) {
                Elements cells = row.select("td");
                if (!cells.isEmpty()) {
                    ApiCurrencyDto currency = new ApiCurrencyDto(
                            cells.get(0).text(),
                            cells.get(1).text(),
                            Double.parseDouble(cells.get(2).text()),
                            htmlBaseUrl + cells.get(3).select("a").attr("href")
                    );
                    currencyMap.put(currency.getCode(), currency);
                }
            }
        }
        return currencyMap;
    }

    private String getHtmlResource() {
        return restTemplate.getForObject(htmlBaseUrl + htmlResource, String.class);
    }

}
