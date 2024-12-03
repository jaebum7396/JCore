package jCore.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "API 응답")
public class Response {
    @Schema(description = "응답 메시지", example = "성공하였습니다")
    String message;

    @Schema(description = "응답 결과 데이터", example = "{\"key\": \"value\"}")
    Map<String, Object> result;
}