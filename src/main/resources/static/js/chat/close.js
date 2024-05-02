document.addEventListener('DOMContentLoaded', () => {
    const closeButton = document.querySelector('.chat_close button');
    
    closeButton.addEventListener('click', () => {
        window.close();
    });
});
