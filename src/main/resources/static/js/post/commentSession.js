document.addEventListener("DOMContentLoaded", function () {
    var memberId = sessionStorage.getItem("member_id");

    var commentButton = document.getElementById("comment-button");
    commentButton.addEventListener("click", function () {
        console.log("==================click=================")
        if (memberId) {
            console.log("==================member id exist=================")
            console.log(memberId)
            document.getElementById("comment-form").submit();
        } else {
            console.log("==================member id not exist=================")
            console.log("==================member id not exist=================")
            alert("로그인을 해주세요.");
        }
    });
});