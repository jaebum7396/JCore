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
      { label: '선택안함', value: '0' }
      , { label: '개인회원', value: '10' }
      , { label: '기업회원', value: '20' }
      , { label: '대리점(벤더)', value: '3' }
      , { label: '고객사-지오시스템', value: '4' }
      , { label: '셀러(셀럽,블로거 등)', value: '5' },
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

// CSS 추가
const style = document.createElement('style');
document.head.appendChild(style);

// 상태(OPEN/CLOSE) 렌더러
class PositionStatusRenderer {
  constructor(props) {
    const el = document.createElement('div');
    el.style.color = props.value === 'OPEN' ? '#2196F3' : '#b1b1b1';
    el.textContent = props.value;
    this.el = el;
  }

  getElement() {
    return this.el;
  }
}

// 방향(LONG/SHORT) 렌더러
class PositionSideRenderer {
  constructor(props) {
    const el = document.createElement('div');
    el.style.color = props.value === 'LONG' ? '#0fcd28' : '#FF5252';
    el.textContent = props.value;
    this.el = el;
  }

  getElement() {
    return this.el;
  }
}

// 실현수익 렌더러
class ProfitRenderer {
  constructor(props) {
    const el = document.createElement('div');
    const value = Number(props.value);
    // 0이 아닐 때만 색상 적용
    if (value !== 0) {
      el.style.color = value > 0 ? '#0fcd28' : '#FF5252';
    }
    el.textContent = props.value;
    this.el = el;
  }

  getElement() {
    return this.el;
  }
}

const columns = [
  {
    name: 'insertDt',
    header: '진입시간',
    minWidth: 150,
    editor: 'text',
    align: 'center',
    borderStyle: '1px solid #ddd',
  },
  {
    name: 'positionStatus',
    header: '상태',
    minWidth: 80,
    align: 'center',
    editor: 'text',
    renderer: {
      type: PositionStatusRenderer,
    },
    borderStyle: '1px solid #ddd',
  },
  {
    name: 'positionSide',
    header: '방향',
    minWidth: 80,
    align: 'center',
    editor: 'text',
    renderer: {
      type: PositionSideRenderer,
    },
    borderStyle: '1px solid #ddd',
  },
  {
    name: 'collateral',
    header: '담보금',
    minWidth: 100,
    editor: 'text',
    align: 'right',
    borderStyle: '1px solid #ddd',
  },
  {
    name: 'openPrice',
    header: '진입가격',
    minWidth: 100,
    editor: 'text',
    align: 'right',
    borderStyle: '1px solid #ddd',
  },
  {
    name: 'closePrice',
    header: '종료가격',
    minWidth: 100,
    editor: 'text',
    align: 'right',
    borderStyle: '1px solid #ddd',
  },
  {
    name: 'profit',
    header: '실현수익',
    minWidth: 120,
    editor: 'text',
    align: 'right',
    renderer: {
      type: ProfitRenderer,
    },
    borderStyle: '1px solid #ddd',
  },
  {
    name: 'leverage',
    header: '레버리지',
    minWidth: 80,
    editor: 'text',
    align: 'center',
    borderStyle: '1px solid #ddd',
  },
];

function makeSelectObj(items) {
  let selectObj = {
    type: 'select'
    , options: {
      listItems: items,
    },
  };
  return selectObj;
}

function getTradingSummary(){
  axios.get(TRADE_URL+'/trading/summary')
      .then(response => {
        if (response.status === 200) {
          console.log('response:', response);
          let data = response.data;
          let total_count = data.totalCount;
          let total_profit = data.totalProfit;
          let total_fee = data.totalFee;
          let total_profit_rate = data.totalProfitRate;
          let win_rate = data.winRate;
          let win_count = data.winCount;
          let lose_count = data.loseCount;

          $('.win_rate').val(win_rate);
          $('.total_count').val(total_count);
          $('.total_profit').val(total_profit);
          $('.total_fee').val(total_fee);
          $('.total_profit_rate').val(total_profit_rate);
          $('.win_count').val(win_count);
          $('.lose_count').val(lose_count);
        }
      })
      .catch(error => {
        console.error('메뉴 로딩 실패:', error);
      });
}

