function validateForm() {
    var id = document.getElementById("id").value;
    var password = document.getElementById("password").value;

    if (id.trim() === '' || password.trim() === '') {
        alert("ID와 비밀번호를 입력하세요.");
        return false;
    }
    return true;
}