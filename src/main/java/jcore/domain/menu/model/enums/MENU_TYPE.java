package jcore.domain.menu.model.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Getter
@Slf4j
public enum MENU_TYPE {
	HEADER("HEADER"),
	NODE("NODE"),
	MENU("MENU");

	private final String menuType;

	MENU_TYPE(String menuType) {
		this.menuType = menuType;
	}

	public static List<String> getMenuTypeList() {
		return Arrays.asList(HEADER.getMenuType(), NODE.getMenuType(), MENU.getMenuType());
	}
}
