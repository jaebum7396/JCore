package jCore.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;
@Data
@Builder
@AllArgsConstructor
public class Response {
    @ApiModelProperty(value="성공하였습니다", example="성공")
    String message;
    @ApiModelProperty(value="{model}", example="model")
    Map<String,Object> result;
}
