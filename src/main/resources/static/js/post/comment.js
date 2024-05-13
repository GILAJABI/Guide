document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    const commentList = document.getElementById("comment-list");

    // 페이지 로드 시 기존 댓글 목록을 불러오는 함수
    fetchComments();

    // 폼 제출 핸들러
    form.addEventListener("submit", function(e) {
        e.preventDefault();
        const formData = new FormData(form);
        const postData = new URLSearchParams();

        for (const pair of formData.entries()) {
            postData.append(pair[0], pair[1]);
        }

        // 폼 데이터로부터 postId 추출
        const postId = formData.get("postId");

        fetch(`/post/carrotDetail?postId=${postId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: postData
        })
            .then(response => response.json())
            .then(data => {
                if (data.id) { // 서버에서 응답받은 데이터에 id가 포함되어 있는 경우
                    const commentItem = document.createElement("li");
                    commentItem.textContent = `${data.authorName}: ${data.content}`; // 작성자 이름과 내용을 표시
                    commentList.appendChild(commentItem);
                    form.reset(); // 폼 초기화
                } else {
                    alert("Error: " + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Error posting comment: " + error.message);
            });
    });

// 페이지 로드 시 기존 댓글 목록을 불러오는 함수
    function fetchComments() {
        const postId = document.querySelector('input[name="postId"]').value; // postId 값을 input 필드에서 가져옴
        fetch(`/api/comments?postId=${postId}`) // 수정된 API 엔드포인트
            .then(response => response.json())
            .then(data => {
                const commentList = document.getElementById('comment-list');
                // commentList.innerHTML = ''; // 기존 댓글 목록 초기화
                data.forEach(comment => {
                    const li = document.createElement('li');
                    li.textContent = `${member.name}: ${comment.commentContent}`; // 회원 이름과 댓글 내용 표시
                    commentList.appendChild(li); // 댓글 목록에 추가
                });
            })
            .catch(error => console.error('Error:', error));
    }

});