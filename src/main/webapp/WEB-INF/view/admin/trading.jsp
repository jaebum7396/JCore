<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:directive.include file="../common/head.jsp"/>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="js/index.js"></script>
        <script src="js/menu.js"></script>
        <script src="js/trading.js"></script>
        <script>
            const sideMenuGenerator = new SideMenuGenerator('layoutSidenav_nav');
            sideMenuGenerator.init();

            const topMenu = new TopMenuGenerator('topNavbar');
            topMenu.init();
        </script>
        <style>
            /* 헤더 테두리 */
            .tui-grid-header-cell {
                border-right: 1px solid #ddd !important;
                border-bottom: 1px solid #ddd !important;
            }

            /* 바디 셀 테두리 */
            .tui-grid-cell {
                border-right: 1px solid #ddd !important;
                border-bottom: 1px solid #ddd !important;
            }

            /* 전체 그리드 테두리 */
            .tui-grid {
                border-top: 1px solid #ddd !important;
                border-left: 1px solid #ddd !important;
            }

            /* 체크박스 컬럼 테두리 */
            .tui-grid-row-header-cell {
                border-right: 1px solid #ddd !important;
                border-bottom: 1px solid #ddd !important;
            }
        </style>
    </head>
    <body class="">
        <div id="contents" style="display: flex; flex-direction: column; height: 100vh;">
            <div id="topArea">
                <div id="topNavbar"></div>
            </div>
            <div id="mainArea" style="flex: 1;">
                <div id="layoutSidenav">
                    <div id="layoutSidenav_nav">
                    </div>
                </div>
                <div class="page_content" style='width:100%; height:100%; overflow:scroll;'>
                    <div style="display:none;">
                        <div class="search_title">
                            <i></i><span> 검색조건</span>
                        </div>
                        <div class="search_param">
                        </div>
                        <div class="search_button">
                            <div class="button basic" onclick="clickSearch();">
                                <span>조 회</span>
                            </div>
                            <div id="create" class="button basic">
                                <span>생성</span>
                            </div>
                            <div id="sync" class="button basic">
                                <span>저장</span>
                            </div>
                            <div id="delete" class="button basic">
                                <span>삭제</span>
                            </div>
                        </div>
                    </div>

                    <!-- 검색 결과 리스트 -->
                    <div class="search_list">
                        <div>
                            <div id="grid" style=''></div>
                            <script>
                                document.getElementById('create').addEventListener('click', createRow);
                                document.getElementById('sync').addEventListener('click', syncServer);
                                document.getElementById('delete').addEventListener('click', deleteRow);

                                function createRow() {
                                    grid.prependRow();
                                    grid.request('createData');
                                }

                                function deleteRow() {
                                    //console.log('삭제할 행 : ' + grid.getCheckedRowKeys());
                                    grid.removeRows(grid.getCheckedRowKeys())
                                    grid.request('deleteData');
                                }

                                // 폼 데이터를 JSON 형식으로 변환하는 함수
                                function serializeFormToJson(formId) {
                                    var formData = $("#" + formId).serializeArray();
                                    var json = {};
                                    $.each(formData, function (index, field) {
                                        json[field.name] = field.value || "";
                                    });
                                    return json;
                                }

                                function clickSearch() {
                                    serializeFormToJson('frmParam');
                                    grid.readData(0, serializeFormToJson('frmParam'));
                                }


                                function syncServer() {
                                    const {rowKey, columnName} = grid.getFocusedCell();

                                    if (rowKey && columnName) {
                                        grid.finishEditing(rowKey, columnName);
                                    }

                                    grid.request('updateData', {
                                        checkedOnly: false
                                    });

                                }

                                var dataSource = {
                                    initialRequest: true
                                    , contentType: 'application/json'
                                    , api: {
                                        readData: {url: TRADE_URL+'/trading', method: 'GET'}
                                        <%--, createData: {url: USER_URL+'/create', method: 'POST'}
                                        , updateData: {url: USER_URL+'/user/update', method: 'PUT'}
                                        , deleteData: {url: USER_URL+'/user/delete', method: 'PUT'}
                                        , modifyData: {url: USER_URL+'/user/modify', method: 'POST'}--%>
                                    }
                                    , headers : {
                                        'Content-Type': 'application/json',
                                        'Authorization':  localStorage.getItem('token')
                                    }
                                }

                                tui.Grid.applyTheme('striped', {
                                    cell: {
                                        normal: {
                                            border: '#ddd'
                                        },
                                        header: {
                                            border: '#ddd'
                                        }
                                    }
                                });
                                var grid = new tui.Grid({
                                    el: document.getElementById('grid')
                                    , rowHeaders: ['checkbox']
                                    , bodyHeight: 500
                                    , data: dataSource
                                    , pageOptions: {
                                        perPage: 10
                                    }
                                    , columnOptions: {
                                        resizable: true
                                    }
                                    , scrollX: true
                                    , scrollY: true
                                    , columns: columns
                                    , onGridMounted(ev) {
                                        console.log('onGridMounted', ev);
                                    }
                                    , onGridBeforeDestroy(ev) {
                                        //console.log('onGridBeforeDestroy', ev);
                                    }
                                    ,border: true  // 이 옵션 추가
                                });

                                grid.on('response', function (ev) {
                                    console.log('ev', ev)
                                    // 성공/실패와 관계 없이 응답을 받았을 경우
                                    let resp = JSON.parse(ev.xhr.response)
                                    console.log('response', resp)
                                    if(resp.statusCode =='401'){
                                        localStorage.setItem('token', '');
                                        alert('로그인이 만료되었습니다');
                                    }
                                    if(JSON.stringify(resp) != '{}'){

                                    }else{
                                        clickSearch();
                                    }
                                })
                            </script>
                        </div>
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
