package ir.rayanbourse.exchangerateservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.rayanbourse.exchangerateservice.dto.CurrencyDto;
import ir.rayanbourse.exchangerateservice.service.ExchangeRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Tag(
        name = "Exchange Rate API",
        description = "Exchange Rate API - Get Currency Rate, Get Currency List, Convert Amount, Retrieve Chart Url"
)
@RestController
@RequestMapping("/api")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Operation(
            summary = "Get Currency Rate REST API",
            description = "Get Currency Rate For Two Currency Code"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/rate/{sourceCurrencyCode}/{targetCurrencyCode}")
    public ResponseEntity<Double> getCurrencyRate(@PathVariable String sourceCurrencyCode,
                                                  @PathVariable String targetCurrencyCode) {
        return new ResponseEntity<>(exchangeRateService.getCurrencyRate(
                sourceCurrencyCode, targetCurrencyCode), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Currency List REST API",
            description = "Get Currency Information List"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDto>> getCurrencyList() {
        return new ResponseEntity<>(exchangeRateService.getCurrencyList(), HttpStatus.OK);
    }

    @Operation(
            summary = "Convert Two Currency REST API",
            description = "Convert Amount From One Currency To Another"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/amount/{amount}/{sourceCurrencyCode}/{targetCurrencyCode}")
    public ResponseEntity<BigDecimal> convertAmount(@PathVariable BigDecimal amount,
                                                    @PathVariable String sourceCurrencyCode,
                                                    @PathVariable String targetCurrencyCode) {
        return new ResponseEntity<>(exchangeRateService.convertAmount(
                amount, sourceCurrencyCode, targetCurrencyCode), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Two Currency Chart Url REST API",
            description = "Retrieve Two Currency Chart Url"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/chart/{sourceCurrencyCode}/{targetCurrencyCode}")
    public ResponseEntity<String> retrieveChartUrl(@PathVariable String sourceCurrencyCode,
                                                   @PathVariable String targetCurrencyCode) {
        return new ResponseEntity<>(exchangeRateService.retrieveChartUrl(
                sourceCurrencyCode, targetCurrencyCode), HttpStatus.OK);
    }

}
