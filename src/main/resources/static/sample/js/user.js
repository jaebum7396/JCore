//**********************************************************************************
// ○ 파일	: index.js
// ● 설명	: 인덱스 js
//**********************************************************************************
// 기본 select 가 제대로 동작하지 않아 추가한 커스텀 셀렉트 2023-06-19 주재범
class CustomSelectEditor {
	constructor(props) {
		let el = document.createElement('select');
		this.el = el;

		// 기본 옵션 추가
		const defaultOptions = [
			{label: '선택안함', value: '0'}
			, {label: '개인회원', value: '10'}
			, {label: '기업회원', value: '20'}
			, {label: '대리점(벤더)', value: '3'}
			, {label: '고객사-지오시스템', value: '4'}
			, {label: '셀러(셀럽,블로거 등)', value: '5'}
		];
		this.addOptions(defaultOptions);

		//console.log(props);
		//console.log(props.value);
		el.value = String(props.value);
	}

	addOptions(options) {
		options.forEach(option => {
			const optionEl = document.createElement('option');
			optionEl.value = String(option.value);
			optionEl.text = option.label;
			this.el.appendChild(optionEl);
		});
	}

	getElement() {
		return this.el;
	}

	getValue() {
		return this.el.value;
	}

	mounted() {
		this.el.focus();
	}
}

//readonly 텍스트에디터
class ReadonlyTextEditor {
	constructor(props) {
		let el = document.createElement('input');

		el.type = 'text';
		el.value = String(props.value);
		el.readOnly = true; // 편집 불가능 설정

		this.el = el;
	}

	getElement() {
		return this.el;
	}

	getValue() {
		return this.el.value;
	}

	mounted() {
		this.el.select();
	}
}

const columns =[
	{ name: "userCd", header: "유저코드(PK)", width: 150, align: 'center', editor: {type: ReadonlyTextEditor,options: {}}}
	, { name: "userType", header: "회원유형", width: 200, align: 'center'
		, formatter: 'listItemText'
		, editor: makeSelectObj([
			{text: '선택안함', value: '0'}
			, {text: '개인회원', value: '1'}
			, {text: '기업회원', value: '20'}
		])
	}
	, {name: "userId", header: "아이디", width: 200, editor: 'text', align: 'center'}
	, {name: "userNm", header: "회원명", width: 100, editor: 'text', align: 'center'}
	, {name: "userPhoneNo", header: "연락처", width: 300, editor: 'text', align: 'center'}
	, {name: "userStatus", header: "유저상태", width: 150, editor: 'text', align: 'center'}
	, {name: "userBirth", header: "생년월일", width: 150, editor: 'text', align: 'center'}
	, {name: "email", header: "Email", width: 300, editor: 'text', align: 'center'}
	, {name: "insertDt",header: "생성일",width: 150,editor: {type: 'datePicker', options: {format: 'dd/MM/yyyy'}},	align: 'center'	}
]


function makeSelectObj(items){
	let selectObj = {
		type: 'select'
		, options: {
			listItems: items
		}
	}
	return selectObj;
}

