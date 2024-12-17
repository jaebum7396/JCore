//**********************************************************************************
// ○ 파일	: index.js
// ● 설명	: 인덱스 js
//**********************************************************************************
// 기본 select 가 제대로 동작하지 않아 추가한 커스텀 셀렉트 2023-06-19 주재범

function getMenu(){
	axios.get('/menu', {
	}, {
	})
	.then(response => {
		console.log(response)
	})
	.catch(error => {
	});
}
function makeMenuHeader(menuObj) {
	let menuHeaderHtml = '';
	<div className="sb-sidenav-menu-heading">Core</div>
}
