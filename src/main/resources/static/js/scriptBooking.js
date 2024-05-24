var bookingSubmitModal = document.getElementById('bookingSubmit');
bookingSubmitModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var roomId = button.getAttribute('room-id');
    var userId = button.getAttribute('user-id');
    var roomIdInput = bookingSubmitModal.querySelector('#roomId');
    var userIdInput = bookingSubmitModal.querySelector('#userId');
    roomIdInput.value = roomId;
    userIdInput.value = userId;
});
