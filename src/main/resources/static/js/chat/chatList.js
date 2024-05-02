var modal = document.getElementById('myModal');
var icon = document.querySelector('.profile');

function openModal() {
  modal.style.display = 'block'; 
}

function closeModal() {
  modal.style.display = 'none'; 
}

icon.addEventListener('click', openModal);

var closeButton = document.querySelector('.close');
closeButton.addEventListener('click', closeModal);

window.onclick = function(event) {
  if (event.target == modal) {
    closeModal();
  }
}