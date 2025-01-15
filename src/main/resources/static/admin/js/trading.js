//**********************************************************************************
// ○ 파일	: trading.js
// ● 설명	: trading js
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
	{name: "insertDt", header: "진입시간", width: 180, editor: 'text', align: 'center', borderStyle: '1px solid #ddd'}
	//, {name: "tradingCd", header: "트레이딩코드(PK)", width: 300, align: 'center', editor: {type: ReadonlyTextEditor,options: {}}, borderStyle: '1px solid #ddd'}
	, {name: "collateral", header: "담보금", width: 100, editor: 'text', align: 'right', borderStyle: '1px solid #ddd'}
	, {name: "leverage", header: "레버리지", width: 100, editor: 'text', align: 'center', borderStyle: '1px solid #ddd'}
	, { name: "positionStatus", header: "상태", width: 100, align: 'center'
		, formatter: 'listItemText'
		, editor: makeSelectObj([
			{text: 'OPEN', value: 'OPEN'}
			, {text: 'CLOSE', value: 'CLOSE'}
		])
		, borderStyle: '1px solid #ddd'
	}
	, { name: "positionSide", header: "방향", width: 100, align: 'center'
		, formatter: 'listItemText'
		, editor: makeSelectObj([
			  {text: 'LONG', value: 'LONG'}
			, {text: 'SHORT', value: 'SHORT'}
		])
		, borderStyle: '1px solid #ddd'
	}
	, {name: "openPrice", header: "진입가격", width: 100, editor: 'text', align: 'right', borderStyle: '1px solid #ddd'}
	, {name: "closePrice", header: "종료가격", width: 100, editor: 'text', align: 'right', borderStyle: '1px solid #ddd'}
	, {name: "profit", header: "실현수익", width: 150, editor: 'text', align: 'right', borderStyle: '1px solid #ddd'}
	//, {name: "qty",header: "qty",width: 100, align: 'left', borderStyle: '1px solid #ddd'}
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

