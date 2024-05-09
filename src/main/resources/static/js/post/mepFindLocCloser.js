var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476),
        level: 8
    };
var map = new daum.maps.Map(mapContainer, mapOption);
var geocoder = new daum.maps.services.Geocoder();
var marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
});

function getLatLngFromRoadName(roadName) {
    geocoder.addressSearch(roadName, function (results, status) {
        if (status === daum.maps.services.Status.OK) {
            var result = results[0];
            var latitude = result.y;
            var longitude = result.x;
            document.getElementById("sample6_latitude").value = latitude;
            document.getElementById("sample6_longitude").value = longitude;

            var coords = new daum.maps.LatLng(latitude, longitude);
            map.setCenter(coords);
            marker.setPosition(coords);
        } else {
            console.log("주소 검색 실패:", status);
        }
    });
}

function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {

            var addr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }

            }
            var addrs = data.address;
            document.getElementById("sample6_address").value = addrs;
            getLatLngFromRoadName(addrs);
        }
    }).open();
}