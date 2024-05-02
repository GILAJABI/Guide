// 아이디 중복 확인
let check_id  = false

const tempInfo = ["user1", "travelmaker"]; // 임시로 아이디 만듦

function checkDuplicateId() {
    let id = document.getElementById('id').value;
    if (id === "") {
        alert("아이디를 입력해주세요.");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/member/checkDuplicateId",
        data: { uid: id }, // Ensure 'uid' matches the parameter name expected by the controller
        success: function(response) {
            if (response === "Duplicate") {
                alert("이미 사용 중인 아이디입니다.");
            } else {
                alert("사용 가능한 아이디입니다.");
                check_id = true
                console.log(check_id)
            }
        },
        error: function(xhr, status, error) {
            console.error(xhr.responseText);
        }
    });
}

function signUpForm() {
    let name = document.getElementById('name').value;
    let id = document.getElementById('id').value;
    let password = document.getElementById('password').value;
    let passwordCheck = document.getElementById('check').value;
    let phone = document.getElementById('phone').value;
    let year = document.getElementById('year').value;

    if (name === "" || id === "" || password === "" || passwordCheck === "" || phone === "" || year === "") {
        alert('모든 항목을 입력해주세요.');
        return false;
    }

    if (tempInfo.includes(id)) {
        alert('사용할 수 없는 아이디입니다.');
        return false;
    }

    if (password !== passwordCheck) {
        alert('입력한 비밀번호가 일치하지 않습니다.');
        return false;
    }

    var phoneRegex = /^010\d{7,8}$/;
    if (!phoneRegex.test(phone)) {
        alert('유효한 휴대폰 번호를 입력해주세요. (예: 01012345678)');
        return false;
    }

    if (year < 1900 || year >= 2005) {
        alert('유효한 연도를 입력해주세요. (1900년에서 2005년 사이)');
        return false;
    }

    if(check_id){
        return true;
    }
    else{
        alert("이미 회원가입된 아이디 입니다.")
        return false;
    }
    return true;
}
// 회원가입 제출 폼
