<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
/***********************************************************************************
* ○ 파일	: main.jsp
* ● 설명	: 인덱스 화면
***********************************************************************************/
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:directive.include file="../common/head.jsp"/>
		
		<!-- tui-pagination -->
		<link rel="stylesheet" href="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.css" />
		<script src="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.js"></script>
		<!-- tui-date-picker -->
		<link rel="stylesheet" href="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.css" />
		<script src="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.js"></script>
		<!-- tui-grid -->
		<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
		<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>

		<%--<script src="utopia/js/index.js<%= 리소스버젼 %>"></script>--%>
		<script src="js/user.js"></script>
		<link href="css/admin.css" type="text/css" rel="stylesheet"/>
	</head>
	<body>

		<div class="page_content" style='margin-left:0;'>
			<div class="search_title">
				<i></i><span> 검색조건</span>
			</div>
			<div class="search_param">
				<%-- 기본 세팅값 --%>
				<input id="LoginUserCD" value="" type="hidden" />
				
				<form id="frmParam" onsubmit="return false" autocomplete="off">
				<table>
					<colgroup>
						<col width="10%"/>
						<col width="23%"/>
						<col width="10%"/>
						<col width="23%"/>
						<col width="10%"/>
						<col width="23%"/>
					</colgroup>
					<tr>
						<th>회원명</th>
						<td>
							<input name="userNm_like">
						</td>
						<th>아이디</th>
						<td>
							<input name="userId_like">
						</td>
						<th>휴대전화</th>
						<td>
							<input name="cellPhoneNO_like">
						</td>
					</tr>
					<tr>
						<th>회원유형</th>
						<td>
							<select name="userType">
								<option value="0">= 전체 =</option>
								<%-- <option value="<%= GC.USER_TYPE_SHOP %>">개인회원</option> --%>
								<option value='10'>개인회원</option>
								<%-- <option value="<%= GC.USER_TYPE_WHOLESALE %>">기업회원</option> --%>
								<option value='20'>기업회원</option>
							</select>
						</td>
						<th>Email</th>
						<td>
							<input name="email_like">
						</td>
						<%/***** %>
						<th>주소</th>
						<td>
							<input name="address_like">
						</td>
						<%*****/ %>
						<th></th>
						<td></td>
					</tr>
				</table>
				</form>
				
				<%-- 파일 다운로드용 form / iframe --%>
					<form action="" id="_FORM_FILE" target="_IFRAME_FILE" method="post"
						  style="display:none; border:0; margin:0;"></form>
					<iframe name="_IFRAME_FILE" style="display:none; border:0; margin:0;"></iframe>
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
							grid.readData(1, serializeFormToJson('frmParam'));
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
								readData: {url: USER_URL+'/grid', method: 'GET'}
								, createData: {url: USER_URL+'/create', method: 'POST'}
								<%--, updateData: {url: USER_URL+'/user/update', method: 'PUT'}
								, deleteData: {url: USER_URL+'/user/delete', method: 'PUT'}
								, modifyData: {url: USER_URL+'/user/modify', method: 'POST'}--%>
							}
							, headers : {
								'Content-Type': 'application/json',
								'Authorization':  localStorage.getItem('token')
							}
						}

						tui.Grid.applyTheme('striped');
						var grid = new tui.Grid({
							el: document.getElementById('grid')
							, rowHeaders: ['checkbox']
							, bodyHeight: 500
							, data: dataSource
							, pageOptions: {
								perPage: 5
							}
							, columnOptions: {
								resizable: true
							}
							, scrollX: true
							, scrollY: true
							, columns: columns
							, onGridMounted(ev) {
								//console.log('onGridMounted', ev);
							}
							, onGridBeforeDestroy(ev) {
								//console.log('onGridBeforeDestroy', ev);
							}
						});

						grid.on('response', function (ev) {
							//console.log('ev', ev)
							// 성공/실패와 관계 없이 응답을 받았을 경우
							let resp = JSON.parse(ev.xhr.response)
							//console.log('response', resp)
							if(resp.statusCode =='401'){
								localStorage.setItem('token', '');
								alert('로그인이 만료되었습니다');
								location.href = '../chat/login';
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
	</body>
</html>