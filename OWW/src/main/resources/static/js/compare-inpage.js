// DIY 내부 비교판: 같은 페이지에 테이블 렌더 (최대 4개)
(function(){
  var maxItems = 4;
  var selected = []; // {no, name, price, addr, desc, cat, img}

  var listEl   = document.getElementById('compareList');
  var clearBtn = document.getElementById('btnClear');
  var toggleBtn= document.getElementById('btnToggleCompare');
  var closeBtn = document.getElementById('btnCloseCompare');
  var ipc      = document.getElementById('inpageCompare');
  var ipcBody  = document.getElementById('ipcBody');

  function renderChips(){
    listEl.innerHTML = '';
    selected.forEach(function(it){
      var li = document.createElement('li');
      li.className = 'cb-chip';
      li.setAttribute('data-no', it.no);
      li.innerHTML = "<span>"+ it.name +"</span><button type='button' aria-label='삭제'>✕</button>";
      li.querySelector('button').addEventListener('click', function(){ remove(it.no); });
      listEl.appendChild(li);
    });
  }

  function renderTable(){
    // 4열 맞춤: 빈 칸은 '-' 처리
    var cols = [];
    for(var i=0;i<maxItems;i++){ cols[i] = selected[i] || null; }

    function td(i, getter){
      return cols[i] ? getter(cols[i]) : '-';
    }

    ipcBody.innerHTML = ''
      + row('이미지', function(i){ return cols[i] ? "<div class='img' style='background-image:url("+(cols[i].img||'/img/default-product.png')+");'></div>" : '-' })
      + row('이름',   function(i){ return td(i, function(it){ return it.name; }); })
      + row('분류',   function(i){ return td(i, function(it){ return it.catText; }); })
      + row('가격',   function(i){ return td(i, function(it){ return it.priceText; }); })
      + row('주소/설명', function(i){ return td(i, function(it){ return (it.addr||'') + (it.desc?(' · '+it.desc):''); }); });

    function row(title, valFn){
      var html = '<tr><th>'+title+'</th>';
      for(var i=0;i<maxItems;i++){
        html += '<td>'+ valFn(i) +'</td>';
      }
      html += '</tr>';
      return html;
    }
  }

  function openCompare(){
    renderTable();
    ipc.hidden = false;
    ipc.scrollIntoView({behavior:'smooth', block:'start'});
  }
  function closeCompare(){ ipc.hidden = true; }

  function addFromCard(card){
    var no   = parseInt(card.getAttribute('data-no'),10);
    if(selected.some(function(x){ return x.no===no; })){ return; }
    if(selected.length >= maxItems){ alert('비교는 최대 '+maxItems+'개까지 가능합니다.'); return; }

    var obj = {
      no: no,
      name: card.getAttribute('data-name') || ('상품#'+no),
      price: parseInt(card.getAttribute('data-price')||'0',10),
      priceText: (card.getAttribute('data-price') ? (+card.getAttribute('data-price')).toLocaleString()+'원' : '-'),
      addr: card.getAttribute('data-addr') || '',
      desc: card.getAttribute('data-desc') || '',
      cat:  card.getAttribute('data-cat'),
      catText: (function(c){ return c==='0'?'홀':(c==='1'?'스튜디오':(c==='2'?'드레스':'메이크업')); })(card.getAttribute('data-cat')),
      img:  (function(){
        var bg = card.querySelector('.img').style.backgroundImage || '';
        var m = bg.match(/url\(["']?(.*?)["']?\)/);
        return m && m[1] ? m[1] : null;
      })()
    };
    selected.push(obj);
    renderChips();
  }

  function remove(no){
    selected = selected.filter(function(x){ return x.no!==no; });
    renderChips();
    if(!selected.length) closeCompare();
    else renderTable();
  }

  function clearAll(){
    selected = [];
    renderChips();
    closeCompare();
  }

  // 바인딩
  document.querySelectorAll('.btn-compare').forEach(function(btn){
    btn.addEventListener('click', function(){
      var card = btn.closest('.card');
      addFromCard(card);
    });
  });
  if(clearBtn) clearBtn.addEventListener('click', clearAll);
  if(toggleBtn) toggleBtn.addEventListener('click', function(){
    if(ipc.hidden){ if(!selected.length){ alert('비교할 상품을 먼저 담아주세요.'); return; } openCompare(); }
    else closeCompare();
  });
  if(closeBtn) closeBtn.addEventListener('click', closeCompare);

  // 간단 카테고리 필터
  var tabs = document.querySelectorAll('#wp-diy .tab');
  var cards= document.querySelectorAll('#wp-diy .card');
  tabs.forEach(function(tab){
    tab.addEventListener('click', function(){
      tabs.forEach(function(t){ t.classList.remove('active'); });
      tab.classList.add('active');
      var cat = tab.getAttribute('data-cat');
      cards.forEach(function(card){
        var c = card.getAttribute('data-cat');
        card.style.display = (cat==='-1' || c===cat) ? '' : 'none';
      });
    });
  });
})();
