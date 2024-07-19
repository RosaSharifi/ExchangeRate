package ir.rayanbourse.exchangerateservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "CurrencyDto Model Information"
)
public class CurrencyDto {

    @Schema(
            description = "Currency Code"
    )
    private String code;
    @Schema(
            description = "Currency Name"
    )
    private String name;
    @Schema(
            description = "Number Of Requests For This Currency"
    )
    private Long numberOfRequests;

    public CurrencyDto(String code, String name, Long numberOfRequests) {
        this.code = code;
        this.name = name;
        this.numberOfRequests = numberOfRequests;
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

    public Long getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(Long numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }
}
