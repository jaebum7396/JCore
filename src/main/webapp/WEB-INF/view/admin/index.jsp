<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:directive.include file="../common/head.jsp"/>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="js/index.js"></script>
        <script src="js/menu.js"></script>
        <script>
            const sideMenuGenerator = new SideMenuGenerator('layoutSidenav_nav');
            sideMenuGenerator.init();

            const topMenu = new TopMenuGenerator('topNavbar');
            topMenu.init();
        </script>
    </head>
    <body class="">
        <div id="contents" style="display: flex; flex-direction: column; min-height: 100vh;">
            <div id="topArea">
                <div id="topNavbar"></div>
            </div>
            <div id="mainArea" style="flex: 1;">
                <div id="layoutSidenav">
                    <div id="layoutSidenav_nav">
                    </div>
                </div>
            </div>
            <div id="footerArea">
                <footer class="py-2 bg-light">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between">
                            <div class="text-muted">Copyright &copy; Your Website 2023</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    </body>
</html>
