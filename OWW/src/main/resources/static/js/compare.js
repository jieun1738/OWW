// ===== DIY 상품 비교 바 (프론트 전용, Ajax 없음) =====
// - 최대 4개까지 선택 가능
// - 카드의 [비교] 버튼 클릭 → 비교바에 chip 추가
// - chip의 x 클릭 → 개별 삭제
// - [전체 비우기] → 전부 삭제
// - [비교하기] → 링크 이동(/planner/compare/product)

(function(){
  var maxItems = 4;
  var selected = []; // {no, name}

  var listEl = document.getElementById('compareList');
  var clearBtn = document.getElementById('btnClear');
  var goBtn = document.getElementById('btnGoCompare');
  var compareButtons = document.querySelectorAll('.btn-compare');

  function render(){
    listEl.innerHTML = '';
    selected.forEach(function(item){
      var li = document.createElement('li');
      li.className = 'cb-chip';
      li.setAttribute('data-no', item.no);
      li.innerHTML = '<span>'+ item.name +'</span><button type="button" aria-label="삭제">✕</button>';
      li.querySelector('button').addEventListener('click', function(){
        remove(item.no);
      });
      listEl.appendChild(li);
    });
  }

  function add(no, name){
    if(selected.some(function(it){ return it.no === no; })){ return; }
    if(selected.length >= maxItems){
      alert('비교는 최대 '+maxItems+'개까지 가능합니다.');
      return;
    }
    selected.push({no:no, name:name});
    render();
  }

  function remove(no){
    selected = selected.filter(function(it){ return it.no !== no; });
    render();
  }

  function clearAll(){
    selected = [];
    render();
  }

  compareButtons.forEach(function(btn){
    btn.addEventListener('click', function(){
      var no = parseInt(btn.getAttribute('data-no'), 10);
      var name = btn.getAttribute('data-name') || ('상품#'+no);
      add(no, name);
    });
  });

  if(clearBtn){ clearBtn.addEventListener('click', clearAll); }

  // 폴더 탭 필터(간단)
  var tabs = document.querySelectorAll('#wp-diy .tab');
  var cards = document.querySelectorAll('#wp-diy .card');
  tabs.forEach(function(tab){
    tab.addEventListener('click', function(){
      tabs.forEach(function(t){ t.classList.remove('active'); });
      tab.classList.add('active');
      var cat = tab.getAttribute('data-cat');
      cards.forEach(function(card){
        var c = card.getAttribute('data-cat');
        if(cat === '-1' || c === cat) card.style.display = '';
        else card.style.display = 'none';
      });
    });
  });

})();
