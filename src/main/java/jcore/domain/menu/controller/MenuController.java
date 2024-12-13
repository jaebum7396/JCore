package jcore.domain.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jcore.common.dto.Response;
import jcore.domain.menu.model.dto.request.SaveMenuRequest;
import jcore.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static jcore.common.CommonUtils.okResponsePackaging;

@Slf4j
@Tag(name = "MenuController", description = "메뉴 API")
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    @GetMapping
    @Operation(summary="메뉴 가져오기", description="메뉴 API")
    public ResponseEntity<Response> getMenu() throws Exception {
        return okResponsePackaging(menuService.getMenu());
    }
    @PostMapping
    @Operation(summary="메뉴 저장", description="메뉴 저장 API")
    public ResponseEntity<Response> saveMenu(@RequestBody SaveMenuRequest saveMenuRequest) throws Exception {
        return okResponsePackaging(menuService.saveMenu(saveMenuRequest));
    }
}