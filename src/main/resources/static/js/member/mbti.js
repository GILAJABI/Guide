document.addEventListener('DOMContentLoaded', function() {
    const selectMBTI = document.querySelectorAll('.select');
    const travel_type = [];
    const mbtiInput = document.getElementById('mbtiResult');  // 숨겨진 입력 필드 참조

    selectMBTI.forEach((group, index) => {
        const buttons = group.querySelectorAll('.selectBtn');

        buttons.forEach(button => {
            button.addEventListener('click', function() {
                buttons.forEach(btn => btn.classList.remove('selected'));
                this.classList.add('selected');
                travel_type[index] = this.id;

                if (travel_type.length === selectMBTI.length && !travel_type.includes(undefined)) {
                    const mbti = travel_type.join('');
                    console.log("여행 MBTI: ", mbti);
                    mbtiInput.value = mbti;  // 숨겨진 입력 필드의 값 업데이트
                }
            });
        });
    });
});
