$(document).ready(function () {
    $('.star_rating .star').click(function () {
        $(this).addClass('on').prevAll('.star').addClass('on');
        $(this).nextAll('.star').removeClass('on');
    });
});

// 첨부파일
function handleImageUpload(event) {
    const selectedFile = event
        .target
        .files[0]; // 선택된 파일 가져오기
    const profileImage = document.getElementById('profile-image'); // 프로필 이미지 요소 가져오기

    // FileReader 객체 생성
    const reader = new FileReader();

    // 파일 읽기에 성공했을 때의 동작 정의
    reader.onload = function () {
        profileImage.src = reader.result; // 읽은 이미지 데이터를 프로필 이미지의 src 속성에 할당하여 표시
    }

    // 선택된 파일을 읽음
    reader.readAsDataURL(selectedFile);
}