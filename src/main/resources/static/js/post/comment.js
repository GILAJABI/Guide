document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    const commentList = document.getElementById("comment-list");

    form.addEventListener("submit", function(e) {
        e.preventDefault();
        const formData = new FormData(form);

        // Fetch API 사용하여 요청 본문에 postId와 memberId 추가
        const postData = new URLSearchParams();
        for (const pair of formData) {
            postData.append(pair[0], pair[1]);
        }

        // AJAX 요청 설정
        fetch('/post/joinDetail', {  // URL을 서버의 엔드포인트와 일치하게 수정
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: postData
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        }).then(data => {
            const commentItem = document.createElement("li");
            commentItem.textContent = formData.get("commentContent"); // 입력된 댓글 내용을 바로 추가
            commentList.appendChild(commentItem);
            form.reset(); // 입력 폼 초기화
        })
            .catch(error => console.error('Error:', error));
    });
});
