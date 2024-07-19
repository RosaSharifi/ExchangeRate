package ir.rayanbourse.exchangerateservice.dto;

public class ApiCurrencyDto {

    private String code;
    private String name;
    private Double exchangeRate;
    private String chartUrl;

    public ApiCurrencyDto(String code, String name, Double exchangeRate, String chartUrl) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
        this.chartUrl = chartUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getChartUrl() {
        return chartUrl;
    }

    public void setChartUrl(String chartUrl) {
        this.chartUrl = chartUrl;
    }

}
