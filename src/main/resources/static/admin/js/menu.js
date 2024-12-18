// 기본 메뉴 생성기 클래스
class BaseMenuGenerator {
	constructor(containerId) {
		this.containerId = containerId;
	}

	init() {
		this.fetchMenuData();
	}

	fetchMenuData() {
		axios.get('/menu')
			.then(response => {
				if (response.status === 200) {
					const menuHtml = this.generateMenu(response.data.result.menuList);
					this.renderMenu(menuHtml);
				}
			})
			.catch(error => {
				console.error('메뉴 로딩 실패:', error);
			});
	}

	renderMenu(menuHtml) {
		const container = document.getElementById(this.containerId);
		if (container) {
			container.innerHTML = menuHtml;
		}
	}
}

// 사이드 메뉴 생성기 클래스
class SideMenuGenerator extends BaseMenuGenerator {
	generateMenu(menuList) {
		let menuHtml = `
            <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                <div class="sb-sidenav-menu">
                    <div class="nav">
        `;

		menuList.forEach(menu => {
			if (menu.menuType === 'HEADER') {
				menuHtml += this.generateHeader(menu);
				menuHtml += this.generateSubMenus(menu.childMenu);
			}
		});

		menuHtml += `
                    </div>
                </div>
                ${this.generateFooter()}
            </nav>
        `;

		return menuHtml;
	}

	generateHeader(menu) {
		return `<div class="sb-sidenav-menu-heading">${menu.menuNm}</div>`;
	}

	generateSubMenus(subMenus) {
		let subMenuHtml = '';

		subMenus.forEach(subMenu => {
			if (subMenu.menuType === 'MENU') {
				subMenuHtml += this.generateSingleMenu(subMenu);
			} else if (subMenu.menuType === 'NODE') {
				subMenuHtml += this.generateCollapsibleMenu(subMenu);
			}
		});

		return subMenuHtml;
	}

	generateSingleMenu(menu) {
		return `
            <a class="nav-link" href="${menu.menuUrl.toLowerCase()}">
                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                ${menu.menuNm}
            </a>
        `;
	}

	generateCollapsibleMenu(menu) {
		const collapseId = `collapse${menu.menuCd.substring(0, 8)}`;
		return `
            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" 
               data-bs-target="#${collapseId}" aria-expanded="false" 
               aria-controls="${collapseId}">
                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                ${menu.menuNm}
                <div class="sb-sidenav-collapse-arrow">
                    <i class="fas fa-angle-down"></i>
                </div>
            </a>
            <div class="collapse" id="${collapseId}" 
                 aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                <nav class="sb-sidenav-menu-nested nav accordion" 
                     id="sidenavAccordion${collapseId}">
                    ${this.generateNestedMenu(menu.childMenu, `sidenavAccordion${collapseId}`)}
                </nav>
            </div>
        `;
	}

	generateNestedMenu(childMenuList, parentId) {
		let nestedHtml = '';

		childMenuList.forEach(child => {
			if (child.childMenu && child.childMenu.length > 0) {
				const collapseId = `collapse${child.menuCd.substring(0, 8)}`;
				nestedHtml += `
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" 
                       data-bs-target="#${collapseId}" aria-expanded="false" 
                       aria-controls="${collapseId}">
                        ${child.menuNm}
                        <div class="sb-sidenav-collapse-arrow">
                            <i class="fas fa-angle-down"></i>
                        </div>
                    </a>
                    <div class="collapse" id="${collapseId}" 
                         aria-labelledby="headingOne" data-bs-parent="#${parentId}">
                        <nav class="sb-sidenav-menu-nested nav">
                            ${this.generateNestedMenu(child.childMenu, parentId)}
                        </nav>
                    </div>
                `;
			} else {
				nestedHtml += this.generateSingleMenu(child);
			}
		});

		return nestedHtml;
	}

