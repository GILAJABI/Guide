document.addEventListener('DOMContentLoaded', () => {
    const chatContentInput = document.querySelector('#chat_content');
    const chatButton = document.querySelector('#chat_btn');
    let chatList = document.querySelector('#chat_list');

    // Check if chatList exists, if not, create it
    if (!chatList) {
        chatList = document.createElement('div');
        chatList.id = 'chat_list';
        document.body.appendChild(chatList);
    }

    chatButton.addEventListener('click', () => {
        const chatContent = chatContentInput.value.trim();
        if (chatContent !== '') {
            const chatItem = document.createElement('div');
            const text = document.createElement('span');


            text.textContent = chatContent;

            chatItem.appendChild(text);
            chatList.appendChild(chatItem);

            chatContentInput.value = '';
        }
    });
});