document.addEventListener('DOMContentLoaded', function() {
  const modal = document.getElementById('chatModal');
  const icon = document.querySelector('.profile');

  function openModal() {
    modal.style.display = 'block';
  }

  function closeModal() {
    modal.style.display = 'none';
  }

  icon.addEventListener('click', openModal);

  window.onclick = function(event) {
    if (event.target === modal) {
      closeModal();
    }
  }
});
