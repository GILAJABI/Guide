window.onload = function() {
    var link = document.querySelector('a[href="../chat/chatRoom.html"]');
    
    link.onclick = function(event) {
        window.open(this.href, '_blank', 'width=375px,height=667px');
        
        event.preventDefault();
    };
};
