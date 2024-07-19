package ir.rayanbourse.exchangerateservice.service;

import ir.rayanbourse.exchangerateservice.dto.ApiCurrencyDto;

import java.util.Map;

public interface ApiHandler {

    Map<String, ApiCurrencyDto> getCurrencyMap();

}