	generateFooter() {
		return `
            <div class="sb-sidenav-footer">
                <div class="small">Logged in as:</div>
                Start Bootstrap
            </div>
        `;
	}
}

// 상단 메뉴 생성기 클래스
class TopMenuGenerator extends BaseMenuGenerator {
	generateMenu(menuList) {
		// 메인 메뉴 아이템들 생성
		let menuItems = '';
		menuList.forEach(menu => {
			if (menu.menuType === 'HEADER') {
				// 헤더 아이템 추가
				menuItems += `
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" 
                           data-bs-toggle="dropdown" aria-expanded="false">
                            ${menu.menuNm}
                        </a>
                        <ul class="dropdown-menu">
                `;

				// 하위 메뉴 아이템들 추가
				menu.childMenu.forEach(subMenu => {
					if (subMenu.menuType === 'MENU') {
						menuItems += this.generateDropdownItem(subMenu);
					} else if (subMenu.menuType === 'NODE') {
						menuItems += this.generateNestedDropdown(subMenu);
					}
				});

				menuItems += `
                        </ul>
                    </li>
                `;
			}
		});

		// 전체 네비게이션 구조
		let menuHtml = `
            <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
                <!-- Navbar Brand-->
                <a class="navbar-brand ps-3" href="index">Start Bootstrap</a>
                <!-- Sidebar Toggle-->
                <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!">
                    <i class="fas fa-bars"></i>
                </button>
                <!-- Main Menu Items -->
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    ${menuItems}
                </ul>
                <!-- Navbar Search-->
                <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
                    <div class="input-group">
                        <input class="form-control" type="text" placeholder="Search for..." 
                               aria-label="Search for..." aria-describedby="btnNavbarSearch" />
                        <button class="btn btn-primary" id="btnNavbarSearch" type="button">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </form>
                <!-- Navbar-->
                <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" 
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fas fa-user fa-fw"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="#!">Settings</a></li>
                            <li><a class="dropdown-item" href="#!">Activity Log</a></li>
                            <li><hr class="dropdown-divider" /></li>
                            <li><a class="dropdown-item" href="#!">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        `;

		return menuHtml;
	}

	generateDropdownItem(menu) {
		return `
            <li><a class="dropdown-item" href="${menu.menuUrl.toLowerCase()}">${menu.menuNm}</a></li>
        `;
	}

	generateNestedDropdown(menu) {
		const dropdownId = `dropdown${menu.menuCd.substring(0, 8)}`;
		return `
            <li class="dropdown-submenu">
                <a class="dropdown-item dropdown-toggle" href="#" 
                   data-bs-toggle="dropdown" aria-expanded="false">
                    ${menu.menuNm}
                </a>
                <ul class="dropdown-menu dropdown-menu-end submenu">
                    ${this.generateNestedDropdownMenu(menu.childMenu)}
                </ul>
            </li>
        `;
	}

	generateNestedDropdownMenu(childMenuList) {
		let nestedHtml = '';

		childMenuList.forEach(child => {
			if (child.childMenu && child.childMenu.length > 0) {
				nestedHtml += this.generateNestedDropdown(child);
			} else {
				nestedHtml += this.generateDropdownItem(child);
			}
		});

		return nestedHtml;
	}

	init() {
		super.init();
		this.addCustomStyles();
	}

	addCustomStyles() {
		const style = document.createElement('style');
		style.innerHTML = `
            .navbar .dropdown-menu .submenu {
                display: none;
                position: absolute;
                left: 100%;
                top: 0;
            }
            .navbar .dropdown-submenu:hover > .submenu {
                display: block;
            }
            .navbar .nav-item.dropdown:hover > .dropdown-menu {
                display: block;
            }
            .navbar .dropdown-menu {
                margin-top: 0;
            }
            .dropdown-toggle::after {
                vertical-align: middle;
            }
            .dropdown-submenu .dropdown-toggle::after {
                transform: rotate(-90deg);
            }
        `;
		document.head.appendChild(style);
	}
}