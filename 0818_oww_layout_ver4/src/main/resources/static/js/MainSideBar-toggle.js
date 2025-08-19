document.addEventListener('DOMContentLoaded', function () {
  const page   = document.querySelector('.page');          // 부모 래퍼
  const toggle = document.querySelector('.sidebar-toggle'); // 사이드바 안 버튼
  if (!page || !toggle) return;

  const saved = localStorage.getItem('sb-collapsed');
  if (saved === '1') page.classList.add('is-collapsed');

  toggle.addEventListener('click', function(){
    page.classList.toggle('is-collapsed');
    localStorage.setItem('sb-collapsed', page.classList.contains('is-collapsed') ? '1' : '0');
  });
});
