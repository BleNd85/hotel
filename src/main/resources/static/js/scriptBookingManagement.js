var deleteBookingModal = document.getElementById('deleteBookingModal');
deleteBookingModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var bookingId = button.getAttribute('data-id');
    var bookingIdInput = deleteBookingModal.querySelector('#bookingIdToDelete');
    bookingIdInput.value = bookingId;
});