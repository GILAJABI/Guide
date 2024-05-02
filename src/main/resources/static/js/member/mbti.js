document.addEventListener('DOMContentLoaded', function() {
    const selectMBTI = document.querySelectorAll('.select');
    const travel_type = [];

    selectMBTI.forEach((group, index) => {
        const buttons = group.querySelectorAll('.selectBtn');

        buttons.forEach(button => {
            button.addEventListener('click', function() {
                // 이전에 선택된 스타일 제거
                buttons.forEach(btn => {
                    btn.classList.remove('selected');
                });

                // 현재 버튼에 선택 스타일 적용
                this.classList.add('selected');

                // 선택된 ID 저장, 배열의 각 위치에 그룹별로 하나의 ID만 저장
                travel_type[index] = this.id;

                // 모든 그룹에서 선택이 완료되었는지 확인하고 콘솔에 출력
                if (travel_type.length === selectMBTI.length && !travel_type.includes(undefined)) {
                    console.log("여행 MBTI: ", travel_type.join(''));
                }
            });
        });
    });
});
