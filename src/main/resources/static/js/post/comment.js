document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    const commentList = document.getElementById("comment-list");

    form.addEventListener("submit", function(e) {
        e.preventDefault();
        const formData = new FormData(form);
        const postData = new URLSearchParams();

        for (const pair of formData) {
            postData.append(pair[0], pair[1]);
        }

        fetch('/post/carrotDetail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: postData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Server responded with an error!');
                }
                return response.json();
            })
            .then(data => {
                const commentItem = document.createElement("li");
                commentItem.textContent = formData.get("commentContent"); // 입력된 댓글 내용을 바로 추가
                commentList.appendChild(commentItem);
                form.reset(); // 입력 폼 초기화
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Error posting comment: " + error.message);
            });
    });
});
