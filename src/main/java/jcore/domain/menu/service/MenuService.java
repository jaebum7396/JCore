package jcore.domain.menu.service;

import jcore.common.CommonUtils;
import jcore.domain.menu.model.dto.request.SaveMenuRequest;
import jcore.domain.menu.model.entity.Menu;
import jcore.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {
    private final CommonUtils commonUtils;
    private final MenuRepository menuRepository;
    public Map<String, Object> getMenu() throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        List<Menu> menuList = menuRepository.findByMenuLevel(0);

        resultMap.put("menuList", menuList);

        return resultMap;
    }
    public Map<String, Object> saveMenu(SaveMenuRequest saveMenuRequest) throws Exception {
        log.info("MenuService.saveMenu.params : " + saveMenuRequest.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        // 1. Menu 생성
        Menu menu = saveMenuRequest.toEntity();

        // 2. 저장
        menuRepository.save(menu);

        // 3. 저장 후 결과 맵에 정보 추가
        resultMap.put("menuCd", menu.getMenuCd());

        return resultMap;
    }
}