document.addEventListener('DOMContentLoaded', function() {
    const travelTypeBoxes = document.querySelectorAll('#mbtiBox .box');
    const mbtiInput = document.getElementById('mbtiResult');

    // Assuming travelType is updated somewhere in your application
    function updateTravelTypeDisplay(mbti) {
        const typeMap = {
            'E': '사람 많은 도시파',
            'I': '나무 많은 자연파',
            'S': '정해진 코스 관광파',
            'N': '상상대로 모험파',
            'F': '감성파',
            'T': '효율파',
            'J': '꼼꼼한 계획파',
            'P': '끌리는대로 행동파'
        };

        travelTypeBoxes.forEach(box => {
            box.classList.remove('active');  // Remove active class from all boxes
            const typeKey = Object.keys(typeMap).find(key => mbti.includes(key));
            if (typeMap[typeKey] === box.textContent.trim()) {
                box.classList.add('active');
            }
        });
    }

    // Example to update the display, replace with actual event listener
    mbtiInput.addEventListener('change', () => updateTravelTypeDisplay(mbtiInput.value));
});
