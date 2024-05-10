document.querySelectorAll('.filter-link').forEach(link => {
    link.addEventListener('click', function() {
        document.querySelectorAll('.filter-link').forEach(lnk => lnk.classList.remove('active'));
        this.classList.add('active');
    });
});