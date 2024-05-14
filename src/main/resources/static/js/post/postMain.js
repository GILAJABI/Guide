document.addEventListener("DOMContentLoaded", function() {
    const links = document.querySelectorAll('#sortMenu .filter-link');
    links.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault(); // 페이지 리로드 방지
            links.forEach(node => node.classList.remove('active')); // 모든 링크에서 active 클래스 제거
            link.classList.add('active'); // 클릭된 링크에 active 클래스 추가
        });
    });
});
